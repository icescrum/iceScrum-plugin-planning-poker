import org.icescrum.components.UtilsWebComponents
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Task

import org.icescrum.core.domain.Product

import org.icescrum.core.domain.Team
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.support.ApplicationSupport
import org.icescrum.core.domain.Story

import org.springframework.security.core.context.SecurityContextHolder as SCH


class PlanningPokerController {

  static ui = true
  static final pluginName = 'ice-scrum-plugin-planning-poker'
  static final id = 'planningPoker'
  static menuBar = [show:[visible:true,pos:0],title:'is.ui.planningPoker']
  static window = [title:'is.ui.planningPoker',help:'is.ui.planningPoker.help',toolbar:true]


  def springSecurityService

  def index = {

       render(template:'window/blank',plugin:'iceScrum-plugin-planning-poker' ,model:[
            id:id])
  }

  def join = {
     flash.notice = [text: 'ouverture planning poker', type: 'notice']
     render(view:"_request", model:[id:id, p:params])
  }

  def start = {

    def projectMembers = []

    def currentProduct = Product.get(params.product)

    projectMembers = currentProduct.getAllUsers()

    def suite = []
    if(currentProduct. planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)


    def stories= Story.findAllByBacklogAndState(currentProduct, Story.STATE_SUGGESTED,  [cache: true, sort: 'rank'])

    pushOthers  "${params.product}-plugin-planning-poker"

    render(template:'window/planningPoker',plugin:'iceScrum-plugin-planning-poker' ,model:[
            u:projectMembers,
            me: User.get(springSecurityService.principal.id),
            suite_fibo:suite,
            stories_l:stories,
            id:id,])
  }

  def accept = {
    def projectMembers = []

    def currentProduct = Product.get('1')

    projectMembers = currentProduct.getAllUsers()

    def suite = []
    if(currentProduct. planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)


    def stories= Story.findAllByBacklogAndState(currentProduct, Story.STATE_SUGGESTED,  [cache: true, sort: 'rank'])

    render(view:'window/planningPoker',plugin:'iceScrum-plugin-planning-poker' ,model:[
            u:projectMembers,
            me: User.get(springSecurityService.principal.id),
            suite_fibo:suite,
            stories_l:stories,
            id:id,])


  }

  def close = {}
}


