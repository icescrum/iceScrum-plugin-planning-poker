package icescrum.plugin.planning.poker

import org.icescrum.core.domain.Product
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Story

class PlanningPokerService {

    //static transactional = true

    def VALUE_BEFORE_VOTE = -10;
    def VALUE_UNVOTED = -1;

    def springSecurityService

    def createSession(productid) {
        PlanningPokerSession session = new PlanningPokerSession(product:Product.get(productid))
        return session.save(flush:true)
    }

    def getSession(productid) {
        return PlanningPokerSession.findByProduct(Product.get(productid))
    }

    def deleteSession(productid) {
        getSession(productid).delete()
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
        getVotes(productid).each{
            if(it.voteValue == VALUE_BEFORE_VOTE)
                return false
        }
        return true
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
}
