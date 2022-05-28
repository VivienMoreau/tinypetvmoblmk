(function () {
	'use strict';

	var app = angular.module('petApp', []);
	app.service('petService', PetService);
	app.controller('affPetController', AffPetController);

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
	AffPetController.$inject = ['$scope','petService'];
	function AffPetController($scope, petService) {
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
            dateCrea: moment().format('L'), //date du jour au format JJ/MM/AAAA
			nbSignature:''
		};

		//definition des fonctions
		vm.signer = signer;

        //a récuperer depuis le back
        vm.listePetitions = 
		/*{
			list: [],
			loadList: function() {
				return m.request({
					method: "GET",
					url: "_ah/api/myApi/v1/petition/signed/top100"
				})
				.then(function(result) {
					vm.listPet = result.items
					console.log("got:",result.items)
				})
			}
		}*/
			$resource('_ah/api/myApi/v1/petition/signed/top100',
			{}, {
			charge: {method: 'GET', params: {charge: true}}
			});
		/*[
			
            {titre:'titre3',description : 'description3',image:'https://img.fotocommunity.com/les-coquelicots-de-warlaing-727050f3-8719-40fc-a218-824a29733583.jpg?height=1080',objectif:'3',auteur:'moi',dateCrea:'03/03/2022',nbSignature:'3'},
            {titre:'titre2',description : 'description2',image:'https://www.referenseo.com/wp-content/uploads/2019/03/image-attractive-960x540.jpg',objectif:'2',auteur:'auteur2',dateCrea:'02/02/2022',nbSignature:'2'},
            {titre:'titre4',description : 'description4',image:'https://palam.ca/wp-content/uploads/2018/11/D%C3%A9couvrez-trois-sites-d%E2%80%99images-gratuites-libre-de-droit-pour-votre-cr%C3%A9ativit%C3%A9e.jpg',objectif:'4',auteur:'moi',dateCrea:'04/04/2022',nbSignature:'4'},
            {titre:'titre1',description : 'description1',image:'https://static-cse.canva.com/blob/187617/free-stock-photos.jpg',objectif:'1',auteur:'auteur1',dateCrea:'01/01/2022',nbSignature:'1'},
            {titre:'titre3',description : 'description3',image:'https://img.fotocommunity.com/les-coquelicots-de-warlaing-727050f3-8719-40fc-a218-824a29733583.jpg?height=1080',objectif:'3',auteur:'moi',dateCrea:'03/03/2022',nbSignature:'3'},
            {titre:'titre2',description : 'description2',image:'https://www.referenseo.com/wp-content/uploads/2019/03/image-attractive-960x540.jpg',objectif:'2',auteur:'auteur2',dateCrea:'02/02/2022',nbSignature:'2'},
            {titre:'titre4',description : 'description4',image:'https://palam.ca/wp-content/uploads/2018/11/D%C3%A9couvrez-trois-sites-d%E2%80%99images-gratuites-libre-de-droit-pour-votre-cr%C3%A9ativit%C3%A9e.jpg',objectif:'4',auteur:'moi',dateCrea:'04/04/2022',nbSignature:'4'},
            {titre:'titre1',description : 'description1',image:'https://static-cse.canva.com/blob/187617/free-stock-photos.jpg',objectif:'1',auteur:'auteur1',dateCrea:'01/01/2022',nbSignature:'1'}
        ];*/


		function signer(idPet, user) {
			var dateSignature = moment().format('L');
			console.log("idPet : " + idPet);
			console.log("user : " + user);
			console.log("dateSignature : " + dateSignature);
		}
    }
}());