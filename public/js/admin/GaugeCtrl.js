define(['controller/controllers', 'libs/underscore', 'libs/nv.d3', 'admin/metric-utils'], function(controllers, _, nv, utils){
  
  controllers.controller('GaugeCtrl', ['$scope', '$http', 
    function GaugeCtrl($scope, $http) {
      //load metrics 
      $scope.loadMetrics = function() {
        $http.get('/api/admin/metrics/fetch').success(function(data){
          //flatten metric array of arrays (one array per web node)
          $scope.fetchedMetrics = _.flatten(data);
          $scope.averagedMetrics = utils.averageMetrics($scope.fetchedMetrics)

          _loadGraph();
        });
      };

      _isGauge = function(item){
        return _.has(item, 'type') && (item.type === 'GAUGE');
      };

      _formatDataForGraph = function(averagedMetrics){
        var count = [];

        _.each(averagedMetrics, function(item, i){ 
          if(_isGauge(item)){
            count.push({ value: item.value, label: item.name });
          }
        });

        return [
          { key: 'Gauge', values: count }
        ];
      };
      
      _loadGraph = function(){ 
        nv.addGraph(function() {
          var chart = nv.models.multiBarHorizontalChart()
            .x(function(d){ return d.label; })
            .y(function(d){ return d.value; })
            .margin({top: 30, right: 20, bottom: 50, left: 175})
            .showControls(false)
            .showLegend(false)
            .tooltips(false)
            .showValues(true);

          chart.yAxis
            .tickFormat(d3.format(',.2f'));

          d3.select('#chart1 svg')
            .datum(_formatDataForGraph($scope.averagedMetrics))
            .transition().duration(500).call(chart);

          nv.utils.windowResize(chart.update);

          return chart;
        });
      };

      //INIT
      $scope.loadMetrics();
    }
  ]);

});