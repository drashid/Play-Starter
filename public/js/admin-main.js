//see: https://github.com/jrburke/requirejs/issues/235
require(['require', 'shim-config', 
      'libs/angular',
      'controller/controllers', 
      'controller/HealthCtrl',
      'controller/MetricCtrl', 
      'controller/AdminCtrl'], 
  function(require){ 
    require(
      [ //modules needed for admin panel 
        'libs/angular'
      ], 
      //set up front-end routes for single page application (e.g. no page reloading)
      function(angular){
        //top level module we're going to use in the admin panel,
        //depends on controllers module our controllers will be added to when they are loaded
        angular.module('admin', ['controllers'])
          .config(function($routeProvider){
            var asset = function(assetPath){ return window.SYSTEM.assetRoot + assetPath };
            $routeProvider.
              when('/timers', { templateUrl: asset('partials/timers.html'), controller: 'MetricCtrl' }).
              when('/meters', { templateUrl: asset('partials/meters.html'), controller: 'MetricCtrl' }).
              otherwise({ redirectTo:'/health', templateUrl: asset('partials/health.html'), controller: 'HealthCtrl'});
          })
          .filter('round', function(){
            return function(input, digits){
              return input.toFixed(digits);
            }
          });
      }
    );
});