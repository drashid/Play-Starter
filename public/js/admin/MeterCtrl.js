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

      $scope.refresh = function() {
        $scope.loadMetrics();
      };

      $scope.sortBy = function(fieldName){
        _sortBy(fieldName, 'sortField', 'sortOrder');
      };

      $scope.showID = function(){
        return $scope.averageNodes ? "hide-id" : "show-id";
      };

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
            .axisLabel('Time')
            .tickFormat(d3.format(',.1f'));

          d3.select('#chart1 svg')
            .datum(_formatDataForGraph($scope.averagedMetrics))
            .transition().duration(250).call(chart);

          nv.utils.windowResize(chart.update);

          return chart;
        });
      };
    }
  ]);

});