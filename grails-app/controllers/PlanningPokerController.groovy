/*
 * Copyright (c) 2011 BE ISI iSPlugins Université Paul Sabatier.
 *
 * This file is part of Planning Poker plugin for icescrum.
 *
 * This icescrum plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This icescrum plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this icescrum plugin.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors:	Marc-Antoine BEAUVAIS (marcantoine.beauvais@gmail.com)
 *		Gabriel GIL (contact.gabrielgil@gmail.com)
 *		Julien GOUDEAUX (julien.goudeaux@orange.fr)
 *		Jihane KHALIL (khaliljihane@gmail.com)
 *		Paul LABONNE (paul.labonne@gmail.com)
 *		Nicolas NOULLET (nicolas.noullet@gmail.com)
 *		Jérémy SIMONKLEIN (jeremy.simonklein@gmail.com)
 *
 *
 */

import org.icescrum.core.domain.User
import org.icescrum.core.domain.Product
import org.icescrum.core.domain.PlanningPokerGame
import org.icescrum.core.domain.Story

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
  def planningPokerService

  def index = {
    if(planningPokerService.getSession(params.product))
      forward(action:'display', params:[product:params.product])
    else
      render(template:'window/blank',plugin:pluginName ,model:[id:id])
  }

  def joinSession = {
    planningPokerService.createVote(params.product, springSecurityService.principal.id)
    redirect(action:'display', params:[product:params.product])
  }

  @Secured('scrumMaster()')
  def startSession = {
    planningPokerService.createSession(params.product)
    planningPokerService.createVote(params.product, springSecurityService.principal.id)
    pushOthers  "${params.product}-plugin-planning-poker"
    redirect(action:'display', params:[product:params.product])
  }

  def startVote = {
    planningPokerService.initVotes(params.product)
    push  "${params.product}-planningPoker-beginningOfCountDown"
    render(status:200)
  }

  def endOfCountDown = {
    if(!planningPokerService.hasVoted(params.product, springSecurityService.principal.id))
      planningPokerService.setUnvoted(params.product, springSecurityService.principal.id)
    if(planningPokerService.isVoteTerminated(params.product))
      push  "${params.product}-planningPoker-endOfCountDown"
    render(status:200)
  }

  def getResult = {
    render(template:'window/result',plugin:pluginName ,model:[result:planningPokerService.getResult(params.product)])
  }

  def display = {

    def currentProduct = Product.get(params.product)
    def storiesEstimees = Story.findAllByBacklogAndState(currentProduct, Story.STATE_ESTIMATED, [cache:true, sort:'rank'])
    def storiesNonEstimees = Story.findAllByBacklogAndState(currentProduct, Story.STATE_ACCEPTED, [cache:true, sort:'rank'])

    def projectMembers = currentProduct.getAllUsers()
    def pop=false
    for(def i=0;i<projectMembers.size() && pop==false;i++){
      if(projectMembers[i].id==springSecurityService.principal.id){
        pop=true
        projectMembers.remove(i)
      }
    }

    def suite
    if(currentProduct.planningPokerGameType == PlanningPokerGame.FIBO_SUITE)
      suite = PlanningPokerGame.getInteger(PlanningPokerGame.FIBO_SUITE)
    else
      suite = PlanningPokerGame.getInteger(PlanningPokerGame.INTEGER_SUITE)

    render(template:'window/planningPoker',plugin:pluginName ,model:[
            projectMembers:projectMembers,
            me: User.get(springSecurityService.principal.id),
            suite_fibo:suite,
            stories_e:storiesEstimees,
            stories_ne:storiesNonEstimees,
            id:id,
            valueBeforeVote:planningPokerService.VALUE_BEFORE_VOTE,
            valueUnvoted:planningPokerService.VALUE_UNVOTED])
  }

  def estimateStory = {
    planningPokerService.acceptEstimate(params.product, params.int('value'))
    push "${params.product}-planningPoker-voteAccepted"
    render(status:200)
  }

  @Secured('scrumMaster()')
  def closeSession = {
    pushOthers "${params.product}-planningPoker-close"
    planningPokerService.deleteSession(params.product)
    render(status:200)
  }

  @Secured('scrumMaster()')
  def selectStory = {
    planningPokerService.setStory(params.product, params.story)
    push "${params.product}-planningPoker-selection-story"
    render(status:200)
  }

  def getStory = {
    render(status:200, text:[story:planningPokerService.getStory(params.product)] as JSON)
  }

  def submitVote = {
    planningPokerService.setVote(params.product, params.iduser, Integer.parseInt(params.valueCard))
    pushOthers "${params.product}-planningPoker-displayStatusOthers"
    render(status:200)
  }

  def getVotes = {
    render(status:200, text:[votes:planningPokerService.getVotes(params.product)] as JSON)
  }

  def button = {
    render(is.separator() + is.iconButton([action:"index",controller:id, onSuccess:"jQuery.icescrum.openWindow('planningPoker');"],message(code:'is.ui.planningPoker')))
  }

  def acceptVote = {
    planningPokerService.acceptResult(params.product);
    push "${params.product}-planningPoker-voteAccepted"
    render(status:200)
  }

  def revote = {
    push "${params.product}-planningPoker-revote"
    render(status:200)
  }

  def cancel = {
    push "${params.product}-planningPoker-voteAccepted"
    render(status:200)
  }
}