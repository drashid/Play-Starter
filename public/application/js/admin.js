angular.module('admin', []).
  config(function($routeProvider){
    $routeProvider.
      when('/admin/timers', { templateUrl: '/assets/application/partials/timers.html', controller: 'MetricCtrl' }).
      when('/admin/meters', { templateUrl: '/assets/application/partials/meters.html', controller: 'MetricCtrl' }).
      otherwise({ redirectTo:'/admin/health', templateUrl: '/assets/application/partials/health.html', controller: 'HealthCtrl'});
  });

  function AdminCtrl($scope, $location) {
    // set nav button to active on route change
    $scope.$on('$routeChangeSuccess', function() {
      var current = $location.path();
      $scope.navTimersActive = current == '/admin/timers' ? 'active' : '';
      $scope.navHealthActive = current == '/admin/health' ? 'active' : '';
      $scope.navMetersActive = current == '/admin/meters' ? 'active' : '';
    });
  };