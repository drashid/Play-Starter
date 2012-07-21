angular.module('admin', []).
  config(function($routeProvider){
    $routeProvider.
      when('/admin/timers', { template: 'partials/timers.html' }).
      when('/admin/meters', { template: 'partials/meters.html' }).
      otherwise({ redirectTo:'/admin/health', template: 'partials/health.html'});
  });