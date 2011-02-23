package icescrum.plugin.planning.poker

import org.icescrum.core.domain.Product
import org.icescrum.core.domain.Story

class PlanningPokerSession {

    static constraints = {
    }

    static belongsTo = [product:Product, story:Story]
    static hasMany = [vote:PlanningPokerVote]
}
