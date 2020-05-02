angular.module('app.services', []).service('ProductService', function ($http){

  this.serachProduct = function(searchCriteria, searchKey){
    var productSearchBean ={searchCriteria:searchCriteria, searchKey:searchKey};
    return $http({
      url: '/api/v1/product/searchProducts/',
      method: 'POST',
      data: productSearchBean,
      headers: {'Content-Type': 'application/json'}
  }).then(function(response){
      return response.data;
    });
  }

})
.service('CartService', function ($http){
   this.getCartDetails = function(userId){
    return $http.get("/api/v1/cart/getCartDetails/"+userId).then(function(response){
       return response.data;
    });
   }
   
   this.createCart = function(userId,productBean){
    return $http({
      url: '/api/v1/cart/createCart/'+userId,
      method: 'POST',
      data: productBean,
      headers: {'Content-Type': 'application/json'}
      }).then(function(response){
         return response.data;
      });
   }

   this.updateCart = function(userId,productBean){
    return $http({
      url: '/api/v1/cart/updateCart/'+userId,
      method: 'PUT',
      data: productBean,
      headers: {'Content-Type': 'application/json'}
      }).then(function(response){
         return response.data;
      });
   }

   this.removeAllProductsFromCart = function(cartId){
    return $http({
      url: '/api/v1/cart/removeAllProducts/'+cartId,
      method: 'POST',
      }).then(function(response){
         return response.data;
      });
   }

   this.addProduct = function(cartId,productCategoryBean){
    return $http({
      url: '/api/v1/cart/addProduct/'+cartId,
      method: 'POST',
      data: productCategoryBean,
      headers: {'Content-Type': 'application/json'}
      }).then(function(response){
         return response.data;
      });
   }

   this.removeProduct = function(cartId,productCategoryBean){
    return $http({
      url: '/api/v1/cart/removeProduct/'+cartId,
      method: 'POST',
      data: productCategoryBean,
      headers: {'Content-Type': 'application/json'}
      }).then(function(response){
         return response.data;
      });
   }

});