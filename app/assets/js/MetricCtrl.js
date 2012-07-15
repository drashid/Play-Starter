function MetricCtrl($scope, $http) {
	//load metrics 
	$scope.loadMetrics = function(){
	  	$http.get('/api/metrics/all').success(function(data){
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
		$http.post('/api/metrics/clear', {'machine': machineName}).success(function(){
			$scope.loadMetrics();
		});
	}

	$scope.sortBy = function(fieldName){
		if($scope.sortField === fieldName){
			$scope.reverse = !$scope.reverse;
		}else{
			$scope.reverse = false;
		}
		$scope.sortField = fieldName;
	}

	//INIT
	$scope.loadMetrics();
	$scope.sortBy("name");
	$scope.reverse = false;
};