

/**
 * Created by IntelliJ IDEA.
 * User: Bertrand
 * Date: 25/02/11
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */

import grails.plugin.spock.*
import org.icescrum.core.domain.Product
import org.icescrum.core.domain.preferences.ProductPreferences
import icescrum.plugin.planning.poker.PlanningPokerSession

class PlanningPokerControllerSpec extends ControllerSpec {

 def setup(){
   mockDomain(Product)
   mockDomain(PlanningPokerSession)
   def planningPokerService = new PlanningPokerService()
  controller.planningPokerService = planningPokerService
   def p = new Product(name: 'testProj')
   p.pkey = 'TESTPROJ'
   p.startDate = new Date().parse('yyyy-M-d', String.format('%tF', new Date()))
   p.preferences = new ProductPreferences()
   p.save()
   def params = [product:p.id]
   controller.params.product = p.id
  }

  def 'index planning poker'(){
    when:
    controller.index()

    then:
    renderArgs == [template:'window/blank',plugin:'icescrum-plugin-planning-poker' ,model:[id:'planningPoker']]
  }
}
