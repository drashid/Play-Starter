require({
    //shims for non-requirejs libraries
    shim: {
      'libs/underscore': { exports: '_' },
      'libs/angular': { exports: 'angular' },
      'libs/jquery': { exports: '$' },
      'libs/bootstrap-tooltip': ['libs/jquery'], 
      'libs/bootstrap-popover': ['libs/bootstrap-tooltip']
    }
  },
  [ //modules needed for admin panel 
    'libs/angular', 
    'controller/controllers', 
    'controller/HealthCtrl',
    'controller/MetricCtrl', 
    'controller/AdminCtrl'
  ], 
  //set up front-end routes for single page application (e.g. no page reloading)
  function(angular){
    //top level module we're going to use in the admin panel,
    //depends on controllers module our controllers will be added to when they are loaded
    angular.module('admin', ['controllers'])
      .config(function($routeProvider){
        var assetPath = window.SYSTEM.assetPath;
        $routeProvider.
          when('/timers', { templateUrl: assetPath + 'partials/timers.html', controller: 'MetricCtrl' }).
          when('/meters', { templateUrl: assetPath + 'partials/meters.html', controller: 'MetricCtrl' }).
          otherwise({ redirectTo:'/health', templateUrl: assetPath + 'partials/health.html', controller: 'HealthCtrl'});
      })
      .filter('round', function(){
        return function(input, digits){
          return input.toFixed(digits);
        }
      });
  }
);