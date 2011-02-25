import org.icescrum.core.domain.User
import org.icescrum.core.domain.Product
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.domain.Story

import icescrum.plugin.planning.poker.PlanningPokerVote
import icescrum.plugin.planning.poker.PlanningPokerSession

import grails.plugins.springsecurity.Secured

class PlanningPokerController {

  static ui = true
  static final pluginName = 'icescrum-plugin-planning-poker'
  static final id = 'planningPoker'
  static menuBar = [show:[visible:true,pos:0],title:'is.ui.planningPoker']
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
    render(template:'window/blank',plugin:pluginName ,model:[id:id])
  }

  def join = {
    redirect(action:'display', params:[product:params.product])
  }

  @Secured('scrumMaster()')
  def start = {
    PlanningPokerSession session = new PlanningPokerSession(product:Product.get(params.product))
    if(session.save())
        println "session created"
    pushOthers  "${params.product}-plugin-planning-poker"
    redirect(action:'display', params:[product:params.product])
  }

  def startVote = {
    pushOthers  "${params.product}-planningPoker-startVote"
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
    //redirect(controller:'project', action:'dashboard', params:[product:params.product])
    pushOthers "${params.product}-planningPoker-close"
    render(status:200)
  }

  def selectStory = {
    println "PP : story selected"
    pushOthers "${params.product}-planningPoker-selection-story"
  }

  def submitVote = {
    User currentUser = User.get(springSecurityService.principal.id)
    def currentSession = PlanningPokerSession.findByProduct(Product.get(params.product))
    def vote
    currentSession?.votes.each{
      if(it.user == currentUser)
        vote = it;
    }
    if(!vote)
      vote = new PlanningPokerVote(user:currentUser, session:currentSession)
    vote.voteValue = Integer.parseInt(params.valueCard)
    if(vote.save(flush:true))
        println "vote saved :" + vote.voteValue
  }
}