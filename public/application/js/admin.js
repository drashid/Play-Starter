angular.module('admin', []).
  config(function($routeProvider){
    var assetPath = window.SYSTEM.assetPath;
    $routeProvider.
      when('/timers', { templateUrl: assetPath + 'application/partials/timers.html', controller: 'MetricCtrl' }).
      when('/meters', { templateUrl: assetPath + 'application/partials/meters.html', controller: 'MetricCtrl' }).
      otherwise({ redirectTo:'/health', templateUrl: assetPath + 'application/partials/health.html', controller: 'HealthCtrl'});
  }).
  filter('round', function(){
    return function(input, digits){
      return input.toFixed(digits);
    }
  });

  function AdminCtrl($scope, $location) {
    // set nav button to active on route change
    $scope.$on('$routeChangeSuccess', function() {
      var current = $location.path();
      $scope.navTimersActive = current == '/timers' ? 'active' : '';
      $scope.navHealthActive = current == '/health' ? 'active' : '';
      $scope.navMetersActive = current == '/meters' ? 'active' : '';
    });
  };