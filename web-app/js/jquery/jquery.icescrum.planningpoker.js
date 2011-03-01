(function($){

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
            product:null

        },

        init:function(product){

             var self = this;
             var o = self.o;
            o.product = product;
             this._resize();
            //----------- Vote------------
             $(".planning-poker-carte.me").click(function(){
               $(".planning-poker-carte.me").removeClass("activatedCard");
               $(this).addClass("activatedCard");  // bla
             });
            $("#window-content-${id}").removeClass('window-content-toolbar');
  if(!$("#dropmenu").is(':visible')){
    $("#window-id-${id}").focus();

                $(window).resize(this._resize);

  }
        },

        notifyPlanningPoker:function(product){
            console.log("product : " + product);
            jQuery.icescrum.renderNotice("New Planning Poker session "+
                                        "<a id=\"redirection\" href=\"javascript:;\" disabled=\"true\" " +
                                        "<button>Join</button>" +
                                        "</a>","notice");
            $('#redirection').click(function(){
                $.ajax({type:'POST',
                    global:false,
                    url: $.icescrum.o.grailsServer + '/planningPoker/join/',
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

        notifyStorySelected:function(product){
                $.ajax({type:'POST',
                    global:false,
                    url: $.icescrum.o.grailsServer + '/planningPoker/getStory/',
                    data: {
                        product: product
                    },
                    success:function(data) {
                        data = $.parseJSON(data);
                        $("#planning-poker-table" + data.story.id ).addClass("selected ui-corner-all").siblings().removeClass("selected ui-corner-all");
                    }
                });
        },

        startVote:function(product, iduser){
            //Requete ajax pour enregistrer le vote par d�fault au d�but du compte � rebours
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/saveVoteBeginning/',
                data: {
                    product: product
                },
                success:function() {
                    $.icescrum.planningpoker.displayStatusOthers(product, iduser);
                }
            });
            //Affichage du compte � rebours
            $('#planning-poker-table').html('<div id="planning-poker-countdown"></div>');
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

        endOfCountDown:function(product, iduser){
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
                    $("#planning-poker-final-estimate").html("<div class=\"planning-poker-carte-result  ui-corner-all\"><div class=\"estimation\">"+data.pourcentage+"</div></div>");

                }
            });
        },

        displayResultOthers:function(product, iduser){
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getVotes/',
                data: {
                    product: product
                },
                success:function(data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.votes.length; i++){
                        if(data.votes[i].user.id != iduser){
                            if(data.votes[i].voteValue == -1){
                                $('#planning-poker-members-list-card-'+data.votes[i].user.id).html('?');
                            }else{
                                $('#planning-poker-members-list-card-'+data.votes[i].user.id).html(data.votes[i].voteValue);
                            }
                        }
                    }
                }
            });
        },

        displayStatusOthers:function(product, iduser){
            $.ajax({type:'POST',
                global:false,
                url: $.icescrum.o.grailsServer + '/planningPoker/getVotes/',
                data: {
                    product: product
                },
                success:function(data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.votes.length; i++){
                        if(data.votes[i].user.id != iduser){
                            if(data.votes[i].voteValue == -10){
                                $('#planning-poker-members-list-card-'+data.votes[i].user.id).html('...');
                            }else{
                                if(data.votes[i].voteValue >= 0)
                                    $('#planning-poker-members-list-card-'+data.votes[i].user.id).html('Ok');
                            }
                        }
                    }
                }
            });
        },

        _resize:function(){
            var self = this;
            var o = self.o;

            o.storyWidth= $(".postit.story").width();
            o.windowWidth = $('.window-content').width();
            o.windowHeight = $('.window-content').height();

            $('.window-content #jeu').css({height: o.windowHeight- $('.window-content #stories').height() +'px'})
            o.estimatedStoriesListWidth = o.storyWidth * $("#estimated-list .postit-story").size();

            o.acceptedStoriesListWidth = o.storyWidth * $("#accepted-list .postit-story").size();


            $("#accepted-list .postit-story").
            $('#accepted-list').css({width: o.acceptedStoriesListWidth });
            $('#estimated-list').css({width: o.estimatedStoriesListWidth });

           $('#accepted-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});
           $('#estimated-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});




        },

        closePlanningPoker:function(){
             jQuery.icescrum.openWindow('project');
        } ,

        selectStory:function(ui,idSelect){
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
                }
            });
        }
    });




    $.widget("ui.planningpoker", {
	    options: {
            id: null,
            storyWidth:null,
            estimatedStoriesListWidth:null,
            acceptedStoriesListWidth:null,
            escapedJid: null,

            planningPokerManager: {
                init: function(elem) {
                    this.elem = elem;



                }


            }
	    },

        widget: function() {
            return this.ui.planninPoker
        },

        _create: function(){




        },

        _setOption: function(option, value) {
            if(value != null){
            switch(option) {
            case "hidden":
                if(value) {
                    this.uiChat.hide();
                } else {
                this.uiChat.show();
                }
                break;
            case "offset":
                this._position(value);
                break;
            case "width":
                this._setWidth(value);
                break;
            }
            }
            $.Widget.prototype._setOption.apply(this, arguments);
        }


    });

}(jQuery));