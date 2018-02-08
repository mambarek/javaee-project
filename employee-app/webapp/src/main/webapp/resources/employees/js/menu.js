jQuery(function($) {

    //$('.form-signin').draggable();

    $('.navbar-toggler').click(function() {

        this.classList.toggle("change");
        // reset colors
        // bars sind normaleweise auf dunklem Hintergrund
        // weiß ist die default Farbe s. CSS 
        $('.bar-top').css('background-color', '#fff');
        $('.bar-bottom').css('background-color', '#fff');

        if ($('.navbar-collapse').hasClass('right-align')) {
            // zeige/verstecke menue
            $('.navbar-collapse').toggleClass('right');
            // kreuz auf hellem hintergrund, also mach in schwarz
            // damit bessersichtbar
            $('.change .bar-top').css('background-color', '#000');
            $('.change .bar-bottom').css('background-color', '#000');
        }

        if ($('.navbar-collapse').hasClass('left-align')) {
            $('.navbar-collapse').toggleClass('left');
            $('.overlay').toggleClass('left');
            $('#main-content').toggleClass('blurry-text');
        }

        $('.navbar-toggler').toggleClass('indexcity transparent inverse');
        //$('.navbar-toggle').toggleClass('hidden');
    });

    $('.overlay').off('click').on('click',function(){
        $('.navbar-toggler').toggleClass("change");

        if ($('.navbar-collapse').hasClass('right-align')) {
            // zeige/verstecke menue
            $('.navbar-collapse').toggleClass('right');
            // kreuz auf hellem hintergrund, also mach in schwarz
            // damit bessersichtbar
            $('.change .bar-top').css('background-color', '#000');
            $('.change .bar-bottom').css('background-color', '#000');
        }

        if ($('.navbar-collapse').hasClass('left-align')) {
            $('.navbar-collapse').toggleClass('left');
            $('.overlay').toggleClass('left');
            $('#main-content').toggleClass('blurry-text');
        }
    })
});