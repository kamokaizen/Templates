$(function() {

//	  Morris.Line({
//		  element: 'notification_activity_line_chart',
//		  data: [{"period":"2015-05-21","mailSeen":0,"passwordChecked":11,"passwordChanged":2},{"period":"2015-05-22","mailSeen":0,"passwordChecked":2,"passwordChanged":0}],
//		  xkey: 'period',
//		  ykeys: ['mailSeen', 'passwordChecked', 'passwordChanged'],
//		  labels: ['E-Postayı Gördü', 'Doğrulama ekranını gördü', 'Şifresini ekledi'],
//		  pointSize: 2,
//		  hideHover: 'auto',
//		  resize: true
//		});	
	
//  Morris.Line({
//  element: 'morris-area-chart',
//  data: [{
//      period: Date.parse('2015-01-01'),
//      MAIL_SEEN: 2,
//      PASSWORD_CHECK_CLICKED: 0,
//      PASSWORD_CHANGED: 0
//  }, {
//	  period: Date.parse('2015-01-02'),
//      MAIL_SEEN: 1,
//      PASSWORD_CHECK_CLICKED: 2,
//      PASSWORD_CHANGED: 1
//  }, {
//	  period: Date.parse('2015-01-03'),
//      MAIL_SEEN: 3,
//      PASSWORD_CHECK_CLICKED: 1,
//      PASSWORD_CHANGED: 2
//  }, {
//	  period: Date.parse('2015-01-04'),
//      MAIL_SEEN: 4,
//      PASSWORD_CHECK_CLICKED: 2,
//      PASSWORD_CHANGED: 5
//  }, {
//	  period: Date.parse('2015-01-05'),
//      MAIL_SEEN: 6,
//      PASSWORD_CHECK_CLICKED: 7,
//      PASSWORD_CHANGED: 9
//  }, {
//	  period: Date.parse('2015-01-06'),
//      MAIL_SEEN: 2,
//      PASSWORD_CHECK_CLICKED: 2,
//      PASSWORD_CHANGED: 2
//  }, {
//	  period: Date.parse('2015-01-07'),
//      MAIL_SEEN: 3,
//      PASSWORD_CHECK_CLICKED: 5,
//      PASSWORD_CHANGED: 7
//  }, {
//	  period: Date.parse('2015-01-08'),
//      MAIL_SEEN: 7,
//      PASSWORD_CHECK_CLICKED: 5,
//      PASSWORD_CHANGED: 3
//  }, {
//	  period: Date.parse('2015-01-09'),
//      MAIL_SEEN: 1,
//      PASSWORD_CHECK_CLICKED: 2,
//      PASSWORD_CHANGED: 1
//  }, {
//	  period: Date.parse('2015-01-10'),
//      MAIL_SEEN: 3,
//      PASSWORD_CHECK_CLICKED: 2,
//      PASSWORD_CHANGED: 1
//  }],
//  xkey: 'period',
//  ykeys: ['MAIL_SEEN', 'PASSWORD_CHECK_CLICKED', 'PASSWORD_CHANGED'],
//  labels: ['E-Postayı Gördü', 'Doğrulama ekranını gördü', 'Şifresini ekledi'],
//  pointSize: 2,
//  hideHover: 'auto',
//  resize: true
//});
	
//    Morris.Area({
//        element: 'morris-area-chart',
//        data: [{
//            period: '2010 Q1',
//            iphone: 2666,
//            ipad: null,
//            itouch: 2647
//        }, {
//            period: '2010 Q2',
//            iphone: 2778,
//            ipad: 2294,
//            itouch: 2441
//        }, {
//            period: '2010 Q3',
//            iphone: 4912,
//            ipad: 1969,
//            itouch: 2501
//        }, {
//            period: '2010 Q4',
//            iphone: 3767,
//            ipad: 3597,
//            itouch: 5689
//        }, {
//            period: '2011 Q1',
//            iphone: 6810,
//            ipad: 1914,
//            itouch: 2293
//        }, {
//            period: '2011 Q2',
//            iphone: 5670,
//            ipad: 4293,
//            itouch: 1881
//        }, {
//            period: '2011 Q3',
//            iphone: 4820,
//            ipad: 3795,
//            itouch: 1588
//        }, {
//            period: '2011 Q4',
//            iphone: 15073,
//            ipad: 5967,
//            itouch: 5175
//        }, {
//            period: '2012 Q1',
//            iphone: 10687,
//            ipad: 4460,
//            itouch: 2028
//        }, {
//            period: '2012 Q2',
//            iphone: 8432,
//            ipad: 5713,
//            itouch: 1791
//        }],
//        xkey: 'period',
//        ykeys: ['iphone', 'ipad', 'itouch'],
//        labels: ['iPhone', 'iPad', 'iPod Touch'],
//        pointSize: 2,
//        hideHover: 'auto',
//        resize: true
//    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Download Sales",
            value: 12
        }, {
            label: "In-Store Sales",
            value: 30
        }, {
            label: "Mail-Order Sales",
            value: 20
        }],
        resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2006',
            a: 100,
            b: 90
        }, {
            y: '2007',
            a: 75,
            b: 65
        }, {
            y: '2008',
            a: 50,
            b: 40
        }, {
            y: '2009',
            a: 75,
            b: 65
        }, {
            y: '2010',
            a: 50,
            b: 40
        }, {
            y: '2011',
            a: 75,
            b: 65
        }, {
            y: '2012',
            a: 100,
            b: 90
        }],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Series A', 'Series B'],
        hideHover: 'auto',
        resize: true
    });

});
