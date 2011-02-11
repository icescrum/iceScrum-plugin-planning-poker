import org.icescrum.components.UtilsWebComponents
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Task

import org.icescrum.core.domain.Product

import org.icescrum.core.domain.Team
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.support.ApplicationSupport
import org.icescrum.core.domain.Story

import grails.plugins.springsecurity.Secured
import org.springframework.security.core.context.SecurityContextHolder as SCH


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

       render(template:'window/blank',plugin:pluginName ,model:[
            id:id])
  }

  def join = {
    redirect(action: 'display', params:[product:params.product])
  }

  @Secured('scrumMaster()')
  def start = {

    pushOthers  "${params.product}-plugin-planning-poker"
    redirect(action: 'display', params:[product:params.product])
  }

  def display = {

    def projectMembers = []
    def storiesEstimees = []
    def storiesNonEstimees = []

    def currentProduct = Product.get(params.product)

    // r�cup�ration de la liste des utilisateurs travaillant sur le projet
    projectMembers = currentProduct.getAllUsers()
       // suppression de l'utilisateur courant de la liste
    def pop=false
    for(def i=0;i<projectMembers.size() && pop==false;i++){
      if(projectMembers[i].id==springSecurityService.principal.id){
        pop=true
        projectMembers.remove(i)
      }
    }

    // Recherche de la liste des stories estimees du projet
    storiesEstimees=Story.findAllByBacklogAndState(currentProduct, Story.STATE_ESTIMATED,  [cache: true, sort: 'rank'])

    // Recherche de la liste des stories estimees du projet
    storiesNonEstimees=Story.findAllByBacklogAndState(currentProduct, Story.STATE_ACCEPTED,  [cache: true, sort: 'rank'])


    // liste des cartes selon parametres du projet
    def suite = []
    if(currentProduct. planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)


    render(template:'window/planningPoker',plugin:pluginName ,model:[
            u:projectMembers,
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
}


