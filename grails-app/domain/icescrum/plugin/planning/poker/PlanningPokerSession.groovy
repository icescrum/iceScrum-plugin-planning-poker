package icescrum.plugin.planning.poker

import org.icescrum.core.domain.Product
import org.icescrum.core.domain.Story

class PlanningPokerSession {

    static belongsTo = [product:Product, story:Story]
    static hasMany = [votes:PlanningPokerVote]

    static constraints = {
        story(nullable:true)
        votes(nullable:true)
    }
}
