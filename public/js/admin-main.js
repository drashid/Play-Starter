require({
    shim: {
      'libs/underscore': { exports: '_' },
      'libs/angular': { exports: 'angular' },
      'libs/jquery': { exports: '$' },
      'libs/bootstrap-tooltip': ['libs/jquery'], 
      'libs/bootstrap-popover': ['libs/bootstrap-tooltip']
    }
  },
  [ 
    'admin', 
    'controller/controllers', 
    'controller/HealthCtrl',
    'controller/MetricCtrl', 
    'controller/AdminCtrl'
  ]
);