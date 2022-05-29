(function () {
	'use strict';

	var app = angular.module('petApp', []);
	app.service('petService', PetService);
	app.controller('petController', PetController);
	app.directive('noTirets', NoTirets);

	NoTirets.$inject = [];
	function NoTirets(){
		return {
			restrict: 'A',
			require: 'ngModel',
			link: function(scope, element, attr, ngModel) {
			  //verifie l'absence de tiret.
			  ngModel.$validators.noTirets = function() {
				if(element.val()) {
				  return /^[^-]+$/.test(element.val());
				} else {
				  return true;
				}
			  };
			}
		}
	}


	/** 
	 * Service de stockage des petitions
	*/
	PetService.$inject = [];
	function PetService (){
		var vm = this;

		vm.listePetitions = [];
		vm.pushPetition = pushPetition;
		vm.claerPetition = claerPetition;

		function pushPetition(petition) {
			console.log('dans pushPetition');
			vm.listePetitions.push(petition);
			console.log(vm.listePetitions);
		}

		function claerPetition(petition) {
			vm.listePetitions.splice(vm.listePetitions.indexOf(petition), 1);
		}

	}

	/** 
	 * Controller de la vue Saisie
	 * @param petService : le service de gestion des pétitions
	*/
	PetController.$inject = ['$scope','petService'];
	function PetController($scope, petService) {
		// capture de l'attribut this
		var vm = this;
		// objet correspondant à une pétition
		vm.pet = {
			titre:'',
			description : '',
			image:'',
			objectif:'',
			auteur:'', //récupéré par google
			dateCrea: moment().format('L'), //date du jour au format JJ/MM/AAAA
			nbSignatures: 0
		};

		vm.isDateInvalide = false;
		// La liste des priorités possibles d'une pétition
		vm.prios = [{name:"Faible", value:"Faible"}, {name:"Moyenne", value:"Moyenne"}, {name:"Haute", value:"Haute"}];
		vm.pets = [];

		vm.formHasError = false;

		// déclaration des fonctions accessibles à la vue
		vm.addPet = addPet;
		vm.valideDate = validateDate;
		vm.checkSubmit = checkSubmit;
		vm.isDateRequired = isDateRequired;
		vm.clear = clear;
		vm.destroy = destroy;

		// Création de la petition : enregistrement dans le service
		function addPet(petition) {
			console.log("dans addPet");
			console.log(petition);
			petService.pushPetition(petition);
			clear();
		};

		// Verification du format des dates
		function validateDate(date) {
			console.log(date);
			var today = moment();
			if(moment(date).isBefore(today)) {
				vm.isDateInvalide = true;
			} else {
				vm.isDateInvalide = false;
			}
		}

		// Verifications à effectuer avant de pouvoir créer la pétition
		function checkSubmit(petition) {
			console.log('checkSubmit');
			console.log(petition);

			/*if($scope.form.$valid && vm.isDateInvalide === false) {
				console.log(petition);
				addPet(petition);
			} else {
				console.log(petition);
				vm.formHasError = true;
			}*/
		}

		function clear() {
			vm.pet = {
				titre:'',
				description : '',
				image:'',
				objectif:'',
				auteur:'',
				dateCrea: moment().format('L') //date du jour au format JJ/MM/AAAA
			};
			vm.formHasError = false;
			vm.isDateInvalide = false;
		}

		// Determine si une date est obligatoire ou non
		function isDateRequired() {
			return vm.pet.echeance === 'oui';
		}

		function destroy(petition) {
			petService.claerPetition(petition);
		}

		$scope.$watch(function() {
			return petService.listePetitions;
		  }, function(listePetitions) {
			vm.pets = listePetitions;
			console.log("liste pet"+ vm.pets);
		  });
	}
}());