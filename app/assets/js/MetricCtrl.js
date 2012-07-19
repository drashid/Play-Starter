function MetricCtrl($scope, $http) {
	//load health checks 
	$scope.loadHealth = function(){
	  	$http.get('/api/admin/metrics/health').success(function(data){
			$scope.health = data;
	  	});
	};

	//load metrics 
	$scope.loadMetrics = function(){
	  	$http.get('/api/admin/metrics/fetch').success(function(data){
			//flatten metric array of arrays (one array per web node)
			$scope.metrics = _.flatten(data);
	  	});
	};

	refresh = function() {
		$scope.loadMetrics();
		$scope.loadHealth();
	};

	$scope.clearMachine = function(machineName){
		$http.post('/api/admin/metrics/clear', {'machine': machineName}).success(function(){
			refresh();
		});
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

	//INIT
	$scope.loadMetrics();
	$scope.loadHealth();
	$scope.sortTimerBy("name");
	$scope.sortMeterBy("name");
	$scope.timerSortOrder = false;
	$scope.meterSortOrder = false;
	$scope.averageNodes = true;
};