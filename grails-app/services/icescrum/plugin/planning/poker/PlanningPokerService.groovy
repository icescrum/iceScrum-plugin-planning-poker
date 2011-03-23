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
package icescrum.plugin.planning.poker

import org.icescrum.core.domain.Product
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Story

class PlanningPokerService {

    def VALUE_BEFORE_VOTE = -10;
    def VALUE_UNVOTED = -1;

    def springSecurityService
    def productBacklogService

    def createSession(productid) {
        PlanningPokerSession session = new PlanningPokerSession(product:Product.get(productid))
        return session.save(flush:true)
    }

    def getSession(productid) {
        return PlanningPokerSession.findByProduct(Product.get(productid))
    }

    def deleteSession(productid) {
        getSession(productid)?.delete()
    }

    def setStory(productid, storyid) {
        PlanningPokerSession currentSession = getSession(productid)
        currentSession.story = Story.get(storyid)
        return currentSession.save(flush:true)
    }

    def getStory(productid) {
        return getSession(productid).story
    }

    def createVote(productid, userid) {
        User currentUser = User.get(userid)
        PlanningPokerSession currentSession = getSession(productid)
        PlanningPokerVote vote = new PlanningPokerVote(user:currentUser, session:currentSession)
        return vote.save(flush:true)
    }

    def initVotes(productid){
        getVotes(productid).each{
            it.voteValue = VALUE_BEFORE_VOTE
            it.save(flush:true)
        }
    }

    def getVotes(productid) {
        return getSession(productid)?.votes
    }

    def setUnvoted(productid, userid) {
        setVote(productid, userid, VALUE_UNVOTED);
    }

    def setVote(productid, userid, value) {
        User currentUser = User.get(userid)
        def vote = null
        getVotes(productid).each{
            if(it.user == currentUser)
                vote = it;
        }
        if(!vote)
            return false
        vote.voteValue = value
        return vote.save(flush:true)
    }

    boolean hasVoted(productid, userid) {
        def currentUser = User.get(userid)
        def hasVoted = false;
        getVotes(productid).each{
            if(it.user == currentUser)
                if (it.voteValue != VALUE_BEFORE_VOTE)
                    hasVoted = true;
        }
        return hasVoted
    }

    def isVoteTerminated (productid) {
        def terminated = true
        getVotes(productid).each{
            if(it.voteValue == VALUE_BEFORE_VOTE)
                terminated = false
        }
        return terminated
    }

    def printVotes (commentaire) {
        println "Votes - " + commentaire
        PlanningPokerVote.list().each{
            println it.user.username + ": " + it.voteValue
        }
    }

    def getResult (productid) {
        def votes = getVotes(productid)
        float totalVotes = 0
        float nbVotes = 0
        votes.each{
            if(it.voteValue >= 0) {
                totalVotes += it.voteValue
                nbVotes ++
            }
        }
        String result = "?"
        if(nbVotes > 0)
            result = String.valueOf((totalVotes/nbVotes).round(1))
        return result
    }

    def acceptResult (productid) {
      def story = getStory(productid)
      def result = getResult(productid)
      if(result != "?")
      {
           result = result.substring(0,result.indexOf("."))
      }
      productBacklogService.estimateStory(story,result);
    }
}
