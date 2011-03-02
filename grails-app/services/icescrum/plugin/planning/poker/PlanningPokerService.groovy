package icescrum.plugin.planning.poker

import org.icescrum.core.domain.Product
import org.icescrum.core.domain.User
import org.icescrum.core.domain.Story

class PlanningPokerService {

    //static transactional = true

    def springSecurityService

    def createSession(productid) {
        PlanningPokerSession session = new PlanningPokerSession(product:Product.get(productid))
        return session.save(flush:true)
    }

    def getCurrentSession(productid) {
        return PlanningPokerSession.findByProduct(Product.get(productid))
    }

    def deleteSession(productid) {
        getCurrentSession(productid).delete()
    }

    def setStory(productid, storyid) {
        PlanningPokerSession currentSession = getCurrentSession(productid)
        currentSession.story = Story.get(storyid)
        return currentSession.save(flush:true)
    }

    def getStory(productid) {
        return getCurrentSession(productid).story
    }

    def createVote(productid, userid) {
        User currentUser = User.get(userid)
        PlanningPokerSession currentSession = getCurrentSession(productid)
        PlanningPokerVote vote = new PlanningPokerVote(user:currentUser, session:currentSession)
        return vote.save(flush:true)
    }

    def initVotes(productid){
        getVotes(productid).each{
            it.voteValue = -10
            it.save(flush:true)
        }
    }

    def getVotes(productid) {
        return getCurrentSession(productid)?.votes
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
}
