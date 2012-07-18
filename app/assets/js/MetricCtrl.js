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
			var flat = [ ];
			var k = 0;
			for(var i = 0; i < data.length; i++){
	    		for(var j = 0; j < data[i].length; j++){
	        		flat[k] = data[i][j];
	        		k++;
	        	}
	        }
			$scope.metrics = flat;
	  	});
	};

	$scope.clearMachine = function(machineName){
		$http.post('/api/admin/metrics/clear', {'machine': machineName}).success(function(){
			$scope.loadMetrics();
			$scope.loadHealth();
		});
	}

	$scope.sortTimerBy = function(fieldName){
		if($scope.timerSortField === fieldName){
			$scope.timerSortOrder = !$scope.timerSortOrder;
		}else{
			$scope.timerSortOrder = false;
		}
		$scope.timerSortField = fieldName;
	}

	$scope.sortMeterBy = function(fieldName){
		if($scope.meterSortField === fieldName){
			$scope.meterSortOrder = !$scope.meterSortOrder;
		}else{
			$scope.meterSortOrder = false;
		}
		$scope.meterSortField = fieldName;
	}

	//INIT
	$scope.loadMetrics();
	$scope.loadHealth();
	$scope.sortTimerBy("name");
	$scope.timerSortOrder = false;
};