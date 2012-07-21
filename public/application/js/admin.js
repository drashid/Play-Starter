angular.module('admin', []).
  config(function($routeProvider){
    $routeProvider.
      when('/admin/timers', { templateUrl: '/assets/application/partials/timers.html' }).
      when('/admin/meters', { templateUrl: '/assets/application/partials/meters.html' }).
      otherwise({ redirectTo:'/admin/health', templateUrl: '/assets/application/partials/health.html'});
  });