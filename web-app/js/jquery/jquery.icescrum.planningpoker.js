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
            acceptedStoriesListWidth:null

        },

        init:function(){
             var self = this;
             var o = self.o;
            o.storyWidth= $(".postit.story").width();
            o.windowWidth = $('.window-content').width();
            o.estimatedStoriesListWidth = o.storyWidth * $("#estimated-list .postit-story").size();

            o.acceptedStoriesListWidth = o.storyWidth * $("#accepted-list .postit-story").size();

             $('#accepted-list').css({width: o.acceptedStoriesListWidth });
            $('#estimated-list').css({width: o.estimatedStoriesListWidth });

            $('#accepted-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});
            $('#estimated-list').scrollbar({contentWidth:o.windowWidth, position:'bottom'});

            //----------- Vote------------
             $(".planning-poker-carte.me").click(function(){
               $(".planning-poker-carte.me").removeClass("activatedCard");
               $(this).addClass("activatedCard");  // bla
             });
            $("#window-content-${id}").removeClass('window-content-toolbar');
  if(!$("#dropmenu").is(':visible')){
    $("#window-id-${id}").focus();
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
                        data: $.parseJSON(data),
                        $("planning-poker-table").html("Story selectionnee : " + data.story );
                    }
                });
        },

        showResult:function(){
            $("#planning-poker-final-estimate").html("<div class=\"planning-poker-carte-result  ui-corner-all\"><div class=\"estimation\">17</div></div>");
            //<is:planningPokerFinalEstimate number=\"16\"/>
        },

        startVote:function(){

        },

        closePlanningPoker:function(){
             jQuery.icescrum.openWindow('project');
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