(function() {
	var app = angular.module('app', ['ui.router', 'navController', 'ngAnimate', 'ui.bootstrap', 'ngResource', 'app.controllers', 'app.services'])

	define('app', [], function() { return app; });

	function req(deps) {
		if (typeof deps === 'string') deps = [deps];
		return {
			deps: function ($q, $rootScope) {
				var deferred = $q.defer();
				require(deps, function() {
					$rootScope.$apply(function () {
						deferred.resolve();
					});
					deferred.resolve();
				});
				return deferred.promise;
			}
		}
	}

	app.config(function($stateProvider, $urlRouterProvider, $controllerProvider){
		var origController = app.controller
		app.controller = function (name, constructor){
			$controllerProvider.register(name, constructor);
			return origController.apply(this, arguments);
		}

		var viewsPrefix = 'views/';

		$urlRouterProvider.otherwise("/")

		$stateProvider
			
			.state('home', {
				url: "/",
				templateUrl: viewsPrefix + "products.html",
				data: {
					pageTitle: 'Home'
				},
				controller:'ProductController',
				resolve : {
					userDeatils : function($http){
						return $http.get("/api/v1/user/userinfo").then(function(response){
							return response.data;
						})
					},
					productDetails : function($http){
						return $http.get("/api/v1/product/getAllProducts").then(function(response){
							return response.data;
						})
					}
				}
			})
			.state('cart',{
				url:'/cart',
				templateUrl: viewsPrefix + 'cart.html',
				controller:'CartController',
				resolve : {
					cartDetails : function($http, $rootScope){
						var userId = $rootScope.userDeatils.userId;
						return $http.get("/api/v1/cart/getCartDetails/"+userId).then(function(response){
							return response.data;
						})
					}
				}
			})
			.state('emptycart',{
				url:'/emptycart',
				templateUrl: viewsPrefix + 'emptycart.html'
			})
			.state('orderplaced',{
				url:'/orderplaced',
				templateUrl: viewsPrefix + 'orderplaced.html',
				controller : 'OrderSuccessController'
			})
			.state('searchproduct',{
				url:'/searchproduct',
				templateUrl: viewsPrefix + 'searchproduct.html',
				controller : 'ProductSearchController'
			})
			.state('productnotfound',{
				url:'/productnotfound',
				templateUrl: viewsPrefix + 'productnotfound.html'
			})
	})
	.directive('updateTitle', ['$rootScope', '$timeout',
		function($rootScope, $timeout) {
			return {
				link: function(scope, element) {
					var listener = function(event, toState) {
						var title = 'Project Name';
						if (toState.data && toState.data.pageTitle) title = toState.data.pageTitle + ' - ' + title;
						$timeout(function() {
							element.text(title);
						}, 0, false);
					};

					$rootScope.$on('$stateChangeSuccess', listener);
				}
			};
		}
	])
	.directive('productSerach',['$rootScope','$state','$http',
			function($rootScope,$state,$http){
				return{
					restrict: 'EA',
					templateUrl: 'views/searchbox.html',
					scope : false,
					link : function (scope){
						scope.searchCriteria=[
							{displayKey : 'Id', key : 'id'},
							{displayKey : 'Name', key : 'name'},
							{displayKey : 'Category', key : 'cat'}
						];
						
						scope.productCategory=[
						  {catType : 'A', catName : 'Apparal'},
						  {catType : 'B', catName : 'Book'}
						];

						scope.searchProduct = function() {

							var searchCriteria = scope.selectedCriteria;
							var searchKey;
							if(searchCriteria === 'id'){
							  searchKey = scope.enteredSearchId;
							}else if(searchCriteria === 'name'){
							  searchKey = scope.enteredSearchName;
							}else if(searchCriteria === 'cat'){
							  searchKey= scope.enteredSearchCat;
							}
						  
							if(searchCriteria != undefined && searchKey != undefined){
								var productSearchBean ={searchCriteria:searchCriteria, searchKey:searchKey};
    							$http({
      								url: '/api/v1/product/searchProducts/',
      								method: 'POST',
     								data: productSearchBean,
     								headers: {'Content-Type': 'application/json'}
 								 }).then(function(response){
      									$rootScope.productDetails = response.data;
										$state.go('searchproduct',{}, {reload: true});
    							}).catch(function(response){
									$state.go('productnotfound',{}, {reload: true});
								});
							}
						  
						  };
					}
				};
			}])
	;
}());
