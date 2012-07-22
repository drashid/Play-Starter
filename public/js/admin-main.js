require({
    shim: {
      'libs/angular': { exports: 'angular' },
      'libs/jquery': { exports: '$' },
      'libs/bootstrap-tooltip': ['libs/jquery'], 
      'libs/bootstrap-popover': ['libs/bootstrap-tooltip'], 
      'libs/underscore': { exports: '_' }
    }
  },
  ['admin', 
    'controller/controllers', 
    'controller/HealthCtrl',
    'controller/MetricCtrl', 
    'controller/AdminCtrl'], 
    function(){ /* don't need to do anything here */ }
);