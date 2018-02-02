angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
    
  

      .state('buses', {
    url: '/page5',
    templateUrl: 'templates/buses.html',
    controller: 'busesCtrl'
  })

  .state('serviciosAlternativos', {
    url: '/page6',
	params: {
		note: "0"		
},
    templateUrl: 'templates/serviciosAlternativos.html',
    controller: 'serviciosAlternativosCtrl'
  })

  .state('paradero', {
    url: '/page7',
    templateUrl: 'templates/paradero.html',
    controller: 'paraderoCtrl'
  })

  .state('thanks', {
    url: '/page8',
    templateUrl: 'templates/thanks.html',
    controller: 'thanksCtrl'
  })

$urlRouterProvider.otherwise('/page5')

  

});