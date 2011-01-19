package icescrum.plugin.planning.poker

class PlanningPokerController {

  static final id = 'planningPoker'
  static ui = true
  static menuBar = [show:[visible:true,pos:0],title:'is.ui.planningPoker']
  static window = [title:'is.ui.planningPoker',help:'is.ui.planningPoker.help',toolbar:false]
  def index = {
    plugin:'iceScrum-plugin-planning-poker'
  }
}
