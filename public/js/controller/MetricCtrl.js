define(['controller/controllers', 'libs/underscore', 'libs/nv.d3'], function(controllers, _, nv){
  
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

      function exampleData() {
        return [
          { key: 'Min', values:[{x: 1, y:10, metricName:'com.cray.metrics.HitIt', units:'ms'}, {x: 2, y:4, metricName:'com.cray.metrics.HitIt', units:'ms'}] },
          { key: 'Average', values:[{x: 1, y:72, metricName:'com.cray.metrics.HitIt', units:'ms'}, {x: 2, y:78, metricName:'com.cray.metrics.HitIt', units:'ms'}] },
          { key: 'Max', values:[{x: 1, y:100, metricName:'com.cray.metrics.HitIt', units:'ms'}, {x: 2, y:97, metricName:'com.cray.metrics.HitIt', units:'ms'}] }                 
        ];
      };
      
      nv.addGraph(function() {
        var chart = nv.models.multiBarChart();
        chart.showControls(false); //disable stacked
        chart.tooltipContent(function(key, x, y, e, graph){
          return '<h3>' + e.point.metricName + '</h3>' + 
                 '<p>' + y + ' ' + e.point.units + '</p>';
        });

        chart.xAxis
          .tickFormat(d3.format(',f'));

        chart.yAxis
          .tickFormat(d3.format(',.1f'));

        d3.select('#chart1 svg')
          .datum(exampleData())
          .transition().duration(500).call(chart);

        nv.utils.windowResize(chart.update);

        return chart;
      });
    }
  ]);

});