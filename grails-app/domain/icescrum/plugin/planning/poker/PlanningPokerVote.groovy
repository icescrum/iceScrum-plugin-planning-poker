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

import org.icescrum.core.domain.User

class PlanningPokerVote implements Serializable {

    static belongsTo = [user:User, session:PlanningPokerSession]
    int voteValue

    static constraints = {
        voteValue(nullable:true)
    }
}
