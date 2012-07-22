define(['controller/controllers', 'libs/underscore'], function(controllers, _){
  
  controllers.controller('MetricCtrl', ['$scope', '$http', 
    function MetricCtrl($scope, $http) {
      //load metrics 
      $scope.loadMetrics = function() {
        $http.get('/api/admin/metrics/fetch').success(function(data){
          //flatten metric array of arrays (one array per web node)
          $scope.fetchedMetrics = _.flatten(data);

          $scope.metrics = _processMetrics();
        });
      };

      $scope.refresh = function() {
        $scope.loadMetrics();
      };

      $scope.sortTimerBy = function(fieldName){
        _sortBy(fieldName, 'timerSortField', 'timerSortOrder');
      };

      $scope.sortMeterBy = function(fieldName){
        _sortBy(fieldName, 'meterSortField', 'meterSortOrder');
      };

      $scope.showID = function(){
        return $scope.averageNodes ? "hide-id" : "show-id";
      };

      _averageObjs = function(objs){
        var size = _.size(objs);
        if(size == 0){
          return {};
        }

        var result = {};
        var keys = _.keys(objs[0]);
        _.each(keys, function(key){
            if(_.isNumber(objs[0][key])){
              var sum = _.chain(objs)
                .pluck(key)
                .reduce(function(memo, num){ return memo + num; }, 0)
                .value();
              result[key] = sum / size;
            }else{
              result[key] = objs[0][key];
            }
          });
        return result;
      };

      _processMetrics = function() {
        if($scope.averageNodes){
          return _.chain($scope.fetchedMetrics)
            .groupBy('name')
            .map(function(objList){ return _averageObjs(objList); })
            .value();
        }else{
          return $scope.fetchedMetrics;
        }
      };

      _sortBy = function(sortFieldName, sortField, orderField) {
        if($scope[sortField] === sortFieldName){
          $scope[orderField] = !$scope[orderField];
        }else{
          $scope[orderField] = false;
        }
        $scope[sortField] = sortFieldName;
      };

      //
      // Initialization
      //

      //trigger metric processing (average or not average) on toggle
      $scope.$watch('averageNodes', function(value){
        $scope.metrics = _processMetrics();
      });

      $scope.averageNodes = true;
      $scope.loadMetrics();

      $scope.sortTimerBy("name");
      $scope.sortMeterBy("count");
      $scope.timerSortOrder = false;
      $scope.meterSortOrder = true;
    }
  ]);

});