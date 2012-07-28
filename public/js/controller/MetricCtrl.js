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
      
      function stream_layers(n, m, o) {
        if (arguments.length < 3) o = 0;
        function bump(a) {
          var x = 1 / (.1 + Math.random()),
          y = 2 * Math.random() - .5,
          z = 10 / (.1 + Math.random());
          for (var i = 0; i < m; i++) {
            var w = (i / m - y) * z;
            a[i] += x * Math.exp(-w * w);
          }
        }
        return d3.range(n).map(function() {
          var a = [], i;
          for (i = 0; i < m; i++) a[i] = o + o * Math.random();
            for (i = 0; i < 5; i++) bump(a);
              return a.map(stream_index);
          });
      }

      function stream_index(d, i) {
        return {x: i, y: Math.max(0, d)};
      }

      function exampleData() {
        return stream_layers(3,10+Math.random()*100,.1).map(function(data, i) {
          return {
           key: 'Stream' + i,
           values: data
         };
       });
      };
      
      nv.addGraph(function() {
        var chart = nv.models.multiBarChart();
        chart.showControls(false); //disable stacked

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