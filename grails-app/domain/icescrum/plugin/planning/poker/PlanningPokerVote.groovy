package icescrum.plugin.planning.poker

import org.icescrum.core.domain.User

class PlanningPokerVote {

    static belongsTo = [user:User, session:PlanningPokerSession]
    int voteValue

    static constraints = {
        voteValue(nullable:true)
    }
}
