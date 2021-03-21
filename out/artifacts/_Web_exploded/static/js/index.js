;(function(){
    var finput = function(obj) {
        this.obj = obj;
        this.input = this.obj.find('input');
        this.label = this.obj.find('label');

        var that = this;

        this.input.on('focus',function(){
            that.focus($(this));
        })

        this.input.on('blur',function() {
            that.blur($(this));
        })

    }
    finput.prototype = {
        focus : function(obj) {
            obj.addClass('active')
                .siblings('label')
                .addClass('active')
                .parent('.input-wrap')
                .siblings()
                .children('input')
                .removeClass('active')
        },
        blur : function(obj) {
            if(obj.val().length > 0) {
                obj.addClass('active');
            }else {
                obj.removeClass('active')
                    .siblings('label')
                    .removeClass('active');
            }
        }
    }

    $.fn.extend({
        finput : function(){
            this.each(function() {
                new finput($(this));
            })
            return this;
        }
    })

    window.finput = finput;

})(jQuery);