angular.module('admin', []).
  config(function($routeProvider){
    var assetPath = window.ASSET_PATH;
    $routeProvider.
      when('/admin/timers', { templateUrl: assetPath + 'application/partials/timers.html', controller: 'MetricCtrl' }).
      when('/admin/meters', { templateUrl: assetPath + 'application/partials/meters.html', controller: 'MetricCtrl' }).
      otherwise({ redirectTo:'/admin/health', templateUrl: assetPath + 'application/partials/health.html', controller: 'HealthCtrl'});
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
      $scope.navTimersActive = current == '/admin/timers' ? 'active' : '';
      $scope.navHealthActive = current == '/admin/health' ? 'active' : '';
      $scope.navMetersActive = current == '/admin/meters' ? 'active' : '';
    });
  };