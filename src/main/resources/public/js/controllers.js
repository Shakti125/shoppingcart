angular.module('app.controllers', []).controller('ProductController', function($scope, $state, $stateParams,$rootScope, userDeatils, productDetails, CartService,$timeout){
  $rootScope.userDeatils = userDeatils;
  $rootScope.productDetails = productDetails;
  $scope.apprals = [];
  $scope.books = [];
  $rootScope.appralQuantity = {};
  $rootScope.bookQuantity = {};
  for(var i =0; i<$rootScope.productDetails.length;i++){
    if($rootScope.productDetails[i].productCategory == 'A'){
      $scope.apprals.push($rootScope.productDetails[i]);
    }
    if($rootScope.productDetails[i].productCategory == 'B'){
      $scope.books.push($rootScope.productDetails[i]);
    }
  }
  $scope.createOrUpdateCart = function(productId,productCategory){
    var userId = $rootScope.userDeatils.userId;
    CartService.getCartDetails(userId).then(function(response){
      $rootScope.cartDetails = response;
      if($rootScope.cartDetails==""){
        CartService.createCart(userId,createProductBean(productId,productCategory,true)).then(function(response){
          snackBar("Prodduct added to the cart successfully");
        }).catch(function(response){
          snackBar("Failed to add Product to cart");
        });
      }else{
        CartService.updateCart(userId,createProductBean(productId,productCategory,false)).then(function(response){
          snackBar("Prodduct added to the cart successfully");
        }).catch(function(response){
          snackBar("Failed to add Product to cart");
        });
      }
    })
    
  }

  function createProductBean(productId,productCategory,flag){
    var productBean; 
    if(productCategory == 'A'){
      if(Object.keys($rootScope.appralQuantity).length == 0){
        $rootScope.appralQuantity[productId] = 1;
      }else{
        var apparalValue = $rootScope.appralQuantity[productId];
        if(apparalValue != null)
          $rootScope.appralQuantity[productId] = apparalValue+1;
        else
          $rootScope.appralQuantity[productId] = 1;
      }    
    }else if(productCategory == 'B'){
      if(Object.keys($rootScope.bookQuantity).length == 0){
        $rootScope.bookQuantity[productId] = 1;
      }else{
        var bookValue = $rootScope.bookQuantity[productId];
        if(bookValue!=null)
          $rootScope.bookQuantity[productId] = bookValue+1;
        else
          $rootScope.bookQuantity[productId] = 1;
      } 
    }
    productBean = {'appralQuantity':$rootScope.appralQuantity,'bookQuantity':$rootScope.bookQuantity,'createFlag':flag};
    return productBean;
  }

  function snackBar(msg){
    $scope.message = msg;
    $scope.show = true; 
    $timeout(function () { 
     $scope.show = false;
      }, 3000);
  }

})
.controller('CartController', function($scope, $state, $stateParams,$rootScope,CartService, cartDetails){

$rootScope.cartDetails = cartDetails;
$scope.products = $scope.cartDetails.productDetails;
$scope.emptycart = false;
if($rootScope.cartDetails=="" || !$rootScope.cartDetails.productDetails || 
$rootScope.cartDetails.productDetails==null || Object.keys($rootScope.cartDetails.productDetails).length == 0){
  $scope.emptycart = true;
  $state.go('emptycart');
}
 var cartId= $rootScope.cartDetails.cartId;
$scope.removeProduct = function(productId,productCategory){
  CartService.removeProduct(cartId,createProductCategoryBean(productId,productCategory,false)).then(function(response){
    $state.go('cart',{}, {reload: true});
  });
}

$scope.addproduct = function(productId,productCategory){
  CartService.addProduct(cartId,createProductCategoryBean(productId,productCategory,true)).then(function(response){
    $state.go('cart',{}, {reload: true});
  });
}

function createProductCategoryBean(productId,productCategory,flag){
   var productCategoryBean = {'productId':productId,'productCategoty':productCategory,'addFlag':flag};
   return productCategoryBean;
}
 
})
.controller('OrderSuccessController', function($scope, $state, $stateParams,$rootScope,CartService){
  $scope.userName = $rootScope.userDeatils.userName;
  CartService.removeAllProductsFromCart($rootScope.cartDetails.cartId).then(function(response){
    $rootScope.cartDetails = response;
  })
 
})
.controller('ProductSearchController', function($scope, $state, $stateParams,$rootScope,CartService,$timeout){
  $scope.apprals = [];
  $scope.books = [];
  
  if($rootScope.productDetails != undefined && $rootScope.productDetails != null){
  for(var i =0; i<$rootScope.productDetails.length;i++){
    if($rootScope.productDetails[i].productCategory == 'A'){
      $scope.apprals.push($rootScope.productDetails[i]);
    }
    if($rootScope.productDetails[i].productCategory == 'B'){
      $scope.books.push($rootScope.productDetails[i]);
    }
  } 
}

$scope.createOrUpdateCart = function(productId,productCategory){
  var userId = $rootScope.userDeatils.userId;
  CartService.getCartDetails(userId).then(function(response){
    $rootScope.cartDetails = response;
    if($rootScope.cartDetails==""){
      CartService.createCart(userId,createProductBean(productId,productCategory,true)).then(function(response){
        snackBar("Prodduct added to the cart successfully");
      }).catch(function(response){
        snackBar("Failed to add Product to cart");
      });
    }else{
      CartService.updateCart(userId,createProductBean(productId,productCategory,false)).then(function(response){
        snackBar("Prodduct added to the cart successfully");
      }).catch(function(response){
        snackBar("Failed to add Product to cart");
      });
    }
  })
  
}

function createProductBean(productId,productCategory,flag){
  var productBean; 
  if(productCategory == 'A'){
    if(Object.keys($rootScope.appralQuantity).length == 0){
      $rootScope.appralQuantity[productId] = 1;
    }else{
      var apparalValue = $rootScope.appralQuantity[productId];
      if(apparalValue != null)
        $rootScope.appralQuantity[productId] = apparalValue+1;
      else
        $rootScope.appralQuantity[productId] = 1;
    }    
  }else if(productCategory == 'B'){
    if(Object.keys($rootScope.bookQuantity).length == 0){
      $rootScope.bookQuantity[productId] = 1;
    }else{
      var bookValue = $rootScope.bookQuantity[productId];
      if(bookValue!=null)
        $rootScope.bookQuantity[productId] = bookValue+1;
      else
        $rootScope.bookQuantity[productId] = 1;
    } 
  }
  productBean = {'appralQuantity':$rootScope.appralQuantity,'bookQuantity':$rootScope.bookQuantity,'createFlag':flag};
  return productBean;
}

function snackBar(msg){
  $scope.message = msg;
  $scope.show = true; 
  $timeout(function () { 
   $scope.show = false;
    }, 3000);
}
 
});


