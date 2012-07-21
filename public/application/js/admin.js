angular.modules('admin', []).
  config(function($routeProvider){
    $routeProvider.
      when('/admin/timers', {
        template: 'partials/timers.html'
      })
  });