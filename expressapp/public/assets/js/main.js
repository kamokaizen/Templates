var App = function() {
    var $lHtml, $lBody, $lPage, $lSidebar, $lSidebarScroll, $lSideOverlay, $lSideOverlayScroll;

    var uiInit = function() {
        $lHtml = jQuery('html');
        $lBody = jQuery('body');
        $lSidebar = jQuery('#sidebar');
        $lSidebarScroll = jQuery('#sidebar-scroll');
        $lSideOverlay = jQuery('#side-overlay');
        $lSideOverlayScroll = jQuery('#side-overlay-scroll');
        $lPage = jQuery('#page-container');
        // Initialize Tooltips
        jQuery('[data-toggle="tooltip"], .js-tooltip').tooltip({
            container: 'body',
            animation: false
        });

        // Initialize Popovers
        jQuery('[data-toggle="popover"], .js-popover').popover({
            container: 'body',
            animation: true,
            trigger: 'hover'
        });

        // Initialize Tabs
        jQuery('[data-toggle="tabs"] a, .js-tabs a').click(function(e) {
            e.preventDefault();
            jQuery(this).tab('show');
        });
    };
    var uiHandleScroll = function($mode) {
        var $windowW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;

        // Init scrolling
        if ($mode === 'init') {
            uiHandleScroll();

            var $sScrollTimeout;

            jQuery(window).on('resize orientationchange', function() {
                clearTimeout($sScrollTimeout);

                $sScrollTimeout = setTimeout(function() {
                    uiHandleScroll();
                }, 150);
            });
        } else {
            if ($windowW > 0 && $lPage.hasClass('side-scroll')) {
                jQuery($lSidebar).scrollLock('off');
                jQuery($lSideOverlay).scrollLock('off');

                if ($lSidebarScroll.length && (!$lSidebarScroll.parent('.slimScrollDiv').length)) {
                    $lSidebarScroll.slimScroll({
                        height: $lSidebar.outerHeight(),
                        color: '#fff',
                        size: '5px',
                        opacity: .35,
                        wheelStep: 15,
                        distance: '2px',
                        railVisible: false,
                        railOpacity: 1
                    });
                } else {
                    $lSidebarScroll
                        .add($lSidebarScroll.parent())
                        .css('height', $lSidebar.outerHeight());
                }

                if ($lSideOverlayScroll.length && (!$lSideOverlayScroll.parent('.slimScrollDiv').length)) {
                    $lSideOverlayScroll.slimScroll({
                        height: $lSideOverlay.outerHeight(),
                        color: '#000',
                        size: '5px',
                        opacity: .35,
                        wheelStep: 15,
                        distance: '2px',
                        railVisible: false,
                        railOpacity: 1
                    });
                } else {
                    $lSideOverlayScroll
                        .add($lSideOverlayScroll.parent())
                        .css('height', $lSideOverlay.outerHeight());
                }
            } else {
                jQuery($lSidebar).scrollLock();
                jQuery($lSideOverlay).scrollLock();

                if ($lSidebarScroll.length && $lSidebarScroll.parent('.slimScrollDiv').length) {
                    $lSidebarScroll
                        .slimScroll({
                            destroy: true
                        });
                    $lSidebarScroll
                        .attr('style', '');
                }

                if ($lSideOverlayScroll.length && $lSideOverlayScroll.parent('.slimScrollDiv').length) {
                    $lSideOverlayScroll
                        .slimScroll({
                            destroy: true
                        });
                    $lSideOverlayScroll
                        .attr('style', '');
                }
            }
        }
    };

    // Main navigation functionality
    var uiNav = function() {
        // When a submenu link is clicked
        jQuery('[data-toggle="nav-submenu"]').on('click', function(e) {
            // Stop default behaviour
            e.stopPropagation();

            // Get link
            var $link = jQuery(this);

            // Get link's parent
            var $parentLi = $link.parent('li');

            if ($parentLi.hasClass('open')) {
                $parentLi.removeClass('open');
            } else {
                $link
                    .closest('ul')
                    .find('> li')
                    .removeClass('open');

                $parentLi
                    .addClass('open');
            }


            if ($lHtml.hasClass('no-focus')) {
                $link.blur();
            }
        });
    };

    var uiLayout = function() {
        uiHandleScroll('init');

        jQuery('[data-toggle="layout"]').on('click', function() {
            var $btn = jQuery(this);

            uiLayoutApi($btn.data('action'));

            if ($('html').hasClass('no-focus')) {
                $btn.blur();
            }
        });
    };

    var uiLayoutApi = function($mode) {
        var $windowW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        switch ($mode) {
            case 'sidebar_pos_toggle':
                $lPage.toggleClass('sidebar-l sidebar-r');
                break;
            case 'sidebar_pos_left':
                $lPage
                    .removeClass('sidebar-r')
                    .addClass('sidebar-l');
                break;
            case 'sidebar_pos_right':
                $lPage
                    .removeClass('sidebar-l')
                    .addClass('sidebar-r');
                break;
            case 'sidebar_toggle':
                if ($windowW > 991) {
                    $lPage.toggleClass('sidebar-o');
                } else {
                    $lPage.toggleClass('sidebar-o-xs');
                }
                break;
            case 'sidebar_open':
                if ($windowW > 991) {
                    $lPage.addClass('sidebar-o');
                } else {
                    $lPage.addClass('sidebar-o-xs');
                }
                break;
            case 'sidebar_close':
                if ($windowW > 991) {
                    $lPage.removeClass('sidebar-o');
                } else {
                    $lPage.removeClass('sidebar-o-xs');
                }
                break;
            case 'sidebar_mini_toggle':
                if ($windowW > 991) {
                    $lPage.toggleClass('sidebar-mini');
                }
                break;
            case 'sidebar_mini_on':
                if ($windowW > 991) {
                    $lPage.addClass('sidebar-mini');
                }
                break;
            case 'sidebar_mini_off':
                if ($windowW > 991) {
                    $lPage.removeClass('sidebar-mini');
                }
                break;
            case 'side_overlay_toggle':
                $lPage.toggleClass('side-overlay-o');
                break;
            case 'side_overlay_open':
                $lPage.addClass('side-overlay-o');
                break;
            case 'side_overlay_close':
                $lPage.removeClass('side-overlay-o');
                break;
            case 'side_overlay_hoverable_toggle':
                $lPage.toggleClass('side-overlay-hover');
                break;
            case 'side_overlay_hoverable_on':
                $lPage.addClass('side-overlay-hover');
                break;
            case 'side_overlay_hoverable_off':
                $lPage.removeClass('side-overlay-hover');
                break;
            case 'header_fixed_toggle':
                $lPage.toggleClass('header-navbar-fixed');
                break;
            case 'header_fixed_on':
                $lPage.addClass('header-navbar-fixed');
                break;
            case 'header_fixed_off':
                $lPage.removeClass('header-navbar-fixed');
                break;
            case 'side_scroll_toggle':
                $lPage.toggleClass('side-scroll');
                uiHandleScroll();
                break;
            case 'side_scroll_on':
                $lPage.addClass('side-scroll');
                uiHandleScroll();
                break;
            case 'side_scroll_off':
                $lPage.removeClass('side-scroll');
                uiHandleScroll();
                break;
            default:
                return false;
        }
    };

    var scrollMap = function() {
        var x = false;
        $('#bg-margin .arrow-map').on('click', function() {
            if (!x) {
                //function
                x = true;
                $(this).find('i').attr('class', 'fa fa-arrow-circle-up');
                $('#bg-margin').animate({
                    marginTop: 550
                }, 500);
            } else {
                //function
                x = false;
                $('#bg-margin').animate({
                    marginTop: 245
                }, 500);
                $(this).find('i').attr('class', 'fa fa-arrow-circle-down');
            }
        });
    };

    var sysInfScroll = function() {
        $('.systemInfoContentInner').slimScroll({
            height: 240,
            color: '#353535',
            size: '5px',
            opacity: .25,
            wheelStep: 15,
            distance: '2px',
            railVisible: false,
            railOpacity: 1
        });
    };

    var fscreen = function() {
        $('#bg-margin .fscreen').on('click', function() {
            $('#mapContent').css("z-index", "100");
            $('body').css("overflow", "hidden");
            $('#header-navbar').css("z-index", "2");
            $('#map, #s, #c').css({
                left: 0,
                top: 0
            });
            window.scrollTo(400, 0);
            $('.closeFscreen').css("display", "block");
        });

        $('#bg-margin .closeFscreen').on('click', function() {
            $('#mapContent').css("z-index", "");
            $('body').css("overflow", "visible");
            $('#header-navbar').css("z-index", "222");
            $('#map, #s, #c').css({
                left: 10,
                top: 30
            });
            $(this).css("display", "none");
        });

    };

    var uiForms = function() {
        jQuery('.form-material.floating > .form-control').each(function() {
            var $input = jQuery(this);
            var $parent = $input.parent('.form-material');

            if ($input.val()) {
                $parent.addClass('open');
            }

            $input.on('change', function() {
                if ($input.val()) {
                    $parent.addClass('open');
                } else {
                    $parent.removeClass('open');
                }
            });
        });
    };

    var initSelect2 = function() {
        if ($.fn.select2) {
            $('.select2').select2({
                minimumResultsForSearch: -1,
                allowClear: true,
                placeholder: "Select..."
            })
        }
    };

    var multipleSelect = function() {
        if ($('.multi-select').length) {
            $('.multi-select').multipleSelect({
                placeholder: "Click and Select",
                multipleWidth: '100%',
                width: '100%'
            });
        }
        if ($('.radio-select').length) {
            $('.radio-select').multipleSelect({
                single: true,
                placeholder: "Click and Select",
                multipleWidth: '100%',
                width: '100%'
            })
        }
    }    

    /*var iframe = function() {
        if ($('iframe').length) {
            iFrameResize({
                log: false,                  
                inPageLinks: true
            });
        }
    }*/

    var initDataTableHelper = function() {
        if ($.fn.dataTable) {
            $('[data-provide="datatable"]').each(function() {
                $(this).addClass('dataTable-helper')
                var defaultOptions = {
                        paginate: false,
                        search: false,
                        info: false,
                        lengthChange: false,
                        scrollX: true,
                        displayRows: 10
                    },
                    dataOptions = $(this).data(),
                    helperOptions = $.extend(defaultOptions, dataOptions),
                    $thisTable,
                    tableConfig = {}

                tableConfig.iDisplayLength = helperOptions.displayRows
                tableConfig.bFilter = true
                tableConfig.bSort = true
                tableConfig.bPaginate = false
                tableConfig.bLengthChange = false
                tableConfig.bInfo = false

                if (helperOptions.paginate) {
                    tableConfig.bPaginate = true
                }
                if (helperOptions.lengthChange) {
                    tableConfig.bLengthChange = true
                }
                if (helperOptions.info) {
                    tableConfig.bInfo = true
                }
                if (helperOptions.search) {
                    $(this).parent().removeClass('datatable-hidesearch')
                }

                tableConfig.aaSorting = []
                tableConfig.aoColumns = []

                $(this).find('thead tr th').each(function(index, value) {
                    var sortable = ($(this).data('sortable') === true) ? true : false
                    tableConfig.aoColumns.push({
                        'bSortable': sortable
                    })

                    if ($(this).data('direction')) {
                        tableConfig.aaSorting.push([index, $(this).data('direction')])
                    }
                })

                // Create the datatable
                $thisTable = $(this).dataTable(tableConfig)

                if (!helperOptions.search) {
                    $thisTable.parent().find('.dataTables_filter').remove()
                }

                var filterableCols = $thisTable.find('thead th').filter('[data-filterable="true"]')

                if (filterableCols.length > 0) {
                    var columns = $thisTable.fnSettings().aoColumns,
                        $row, th, $col, showFilter

                    $row = $('<tr>', {
                        cls: 'dataTable-filter-row'
                    }).appendTo($thisTable.find('thead'))

                    for (var i = 0; i < columns.length; i++) {
                        $col = $(columns[i].nTh.outerHTML)
                        showFilter = ($col.data('filterable') === true) ? 'show' : 'hide'

                        th = '<th class="' + $col.prop('class') + '">'
                        th += '<input type="text" class="form-control input-sm ' + showFilter + '" placeholder="' + $col.text() + '">'
                        th += '</th>'
                        $row.append(th)
                    }

                    $row.find('th').removeClass('sorting sorting_disabled sorting_asc sorting_desc sorting_asc_disabled sorting_desc_disabled')

                    $thisTable.find('thead input').keyup(function() {
                        $thisTable.fnFilter(this.value, $thisTable.oApi._fnVisibleToColumnIndex(
                            $thisTable.fnSettings(), $thisTable.find('thead input[type=text]').index(this)))
                    })

                    $thisTable.addClass('datatable-columnfilter')
                }
            })

            $('.dataTables_filter input').prop('placeholder', 'Search...')
        }
    }


    // Table sections functionality
    var uiHelperTableToolsSections = function(){
        if ($('.js-table-sections').length) {
            var $table      = jQuery('.js-table-sections');
            var $tableRows  = jQuery('.js-table-sections-header > tr > td:not(:last-child)', $table);

            // When a row is clicked in tbody.js-table-sections-header
            $tableRows.click(function(e) {
                var $row    = jQuery(this).parent();
                var $tbody  = $row.parent('tbody');

                if (! $tbody.hasClass('open')) {
                    jQuery('tbody', $table).removeClass('open');
                }

                $tbody.toggleClass('open');
            });
        };
    };

    // Checkable table functionality
    var uiHelperTableToolsCheckable = function() {
        if ($('.js-table-checkable').length) {
            var $table = jQuery('.js-table-checkable');

            // When a checkbox is clicked in thead
            jQuery('thead input:checkbox', $table).click(function() {
                var $checkedStatus = jQuery(this).prop('checked');

                // Check or uncheck all checkboxes in tbody
                jQuery('tbody input:checkbox', $table).each(function() {
                    var $checkbox = jQuery(this);

                    $checkbox.prop('checked', $checkedStatus);
                    uiHelperTableToolscheckRow($checkbox, $checkedStatus);
                });
            });

            // When a checkbox is clicked in tbody
            jQuery('tbody input:checkbox', $table).click(function() {
                var $checkbox = jQuery(this);

                uiHelperTableToolscheckRow($checkbox, $checkbox.prop('checked'));
            });

            // When a row is clicked in tbody
            jQuery('tbody > tr', $table).click(function(e) {
                if (e.target.type !== 'checkbox'
                        && e.target.type !== 'button'
                        && e.target.tagName.toLowerCase() !== 'a'
                        && !jQuery(e.target).parent('label').length) {
                    var $checkbox       = jQuery('input:checkbox', this);
                    var $checkedStatus  = $checkbox.prop('checked');

                    $checkbox.prop('checked', ! $checkedStatus);
                    uiHelperTableToolscheckRow($checkbox, ! $checkedStatus);
                }
            });
        };
    };

    // Checkable table functionality helper - Checks or unchecks table row
    var uiHelperTableToolscheckRow = function($checkbox, $checkedStatus) {
        if ($checkedStatus) {
            $checkbox
                .closest('tr')
                .addClass('active');
        } else {
            $checkbox
                .closest('tr')
                .removeClass('active');
        }
    };


    return {
        init: function() {
            uiInit();
            uiNav(); 
            uiLayout();
            sysInfScroll();
            scrollMap();
            fscreen();
            uiForms();
            initSelect2();
            initDataTableHelper();
            //multipleSelect();
            //iframe();
            uiHelperTableToolsSections();
        }       
    };
}();

jQuery(function() {
    App.init();
    $.fn.modal.prototype.constructor.Constructor.DEFAULTS.backdrop = 'static';
});

$(window).load(function() {
    $('#status').fadeOut();
    $('#preloader').delay(350).fadeOut('slow');
    $('body').delay(350).css({
        'overflow': 'visible'
    });
});