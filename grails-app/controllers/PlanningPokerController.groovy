import org.icescrum.components.UtilsWebComponents
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Task

import org.icescrum.core.domain.Product

import org.icescrum.core.domain.Team
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.support.ApplicationSupport

import org.springframework.security.core.context.SecurityContextHolder as SCH


class PlanningPokerController {

  static ui = true
  static final pluginName = 'ice-scrum-plugin-planning-poker'
  static final id = 'planningPoker'
  static menuBar = [show:[visible:true,pos:0],title:'is.ui.planningPoker']
  static window = [title:'is.ui.planningPoker',help:'is.ui.planningPoker.help',toolbar:true]


  def springSecurityService

  def index = {

    def projectMembers = []
    Product project = Product.findById(params.product)
    projectMembers = project.getAllUsers()

    def suite = []
    if(project. planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
         suite= PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)


    render(template:'window/blank',plugin:'iceScrum-plugin-planning-poker' ,model:[
            u:projectMembers,
            me: User.get(springSecurityService.principal.id),
            suite_fibo:suite,
            id:id,])
  }

  def join = {
      render(view:"_request", model:[id:id])
  }

  def start = {
    redirect(action: 'index', params:[product:params.product])
    pushOthers  "${params.product}-planningPoker"
  }

  def close = {}
}


