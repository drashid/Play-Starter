define(['controller/controllers', 'libs/underscore', 'libs/nv.d3'], function(controllers, _, nv){
  
  controllers.controller('MetricCtrl', ['$scope', '$http', 
    function MetricCtrl($scope, $http) {
      //load metrics 
      $scope.loadMetrics = function() {
        $http.get('/api/admin/metrics/fetch').success(function(data){
          //flatten metric array of arrays (one array per web node)
          $scope.fetchedMetrics = _.flatten(data);
          $scope.averagedMetrics = _averageMetrics($scope.fetchedMetrics)

          $scope.metrics = _chooseMetrics();
          _loadGraph();
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

      _averageMetrics = function(fetchedMetrics){
        return _.chain($scope.fetchedMetrics)
            .groupBy('name')
            .map(function(objList){ return _averageObjs(objList); })
            .value();
      }

      _chooseMetrics = function() {
        if($scope.averageNodes){
          return $scope.averagedMetrics;
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
        $scope.metrics = _chooseMetrics();
      });

      $scope.averageNodes = true;
      $scope.loadMetrics();

      $scope.sortTimerBy("name");
      $scope.sortMeterBy("count");
      $scope.timerSortOrder = false;
      $scope.meterSortOrder = true;

      _getGraphData = function(averagedMetrics){
        var min = [],
            avg = [],
            max = [];

        _.chain(averagedMetrics)
          .filter(function(item){
            switch(item.type){
              case 'TIMER': return true;
              default: return false;
            }
          })
          .each( function(item, i){ 
            min.push({ x: i, y: item.min, units: item.durationUnit, metricName: item.name });
            avg.push({ x: i, y: item.mean, units: item.durationUnit, metricName: item.name });
            max.push({ x: i, y: item.max, units: item.durationUnit, metricName: item.name });
          });

        return [
          { key: 'Min', values: min },
          { key: 'Average', values: avg },
          { key: 'Max', values: max }
        ];
      };
      
      _loadGraph = function(){ 
        nv.addGraph(function() {
          var chart = nv.models.multiBarChart();
          chart.showControls(false); //disable stacked
          chart.tooltipContent(function(key, x, y, e, graph){
            return '<h3>' + e.point.metricName + '</h3>' + 
                   '<p>' + y + ' ' + e.point.units + '</p>';
          });

          chart.xAxis
            .axisLabel('Metrics')
            .tickFormat(d3.format(',f'));

          chart.yAxis
            .axisLabel('Time')
            .tickFormat(d3.format(',.1f'));

          d3.select('#chart1 svg')
            .datum(_getGraphData($scope.averagedMetrics))
            .transition().duration(250).call(chart);

          nv.utils.windowResize(chart.update);

          return chart;
        });
      };
    }
  ]);

});