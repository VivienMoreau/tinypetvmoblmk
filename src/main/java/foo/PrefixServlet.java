package foo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
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
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import  java.util.concurrent.ThreadLocalRandom;

@WebServlet(name = "PrefixServlet", urlPatterns = { "/prefix" })
public class PrefixServlet extends HttpServlet {

	static Random r = new Random();

	
	public LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
	    long startEpochDay = startInclusive.toEpochDay();
	    long endEpochDay = endExclusive.toEpochDay();
	    long randomDay = ThreadLocalRandom
	    	      .current()
	    	      .nextLong(startEpochDay, endEpochDay);
	    return LocalDate.ofEpochDay(randomDay);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity e;
		// Create Petitions 
		//pet 1
		e = new Entity("Petition", "P" + 1);
		e.setProperty("titre","titre1");
		e.setProperty("description", "description1");
		e.setProperty("image", "https://static-cse.canva.com/blob/187617/free-stock-photos.jpg");
		e.setProperty("objectif",1);
		e.setProperty("auteur","auteur1");
		e.setProperty("dateCrea","01/01/2022");
		e.setProperty("nbSignature",1);
		datastore.put(e);
		response.getWriter().print("<li> nouvelle petition :" + e.getKey() + "<br>");

		//pet 2
		e = new Entity("Petition", "P" + 2);
		e.setProperty("titre","titre2");
		e.setProperty("description", "description2");
		e.setProperty("image", "https://www.referenseo.com/wp-content/uploads/2019/03/image-attractive-960x540.jpg");
		e.setProperty("objectif",2);
		e.setProperty("auteur","auteur2");
		e.setProperty("dateCrea","02/02/2022");
		e.setProperty("nbSignature",2);
		datastore.put(e);
		response.getWriter().print("<li> nouvelle petition :" + e.getKey() + "<br>");

		//pet 3
		e = new Entity("Petition", "P" + 3);
		e.setProperty("titre","titre1");
		e.setProperty("description", "description1");
		e.setProperty("image", "https://img.fotocommunity.com/les-coquelicots-de-warlaing-727050f3-8719-40fc-a218-824a29733583.jpg?height=1080");
		e.setProperty("objectif",3);
		e.setProperty("auteur","auteur3");
		e.setProperty("dateCrea","03/03/2022");
		e.setProperty("nbSignature",3);
		datastore.put(e);
		response.getWriter().print("<li> nouvelle petition :" + e.getKey() + "<br>");

		//pet 4
		e = new Entity("Petition", "P" + 4);
		e.setProperty("titre","titre4");
		e.setProperty("description", "description1");
		e.setProperty("image", "https://palam.ca/wp-content/uploads/2018/11/D%C3%A9couvrez-trois-sites-d%E2%80%99images-gratuites-libre-de-droit-pour-votre-cr%C3%A9ativit%C3%A9e.jpg");
		e.setProperty("objectif",4);
		e.setProperty("auteur","auteur4");
		e.setProperty("dateCrea","04/04/2022");
		e.setProperty("nbSignature",4);
		datastore.put(e);
		response.getWriter().print("<li> nouvelle petition :" + e.getKey() + "<br>");
	}
}