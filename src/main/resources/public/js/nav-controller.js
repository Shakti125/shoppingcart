angular.module('navController', [])
	.controller('nav', function($scope, $state) {
		$scope.title = 'Shopping Cart';

		$scope.isUrl = function(url) {
			if (url === '#') return false;
			return ('#' + $state.$current.url.source + '/').indexOf(url + '/') === 0;
		};

		$scope.pages = [
			{
				name: 'Products',
				url: '#/'
			},
			{
				name: 'Cart',
				url: '#/cart'
			}

		]
	});
