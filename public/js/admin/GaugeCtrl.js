define(['controller/controllers', 'libs/underscore', 'libs/nv.d3', 'admin/metric-utils'], function(controllers, _, nv, utils){
  
  controllers.controller('GaugeCtrl', ['$scope', '$http', 
    function GaugeCtrl($scope, $http) {
      //load metrics 
      $scope.loadMetrics = function() {
        $http.get('/api/admin/metrics/fetch').success(function(data){
          //flatten metric array of arrays (one array per web node)
          $scope.fetchedMetrics = _.flatten(data);
          $scope.averagedMetrics = utils.averageMetrics($scope.fetchedMetrics)

          // $scope.metrics = _chooseMetrics();
          _loadGraph();
        });
      };

      // $scope.sortBy = function(fieldName){
      //   utils.sortBy($scope, fieldName, 'sortField', 'sortOrder');
      //   _showSortArrow(fieldName);
      // }

      // $scope.showID = function(){
      //   return $scope.averageNodes ? "hide-id" : "show-id";
      // };

      $scope.isGauge = function(item){
        return _.has(item, 'type') && (item.type === 'GAUGE');
      };

      // _showSortArrow = function(fieldName){
      //   $scope.sortArrow = $scope.sortOrder ? "▼" : "▲";
      //   $("th > span.sortArrow").css("visibility", "hidden");
      //   $("th#" + fieldName + " > span.sortArrow").css("visibility", "visible");
      // }

      // _chooseMetrics = function(){
      //   return $scope.averageNodes ? $scope.averagedMetrics : $scope.fetchedMetrics;
      // }

      _formatDataForGraph = function(averagedMetrics){
        var count = [];

        _.each(averagedMetrics, function(item, i){ 
            if($scope.isGauge(item)){
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

          // chart.tooltipContent(function(key, x, y, e, graph){
          //   return '<h3>' + x + '</h3>' + 
          //          '<p>' + y + '</p>';
          // });

          // chart.xAxis
          //   .axisLabel('Metrics')
          //   .tickFormat(d3.format(',f'));

          chart.yAxis
            .axisLabel('Value')
            .tickFormat(d3.format(',.2f'));

          d3.select('#chart1 svg')
            .datum(_formatDataForGraph($scope.averagedMetrics))
            .transition().duration(500).call(chart);

          nv.utils.windowResize(chart.update);

          return chart;
        });
      };

      //trigger metric processing (average or not average) on toggle
      // $scope.$watch('averageNodes', function(value){
      //   $scope.metrics = _chooseMetrics();
      // });

      //INIT 
      // $scope.averageNodes = true;
      $scope.loadMetrics();
      // $scope.sortBy('count');
      // $scope.sortOrder = true;
      // _showSortArrow('count');
    }
  ]);

});