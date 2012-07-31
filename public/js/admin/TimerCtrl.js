define(['controller/controllers', 'libs/underscore', 'libs/nv.d3', 'admin/metric-utils'], function(controllers, _, nv, utils){
  
  controllers.controller('TimerCtrl', ['$scope', '$http', 
    function TimerCtrl($scope, $http) {
      //load metrics 
      $scope.loadMetrics = function() {
        $http.get('/api/admin/metrics/fetch').success(function(data){
          //flatten metric array of arrays (one array per web node)
          $scope.fetchedMetrics = _.flatten(data);
          $scope.averagedMetrics = utils.averageMetrics($scope.fetchedMetrics)

          $scope.metrics = _chooseMetrics();
          _loadGraph();
        });
      };

      $scope.sortBy = function(fieldName){
        utils.sortBy($scope, fieldName, 'sortField', 'sortOrder');
        _showSortArrow(fieldName);
      }

      _showSortArrow = function(fieldName){
        $scope.sortArrow = $scope.sortOrder ? "▼" : "▲";
        $("th > span.sortArrow").css("visibility", "hidden");
        $("th#" + fieldName + " > span.sortArrow").css("visibility", "visible");
      }

      $scope.showID = function(){
        return $scope.averageNodes ? "hide-id" : "show-id";
      };

      _chooseMetrics = function(){
        return $scope.averageNodes ? $scope.averagedMetrics : $scope.fetchedMetrics;
      }

      _formatDataForGraph = function(averagedMetrics){
        var min = [],
            avg = [],
            median = [],
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
            median.push({ x: i, y: item.median, units: item.durationUnit, metricName: item.name });
            avg.push({ x: i, y: item.mean, units: item.durationUnit, metricName: item.name });
            max.push({ x: i, y: item.max, units: item.durationUnit, metricName: item.name });
          });

        return [
          { key: 'Min', values: min },
          { key: 'Median', values: median},
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
            // .scale(d3.scale.log()) Would be nice if this worked...
            .axisLabel('Time')
            .tickFormat(d3.format(',.1f'));

          d3.select('#chart1 svg')
            .datum(_formatDataForGraph($scope.averagedMetrics))
            .transition().duration(250).call(chart);

          nv.utils.windowResize(chart.update);

          return chart;
        });
      };

      //trigger metric processing (average or not average) on toggle
      $scope.$watch('averageNodes', function(value){
        $scope.metrics = _chooseMetrics();
      });

      //INIT
      $scope.averageNodes = true;
      $scope.loadMetrics();
      $scope.sortBy('median');
      $scope.sortOrder = true;
      _showSortArrow('median');
    }
  ]);

});