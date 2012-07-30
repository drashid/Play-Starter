define(['controller/controllers', 'libs/underscore', 'libs/nv.d3', 'admin/metric-utils'], function(controllers, _, nv, utils){
  
  controllers.controller('MeterCtrl', ['$scope', '$http', 
    function MeterCtrl($scope, $http) {
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
      };

      $scope.showID = function(){
        return $scope.averageNodes ? "hide-id" : "show-id";
      };

      _chooseMetrics = function(){
        return $scope.averageNodes ? $scope.averagedMetrics : $scope.fetchedMetrics;
      }

      _formatDataForGraph = function(averagedMetrics){
        var count = [];

        _.each(averagedMetrics, function(item, i){ 
            count.push({ x: i, y: item.count, metricName: item.name });
          });

        return [
          { key: 'Count', values: count }
        ];
      };
      
      _loadGraph = function(){ 
        nv.addGraph(function() {
          var chart = nv.models.multiBarChart();
          chart.showControls(false); //disable stacked
          chart.showLegend(false);
          chart.tooltipContent(function(key, x, y, e, graph){
            return '<h3>' + e.point.metricName + '</h3>' + 
                   '<p>' + y + '</p>';
          });

          chart.xAxis
            .axisLabel('Metrics')
            .tickFormat(d3.format(',f'));

          chart.yAxis
            .axisLabel('Count')
            .tickFormat(d3.format(',d'));

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
      $scope.sortBy('count');
      $scope.sortOrder = true;
    }
  ]);

});