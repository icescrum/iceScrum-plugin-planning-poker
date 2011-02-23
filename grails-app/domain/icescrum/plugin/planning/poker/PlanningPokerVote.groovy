package icescrum.plugin.planning.poker

import org.icescrum.core.domain.User

class PlanningPokerVote {

    static constraints = {
    }

    static belongsTo = [user:User, session:PlanningPokerSession]
    int voteValue
}
