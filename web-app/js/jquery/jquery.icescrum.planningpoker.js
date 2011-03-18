/*
 * Copyright (c) 2011 BE ISI iSPlugins Université Paul Sabatier.
 *
 * This file is part of iceScrum.
 *
 * Planning Poker plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Planning Poker plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Planning Poker plugin.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: 	Claude AUBRY (claude.aubry@gmail.com)
 * 		Vincent BARRIER (vbarrier@kagilum.com)
 *		Marc-Antoine BEAUVAIS (marcantoine.beauvais@gmail.com)
 *		Vincent CARASSUS (vincentcarassus@gmail.com)
 *		Gabriel GIL (contact.gabrielgil@gmail.com)
 *		Julien GOUDEAUX (julien.goudeaux@orange.fr)
 *		Guillaume JANDIN (guillaume.baz@gmail.com)
 *		Jihane KHALIL (khaliljihane@gmail.com)
 *		Paul LABONNE (paul.labonne@gmail.com)
 *		Nicolas NOULLET (nicolas.noullet@gmail.com)
 *		Bertrand PAGES (pages.bertrand@gmail.com)
 *		Jérémy SIMONKLEIN (jeremy.simonklein@gmail.com)
 *		Steven STREHL (steven.strehl@googlemail.com)
 *
 *
 */

(function($) {

    $.icescrum.planningpoker = ({

        defaults:{
            i18n:{
                newPlanningSession:'New planning poker session',
                join:'Join'
            }
        },

        o:{
            id: null,
            storyWidth:null,
            estimatedStoriesListWidth:null,
            acceptedStoriesListWidth:null,
            product:null,
            valueBeforeVote:null,
            valueUnvoted:null
        },

        init:function(product, valueBeforeVote, valueUnvoted) {
            var self = this;
            var o = self.o;
            o.product = product;
            o.valueBeforeVote = valueBeforeVote;
            o.valueUnvoted = valueUnvoted;
            this._resize();
            //----------- Vote------------
            $(".planning-poker-carte.me").click(function() {
                $(".planning-poker-carte.me").removeClass("activatedCard");
                $(this).addClass("activatedCard");  // bla
            });
            $("#window-content-${id}").removeClass('window-content-toolbar');
            if (!$("#dropmenu").is(':visible')) {
                $("#window-id-${id}").focus();

                $(window).resize(this._resize);

            }
            $("#voteButton").hide();
            $('#planning-poker-card-list').hide();

            $('#estimated-title').toggle(function(){
                $('#estimated-list').hide();
                self._resize();
                $(this).find('span').html('<strong>+</strong>');}, function(){
                $('#estimated-list').show();
                self._resize();
                $(this).find('span').html('<strong>-</strong>');

            })
        },

        notifyPlanningPoker:function(product) {
            console.log("product : " + product);
            jQuery.icescrum.renderNotice("New Planning Poker session " +
                    "<a id=\"redirection\" href=\"javascript:;\" disabled=\"true\" " +
                    "<button>Join</button>" +
                    "</a>", "notice");
            $('#redirection').click(function() {
                $.ajax({type:'POST',
                    global:false,
                    url: $.icescrum.o.grailsServer + '/planningPoker/joinSession/',
                    data: {
                        product: product
                    },
                    success:function(data) {
                        console.log("redirection pp");
                        jQuery.icescrum.openWindow('planningPoker/display');
                    }
                });
            });
        },

        notifyStorySelected:function(product) {
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getStory/',
                data: {
                    product: product
                },
                success:function(data) {
                    data = $.parseJSON(data);
                    $("#postit-story-" + data.story.id).parent().addClass("selected ui-corner-all").siblings().removeClass("selected ui-corner-all");
                }
            });
        },

        startVote:function(iduser) {
            var product = this.o.product;
            $.icescrum.planningpoker.displayStatusOthers(product, iduser);
           $('#accepted-list .accepted-list').selectable("disable");
            //Affichage du compte � rebours
            $('#planning-poker-table').html('<div id="planning-poker-countdown"></div>');
            $('#planning-poker-card-list').show("normal");
            $('#planning-poker-countdown').countDown({
                startNumber: 10,
                callBack: function() {
                    //A la fin du compte � rebours
                    //Requete ajax pour checker si tout le monde a vot�, si oui alors notifie tout le monde pour afficher le r�sultat
                    $.ajax({type:'POST',
                        global:false,
                        url: $.icescrum.o.grailsServer + '/planningPoker/endOfCountDown/',
                        data: {
                            product: product
                        },
                        success:function() {
                        }
                    });
                }
            });

        },

        endOfCountDown:function(iduser) {
            var product = this.o.product;
            //Requete ajax pour avoir le resultat du planning poker
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getResult/',
                data: {
                    product: product
                },
                success:function(data) {
                    $('#planning-poker-table').html('');
                    $.icescrum.planningpoker.displayResultOthers(product, iduser);
                    $('#planning-poker-final-estimate').html(data);
                    $('#planning-poker-card-list').hide("normal");
                }
            });
        },

        displayResultOthers:function(iduser) {
            var product = this.o.product;
            var valueUnvoted = this.o.valueUnvoted;
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getVotes/',
                data: {
                    product: product
                },
                success:function(data) {
                    data = $.parseJSON(data);
                    $('#planning-poker-members-list-card-me').html("<div id=\"planning-poker-members-list-card-" + iduser + "\" class=\"planning-poker-carte-others ui-corner-all\">&nbsp;</div>");
                    for (var i = 0; i < data.votes.length; i++) {
                        if (data.votes[i].voteValue == valueUnvoted) {
                            $('#planning-poker-members-list-card-' + data.votes[i].user.id).html('?');
                        } else {
                            $('#planning-poker-members-list-card-' + data.votes[i].user.id).html(data.votes[i].voteValue);
                        }
                    }
                }
            });
        },

        displayStatusOthers:function(iduser) {
            var product = this.o.product;
            var valueBeforeVote = this.o.valueBeforeVote;
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getVotes/',
                data: {
                    product: product
                },
                success:function(data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.votes.length; i++) {
                        if (data.votes[i].user.id != iduser) {
                            if (data.votes[i].voteValue == valueBeforeVote) {
                                $('#planning-poker-members-list-card-' + data.votes[i].user.id).html('...');
                            } else {
                                if (data.votes[i].voteValue >= 0)
                                    $('#planning-poker-members-list-card-' + data.votes[i].user.id).html('Ok');
                            }
                        }
                    }
                }
            });
        },

        _resize:function() {
            var self = this;
            var o = self.o;
            o.storyWidth = $(".postit.story").width();
            o.windowWidth = $('.window-content').width();

            o.windowHeight = $('.window-content').height();

             o.estimatedStoriesListWidth = o.storyWidth * $("#estimated-list .postit-story").size();

            o.acceptedStoriesListWidth = o.storyWidth * $("#accepted-list .postit-story").size();


            //$("#accepted-list .postit-story").
            $('#accepted-list').css({width: o.acceptedStoriesListWidth });
            $('#estimated-list').css({width: o.estimatedStoriesListWidth });
$('#accepted-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});
            $('#estimated-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});
            $('.window-content #jeu').css({height: o.windowHeight - $('.window-content #stories').height()-1 + 'px'})


        },

        closePlanningPoker:function() {
            jQuery.icescrum.openWindow('productBacklog');
        } ,

        selectStory:function(ui, idSelect) {
            var elem = ui;
            var o = this.o;
            //Requete ajax pour avoir le resultat du planning poker
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/selectStory/',
                data: {
                    product: o.product,
                    story:idSelect
                },
                success:function() {
                    $(elem).addClass("selected ui-corner-all").siblings().removeClass("selected ui-corner-all");
                    $("#voteButton").show();
                }
            });
        }
    });

}(jQuery));
