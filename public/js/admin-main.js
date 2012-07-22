require({
    shim: {
      'libs/angular': { exports: 'angular' },
      'libs/jquery': {exports: '$'},
      'libs/underscore': {exports: '_'}
    }
  },
  ['admin', 
    'controller/controllers', 
    'controller/HealthCtrl',
    'controller/MetricCtrl', 
    'controller/AdminCtrl'], 
    function(app){
      app.run();
    }
);