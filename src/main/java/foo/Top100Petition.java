package foo;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

@WebServlet(name = "Top100Petition", urlPatterns = { "/top100Petition" })
public class Top100Petition extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		

	response.getWriter().print("<h2> top 100 des petitions signé </h2>");
	long t1=System.currentTimeMillis();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	Query q = new Query("Petition");
			q.addProjection(new PropertyProjection("nbSignataire", Integer.class));
			q.addSort("nbSignataire", SortDirection.DESCENDING);
	
	PreparedQuery pq = datastore.prepare(q);
	List<Entity> petitions = pq.asList(FetchOptions.Builder.withLimit(100));
	
	
	for (int i=0; i<petitions.size(); i++) {
		response.getWriter()
		.print("<li> " + petitions.get(i).getKey() + " ------> Nombre votant : " + petitions.get(i).getProperty("nbSignataire"));

	}
	long t2=System.currentTimeMillis();
	
	response.getWriter().print("<li> Temps ecoulé : " + (t2-t1));
	// compte des signataires en back-end Methode 1 
	
	}
}

	// Méthode pour récuperer les signataires deja presents puis en rajouter, à voir ac le prof si cest necessaire 

	//Query u = new Query("User");
	// ajouter une transcation read only
	//u.addProjection(new PropertyProjection("id", String.class));
	/*
	PreparedQuery uq = datastore.prepare(u);
	List<Entity> users = uq.asList(FetchOptions.Builder.withLimit(50));
			
		
	response.getWriter().print("<li> petitions:" + petitions.size() + "<br>");
	response.getWriter().print("<li> users:" + users.size() + "<br>");
	
	//int nbSignatures = 2;
    for (int i=0; i<users.size(); i++) {
    	for (int j=0; j<petitions.size(); j+=2) {
    		ArrayList<String> listSignatairePet1 = (ArrayList<String>) petitions.get(j).getProperty("signataire");
    		ArrayList<String> listSignatairePet2 = (ArrayList<String>) petitions.get(j+1).getProperty("signataire");

    		HashSet<String> hashListSignataire1 = new HashSet<String>(listSignatairePet1);
    		HashSet<String> hashListSignataire2 = new HashSet<String>(listSignatairePet2);

    		for (int z=0; z<nbSignatures; i++) {
    			hashListSignataire1.add(users.get(i).toString());
    			hashListSignataire2.add(users.get(i).toString());

    		}
    		if (petitions.size() % 10 == 0) {
    			nbSignatures *= 2 ;
    		}
    		response.getWriter().print("<li>" + hashListSignataire1 + "<li>");
    		response.getWriter().print("<li>" + hashListSignataire2 + "<li>");

    		petitions.get(j).setProperty("signataire", hashListSignataire1);
    		petitions.get(j+1).setProperty("signataire", hashListSignataire2);

    		datastore.put(petitions.get(j));
    		datastore.put(petitions.get(j+1));

    		response.getWriter().print("<li> petition:" + petitions.get(j).getKey() + "signé par " + users.get(i).getKey());
    		response.getWriter().print("<li> petition:" + petitions.get(j+1).getKey() + "signé par " + users.get(i).getKey());
    		}
    	}*/
		
	


