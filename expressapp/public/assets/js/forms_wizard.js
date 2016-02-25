var BaseFormWizard = function() {
    var initWizardSimple = function(){
        jQuery('.js-wizard-simple').bootstrapWizard({
            'tabClass': '',
            'firstSelector': '.wizard-first',
            'previousSelector': '.wizard-prev',
            'nextSelector': '.wizard-next',
            'lastSelector': '.wizard-last',
            'onTabShow': function($tab, $navigation, $index) {
		var $total      = $navigation.find('li').length;
		var $current    = $index + 1;
		var $percent    = ($current/$total) * 100;

                var $wizard     = $navigation.parents('.block');
                var $progress   = $wizard.find('.wizard-progress > .progress-bar');
                var $btnPrev    = $wizard.find('.wizard-prev');
                var $btnNext    = $wizard.find('.wizard-next');
                var $btnFinish  = $wizard.find('.wizard-finish');

		if ($progress) {
                    $progress.css({ width: $percent + '%' });
                }

		if($current >= $total) {
                    $btnNext.hide();
                    $btnFinish.show();
		} else {
                    $btnNext.show();
                    $btnFinish.hide();
		}
            }
        });
    };



    return {
        init: function () {
            initWizardSimple();
        }
    };
}();

jQuery(function(){ BaseFormWizard.init(); });