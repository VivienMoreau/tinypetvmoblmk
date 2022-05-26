(function () {
	'use strict';

	var app = angular.module('petApp', []);
	app.service('petService', PetService);
	app.controller('mesPetController', MesPetController);

    //pour filtrer sur l'auteur mais idealement c'est a gerer dans le back je pense
    app.filter('filtreAuteur', function(){
        return function(pet) {
            var retour = [];
            pet.forEach(petition => {
                if(petition.auteur == 'moi'){ //à remplacer par l'id google
                    retour.push(petition);
                }
            });
            return retour;
        }
    })

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
	MesPetController.$inject = ['$scope','petService'];
	function MesPetController($scope, petService) {
		// capture de l'attribut this
		var vm = this;
		// objet correspondant à une pétition
		vm.pet = {
			titre:'',
			description : '',
			image:'',
			objectif:'',
            checkCond:'',
			auteur:'', //récupéré par google
            dateCrea: moment().format('L') //date du jour au format JJ/MM/AAAA
		};

        //a récuperer depuis le back en donnant l'id de l'auteur
        vm.listePetitions = [
            {id:'1', titre:'titre3',description : 'description3',image:'image3',objectif:'3',auteur:'moi',dateCrea:'03/03/2022',nbSignature:3},
            {id:'2', titre:'titre2',description : 'description2',image:'image2',objectif:'2',auteur:'auteur2',dateCrea:'02/02/2022',nbSignature:2},
            {id:'3', titre:'titre4',description : 'description4',image:'image4',objectif:'4',auteur:'moi',dateCrea:'04/04/2022',nbSignature:4},
            {id:'4', titre:'titre1',description : 'description1',image:'image1',objectif:'1',auteur:'auteur1',dateCrea:'01/01/2022',nbSignature:1} 
        ];

		//a récuperer depuis le back en donnant l'id de l'auteur
		vm.listePetitionsSignees = [
            {titre:'titre4',description : 'description4',image:'image4',objectif:'4',auteur:'moi',dateCrea:'04/04/2022',nbSignature:4, dateSignature:'25/06/22'},
            {titre:'titre1',description : 'description1',image:'image1',objectif:'1',auteur:'auteur1',dateCrea:'01/01/2022',nbSignature:1, dateSignature:'28/06/22'}
        ];
    }
}());