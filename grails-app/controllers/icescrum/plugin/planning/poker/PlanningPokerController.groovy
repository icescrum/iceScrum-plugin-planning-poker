import org.icescrum.components.UtilsWebComponents
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Task

import org.icescrum.core.domain.Product
import grails.converters.JSON
import org.icescrum.core.domain.Team
import org.icescrum.plugins.chat.ChatConnection
import org.icescrum.plugins.chat.ChatUtils
import org.icescrum.core.support.ApplicationSupport



class PlanningPokerController {

  static final id = 'planningPoker'
  static ui = true
  static menuBar = [show:[visible:true,pos:0],title:'is.ui.planningPoker']
  static window = [title:'is.ui.planningPoker',help:'is.ui.planningPoker.help',toolbar:false]
  def index = {
    plugin:'iceScrum-plugin-planning-poker'

    def projectMembers = []
    def users = []
    Product project = (Product) Product.findById(1)
   projectMembers = project.getAllUsers()
    //users: project.getAllUsers()


    render(template:'window/blank',plugin:'iceScrum-plugin-planning-poker' ,model:[
            u:projectMembers,
            id:id,])
  }

  def pushTest = {
    render(status: 200, text: 'ok')
    pushOthers "${params.product}-product"

  }
}