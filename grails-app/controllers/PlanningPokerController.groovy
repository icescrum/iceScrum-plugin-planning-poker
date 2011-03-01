import org.icescrum.core.domain.User
import org.icescrum.core.domain.Product
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.domain.Story

import icescrum.plugin.planning.poker.PlanningPokerVote
import icescrum.plugin.planning.poker.PlanningPokerSession

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured('inProduct()')
class PlanningPokerController {

  static ui = true
  static final pluginName = 'icescrum-plugin-planning-poker'
  static final id = 'planningPoker'
  static window = [title:'is.ui.planningPoker',help:'is.ui.planningPoker.help',toolbar:true]

  static stateBundle = [
          (Story.STATE_SUGGESTED):'is.story.state.suggested',
          (Story.STATE_ACCEPTED):'is.story.state.accepted',
          (Story.STATE_ESTIMATED):'is.story.state.estimated',
          (Story.STATE_PLANNED):'is.story.state.planned',
          (Story.STATE_INPROGRESS):'is.story.state.inprogress',
          (Story.STATE_DONE):'is.story.state.done'
  ]

  static typesBundle = [
          (Story.TYPE_USER_STORY): 'is.story.type.story',
          (Story.TYPE_DEFECT): 'is.story.type.defect',
          (Story.TYPE_TECHNICAL_STORY): 'is.story.type.technical'
  ]

  def springSecurityService

  def index = {
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    if(currentSession)
        forward(action:'display', params:[product:params.product])
    else
        render(template:'window/blank',plugin:pluginName ,model:[id:id])
  }

  def join = {
    User currentUser = User.get(springSecurityService.principal.id)
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    PlanningPokerVote vote = new PlanningPokerVote(user:currentUser, session:currentSession)
    vote.save(flush:true)
    redirect(action:'display', params:[product:params.product])
  }

  @Secured('scrumMaster()')
  def start = {
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    currentSession?.delete()
    PlanningPokerSession session = new PlanningPokerSession(product:Product.get(params.product))
    if(session.save())
        println "session created"
    pushOthers  "${params.product}-plugin-planning-poker"
    redirect(action:'display', params:[product:params.product])
  }

  def startVote = {
    push  "${params.product}-planningPoker-beginningOfCountDown"
    render(status:200)
  }

  def endOfCountDown = {
    //Fin du compte � rebours, enregistre -1 si l'utilisateur n'a pas vot�
    //Si il reste des votes � -10 alors certain non pas fini leur compte � rebours
    //Si tout le monde a fini le compte � rebours push tout le monde pour afficher le r�sultat du planning poker
    User currentUser = User.get(springSecurityService.principal.id)
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    def vote
    currentSession.votes.each{
      if(it.user == currentUser)
        vote = it;
    }
    if(vote.voteValue == -10)
        vote.voteValue = -1
    if(vote.save(flush:true))
        println "vote saved :" + vote.voteValue
    def fini = true
    currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    currentSession.votes.each{
      if(fini && it.voteValue == -10 && it.user != currentUser){
        fini = false
      }
    }
    if(fini){
        push  "${params.product}-planningPoker-endOfCountDown"
    }
  }

  def saveVoteBeginning = {
    //Enregistre par d�fault -10 au d�but du compte � rebours
    User currentUser = User.get(springSecurityService.principal.id)
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    def vote
    currentSession.votes.each{
      if(it.user == currentUser)
        vote = it;
    }
    if(vote == null)
        vote = new PlanningPokerVote(user:currentUser, session:currentSession)
    vote.voteValue = -10
    if(vote.save(flush:true))
        println "vote saved :" + vote.voteValue
    render(status:200)
  }

  def getResult = {
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    float totalVotes = 0
    float countUsers = 0
    currentSession.votes.each{
      if(it.voteValue >= 0){
        totalVotes += it.voteValue
        countUsers ++
      }
    }
    def result
    if(countUsers != 0){
        result = [pourcentage:(totalVotes/countUsers).round(1)]
    }else{
        result = [pourcentage:"?"]
    }
    render(status:200, contentType: 'application/json', text: result as JSON)
  }

  def display = {

    def currentProduct = Product.get(params.product)

    def projectMembers = currentProduct.getAllUsers()
    def pop=false
    for(def i=0;i<projectMembers.size() && pop==false;i++){
      if(projectMembers[i].id==springSecurityService.principal.id){
        pop=true
        projectMembers.remove(i)
      }
    }
    def usersWithVote = []
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    projectMembers.each{
      def memberSearched = it ;
      def voteValue = null ;
      currentSession?.votes.each{
        if(it.user == memberSearched)
         voteValue = it.voteValue
        }
        usersWithVote.add(user:memberSearched,voteValue:voteValue)
    }

    def storiesEstimees = Story.findAllByBacklogAndState(currentProduct, Story.STATE_ESTIMATED,  [cache: true, sort: 'rank'])
    def storiesNonEstimees = Story.findAllByBacklogAndState(currentProduct, Story.STATE_ACCEPTED,  [cache: true, sort: 'rank'])

    def suite
    if(currentProduct.planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
         suite = PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
         suite = PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)

    render(template:'window/planningPoker',plugin:pluginName ,model:[
            usersWithVote:usersWithVote,
            me: User.get(springSecurityService.principal.id),
            suite_fibo:suite,
            stories_e:storiesEstimees,
            stories_ne:storiesNonEstimees,
            id:id,])
  }

  def close = {
    pushOthers "${params.product}-planningPoker-close"
    render(status:200)
  }

  def selectStory = {
    println "PP : story selected"
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    currentSession.story = Story.get(params.story)
    currentSession.save(flush:true)
    push "${params.product}-planningPoker-selection-story"
    render(status:200)
  }

  def getStory = {
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    def story = currentSession.story
    render(status:200, text:[story:story] as JSON)
  }

  def submitVote = {
    User currentUser = User.get(springSecurityService.principal.id)
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    def vote
    currentSession.votes.each{
      if(it.user == currentUser)
        vote = it;
    }
    vote.voteValue = Integer.parseInt(params.valueCard)
    if(vote.save(flush:true))
        println "vote saved :" + vote.voteValue
    pushOthers "${params.product}-planningPoker-displayStatusOthers"
      render(status:200)
  }

  def getVotes = {
      def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
      render(status:200, text:[votes:currentSession.votes] as JSON)
  }

  def button = {
    render(is.separator() + is.iconButton([action:"index",controller:id, onSuccess:"jQuery.icescrum.openWindow('planningPoker');"],message(code:'is.ui.planningPoker')))
  }
}