angular.module('admin', []).
  config(function($routeProvider){
    $routeProvider.
      when('/admin/timers', {
        template: 'partials/timers.html'
      })
  });