package foo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.text.DateFormat;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.auth.EspAuthenticator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Transaction;

@Api(name = "myApi",
	version = "v1",
	audiences = "527621972350-qlpn957kak51r0subapta5r36h5t3f6e.apps.googleusercontent.com",
	clientIds = "527621972350-qlpn957kak51r0subapta5r36h5t3f6e.apps.googleusercontent.com"

)
public class PetitionEndpoint {
	
	@ApiMethod(name = "petitions", httpMethod = HttpMethod.GET)
	public List<Entity> scores() {
		Query q = new Query("Petition").addSort("titre", SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(100));
		return result;
	}
	
	@ApiMethod(name = "addPetition", path="petition/add", httpMethod = ApiMethod.HttpMethod.POST)
	public Entity addPetition(Petition p) {
		Random r = new Random();
		int k = r.nextInt(50000);		
		Entity e = new Entity("Petition", "P"+ k + p.auteur);
		e.setProperty("titre", p.titre);
		e.setProperty("description", p.description);
		e.setProperty("image", p.image);
		e.setProperty("objectif", p.objectif);
		e.setProperty("auteur", p.auteur);
		e.setProperty("dateCrea", p.dateCrea);
		e.setProperty("nbSignature", p.nbSignature);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(e);
		return e;
	}

	@ApiMethod(name = "updatePetition", path="petition/update", httpMethod = ApiMethod.HttpMethod.POST)
	public Entity updatePetition(Petition p) throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key petitionKey = new Entity("Petition", p.idp).getKey();
		Entity e = datastore.get(petitionKey);
		e.setProperty("titre", p.titre);
		e.setProperty("description", p.description);
		e.setProperty("image", p.image);
		e.setProperty("objectif", p.objectif);
		e.setProperty("auteur", p.auteur);
		e.setProperty("dateCrea", p.dateCrea);
		e.setProperty("nbSignature", p.nbSignature);
		datastore.put(e);
		return e;
	}
	@ApiMethod(name = "deletePetition", path="petition/delete/{petitionID}", httpMethod = ApiMethod.HttpMethod.DELETE)
	public Entity deletePetition(@Named("petitionID") String petitionID) throws EntityNotFoundException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key petitionKey = new Entity("Petition", petitionID).getKey();
		Entity e = datastore.get(petitionKey);
		datastore.delete(e.getKey());
		
		Query q = new Query("Signature");
		q.setFilter(new FilterPredicate("petition", FilterOperator.EQUAL, petitionID)); 
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
		Entity response = new Entity("response");
		int nbSignature = result.size();
		if (nbSignature>0) {
			for (Entity entity : result) {
				datastore.delete(entity.getKey());
			}
		} 
		response.setProperty("type", "OK");
		response.setProperty("Message", "Petition supprimée avec succès");
		response.setProperty("SignatureSupprimer", nbSignature);
		
		return response;
	}
		
		
	
}