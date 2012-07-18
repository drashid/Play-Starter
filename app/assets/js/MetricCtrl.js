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

	sortBy = function(sortFieldName, sortField, orderField) {
		if($scope[sortField] === sortFieldName){
			$scope[orderField] = !$scope[orderField];
		}else{
			$scope[orderField] = false;
		}
		$scope[sortField] = sortFieldName;
	}

	$scope.sortTimerBy = function(fieldName){
		sortBy(fieldName, 'timerSortField', 'timerSortOrder');
	}

	$scope.sortMeterBy = function(fieldName){
		sortBy(fieldName, 'meterSortField', 'meterSortOrder');
	}

	//INIT
	$scope.loadMetrics();
	$scope.loadHealth();
	$scope.sortTimerBy("name");
	$scope.sortMeterBy("name");
	$scope.timerSortOrder = false;
	$scope.meterSortOrder = false;
	
};