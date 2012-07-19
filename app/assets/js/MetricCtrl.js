function MetricCtrl($scope, $http) {
	//load health checks 
	$scope.loadHealth = function(){
	  	$http.get('/api/admin/metrics/health').success(function(data){
			$scope.health = data;
	  	});
	};

	averageObjs = function(objs){
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

	processMetrics = function() {
		if($scope.averageNodes){
			return _.chain($scope.fetchedMetrics)
					.groupBy('name')
					.map(function(objList){ return averageObjs(objList); })
					.value();
		}else{
			return $scope.fetchedMetrics;
		}
	};

	//load metrics 
	$scope.loadMetrics = function() {
	  	$http.get('/api/admin/metrics/fetch').success(function(data){
			//flatten metric array of arrays (one array per web node)
			$scope.fetchedMetrics = _.flatten(data);

			$scope.metrics = processMetrics();
	  	});
	};

	$scope.refresh = function() {
		$scope.loadMetrics();
		$scope.loadHealth();
	};

	$scope.clearMachine = function(machineName){
		$http.post('/api/admin/metrics/clear', {'machine': machineName}).success(function(){
			$scope.refresh();
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

	$scope.$watch('averageNodes', function(value){
		$scope.metrics = processMetrics();
	});

	$scope.showID = function(){
		if($scope.averageNodes){
			return "hide-id";
		}
		return "show-id";
	};

	//INIT
	$scope.averageNodes = true;
	$scope.loadMetrics();
	$scope.loadHealth();

	$scope.sortTimerBy("name");
	$scope.sortMeterBy("name");
	$scope.timerSortOrder = false;
	$scope.meterSortOrder = false;
};