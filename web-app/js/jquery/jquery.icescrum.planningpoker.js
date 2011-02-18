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


            o.storyWidth= $(".postit.story").css('width');

           o.estimatedStoriesListWidth = o.storyWidth * $("#estimated-list").size();

            o.acceptedStoriesListWidth = o.storyWidth * $("#accepted-list").size();

        },

        notifyPlanningPoker:function(){
            jQuery.icescrum.renderNotice("New Planning Poker session "+
                    "<a href=\"javascript:;\" disabled=\"true\" onClick=\"jQuery.icescrum.openWindow(\'planningPoker/display\')\">" +
                    "<button>Join</button>" +
                    "</a>","notice");
        } ,

        showResult:function(){
            $("#planning-poker-final-estimate").html("<div class=\"planning-poker-carte-result  ui-corner-all\"><div class=\"estimation\">17</div></div>");
            //<is:planningPokerFinalEstimate number=\"16\"/>
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