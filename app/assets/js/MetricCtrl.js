function MetricCtrl($scope, $http) {
	//load metrics 
	$http.get('/api/metrics').success(function(data){
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