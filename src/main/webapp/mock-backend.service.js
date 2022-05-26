var prios = {"data":[{name:"Moyenne", value:"Faible"}, {name:"Moyenne", value:"Moyenne"}, {name:"Haute", value:"Haute"}]};

$httpBackend.whenGET('http://localhost:8080/priorities').respond(function(method, url, data, headers) {
    return [200, prios, {/*headers*/}];
});