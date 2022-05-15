package foo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.repackaged.com.google.datastore.v1.CompositeFilter;
import com.google.appengine.repackaged.com.google.datastore.v1.Projection;
import com.google.appengine.repackaged.com.google.datastore.v1.PropertyFilter;

@WebServlet(name = "PetitionSigned", urlPatterns = { "/petitionSigned" })
public class PetitionSigned extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		
	response.getWriter().print("<h2> petition signees par id8_6 </h2>");
	long t1=System.currentTimeMillis();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	Query q = new Query("Petition")
			.setFilter(
					new FilterPredicate("signataire", FilterOperator.EQUAL, "id8_6")
					); //.addSort("date", SortDirection.ASCENDING)
	
	q.setKeysOnly();
	PreparedQuery pq = datastore.prepare(q);
	List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
	
	
	
	response.getWriter().print("<li> result:" + result.size() + "<br>");
	
	
	//int nbSignatures=2;
	for (Entity entity : result) {
		response.getWriter().print("<li>" + entity.getKey()+ "<br>" );
		
	}

	
	long t2=System.currentTimeMillis();
	response.getWriter().print("<li> Temps ecoulé : " + (t2-t1));
	if (result.size()>100 && (t2-t1)>500) {
		response.getWriter().print("<li> On peut optimiser cette requete en limitant le resultat à 100, et en ajoutant une pagination <br>");
	}

	}
}
