function HealthCtrl($scope, $http) {

  $scope.loadHealth = function(){
      $http.get('/api/admin/metrics/health').success(function(data){
      $scope.health = data;
      });
  };

  $scope.refresh = function() {
    $scope.loadHealth();
  };

  $scope.clearMachine = function(machineName){
    $http.post('/api/admin/metrics/remove', {'machine': machineName}).success(function(){
      $scope.refresh();
    });
  };

  //example input {"healthy":true,"message":null,"error":null}
  $scope.healthStatus = function(health) {
    return health.healthy ? "alert-success" : "alert-error";
  }

  //example input {"healthy":true,"message":null,"error":null}
  healthIcon = function(health) {
    return health.healthy ? "icon-ok" : "icon-remove";
  }

  $scope.healthInfo = function(health){
    var icon = healthIcon(health);
    if(health.message){
      return "<i class=\"" + icon +  "\" rel=\"popover\" data-content=\"" 
        + health.message + "\" data-original-title=\"Status Message\"></i>";
    }else{
      return "<i class=\"" + icon + "\"></i>";
    }
  };

  //after template rendering related to model changes on health, add js popover 
  $scope.$watch('health', function(){
    $scope.$evalAsync(function(){
      $('[rel=popover]').popover({
        placement: 'bottom'
      });
    });
  });

  //
  // Initialization
  //

  $scope.loadHealth();  

};