function HealthCtrl($scope, $http) {
	//load health checks 
	$scope.loadHealth = function(){
	  	$http.get('/api/health').success(function(data){
			$scope.health = data;
	  	});
	};

	//INIT
	$scope.loadHealth();
};