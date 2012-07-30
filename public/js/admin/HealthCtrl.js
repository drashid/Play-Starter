define(['controller/controllers', 'libs/jquery', 
        'libs/bootstrap-tooltip', 'libs/bootstrap-popover'], function(controllers, $){
  
  controllers.controller('HealthCtrl', ['$scope', '$http',
    function HealthCtrl($scope, $http) {

      $scope.loadHealth = function(){
          $http.get('/api/admin/metrics/health').success(function(data){
            $scope.health = data;
          });
      };

      $scope.clearMachine = function(machineName){
        //hide tooltip associated with this node, since the button will be removed
        $('[rel=tooltip]').tooltip('hide');
        $http.post('/api/admin/metrics/remove', {'machine': machineName}).success(function(){
          $scope.refresh();
        });
      };

      $scope.refresh = function() {
        $scope.loadHealth();
      };

      //example input {"healthy":true,"message":null,"error":null}
      $scope.healthStatus = function(health) {
        return health.healthy ? "alert-success" : "alert-error";
      }

      $scope.healthInfo = function(health){
        var icon = _healthIcon(health);
        if(health.message){
          return "<i class=\"" + icon +  "\" rel=\"popover\" data-content=\"" 
            + health.message + "\" data-original-title=\"Status Message\"></i>";
        }else{
          return "<i class=\"" + icon + "\"></i>";
        }
      };

      $scope.diffTime = function(time){
        return Math.round((new Date().getTime() - time)/1000);
      }

      //example input {"healthy":true,"message":null,"error":null}
      _healthIcon = function(health) {
        return health.healthy ? "icon-ok" : "icon-remove";
      }

      //
      // Initialization
      //

      //after template rendering related to model changes on health, add js popover 
      $scope.$watch('health', function(){
        $scope.$evalAsync(function(){
          $('[rel=popover]').popover({
            placement: 'bottom'
          });
          $('[rel=tooltip]').tooltip({
            placement: 'right'
          });
        });
      });

      $scope.loadHealth();  

    }
  ]);

});