define(['libs/angular'], function(angular){
  angular.module('admin', ['controllers']).
    config(function($routeProvider){
      var assetPath = window.SYSTEM.assetPath;
      $routeProvider.
        when('/timers', { templateUrl: assetPath + 'partials/timers.html', controller: 'MetricCtrl' }).
        when('/meters', { templateUrl: assetPath + 'partials/meters.html', controller: 'MetricCtrl' }).
        otherwise({ redirectTo:'/health', templateUrl: assetPath + 'partials/health.html', controller: 'HealthCtrl'});
    }).
    filter('round', function(){
      return function(input, digits){
        return input.toFixed(digits);
      }
    });
});