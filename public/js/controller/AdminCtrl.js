define(['controller/controllers'], function(controllers){
  controllers.controller('AdminCtrl', ['$scope', '$location', function AdminCtrl($scope, $location) {
      // set nav button to active on route change
      $scope.$on('$routeChangeSuccess', function() {
        var current = $location.path();
        $scope.navTimersActive = current == '/timers' ? 'active' : '';
        $scope.navHealthActive = current == '/health' ? 'active' : '';
        $scope.navMetersActive = current == '/meters' ? 'active' : '';
      });     
    }
  ]);
});