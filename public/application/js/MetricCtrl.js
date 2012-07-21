function MetricCtrl($scope, $http) {

  averageObjs = function(objs){
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

  processMetrics = function() {
    if($scope.averageNodes){
      return _.chain($scope.fetchedMetrics)
        .groupBy('name')
        .map(function(objList){ return averageObjs(objList); })
        .value();
    }else{
      return $scope.fetchedMetrics;
    }
  };

  //load metrics 
  $scope.loadMetrics = function() {
    $http.get('/api/admin/metrics/fetch').success(function(data){
      //flatten metric array of arrays (one array per web node)
      $scope.fetchedMetrics = _.flatten(data);

      $scope.metrics = processMetrics();
    });
  };

  $scope.refresh = function() {
    $scope.loadMetrics();
  };

  sortBy = function(sortFieldName, sortField, orderField) {
    if($scope[sortField] === sortFieldName){
      $scope[orderField] = !$scope[orderField];
    }else{
      $scope[orderField] = false;
    }
    $scope[sortField] = sortFieldName;
  };

  $scope.sortTimerBy = function(fieldName){
    sortBy(fieldName, 'timerSortField', 'timerSortOrder');
  };

  $scope.sortMeterBy = function(fieldName){
    sortBy(fieldName, 'meterSortField', 'meterSortOrder');
  };

  $scope.showID = function(){
    return $scope.averageNodes ? "hide-id" : "show-id";
  };

  //
  //  On change events
  //

  //trigger metric processing (average or not average) on toggle
  $scope.$watch('averageNodes', function(value){
    $scope.metrics = processMetrics();
  });

  //
  // Initialization
  //

  $scope.averageNodes = true;
  $scope.loadMetrics();

  $scope.sortTimerBy("name");
  $scope.sortMeterBy("count");
  $scope.timerSortOrder = false;
  $scope.meterSortOrder = true;

};