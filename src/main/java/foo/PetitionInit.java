package foo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.api.server.spi.config.Description;
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

import foo.RandomDate;

@WebServlet(name = "PetInit", urlPatterns = { "/petitionInit" })
public class PetitionInit extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String name_tag;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		Random r = new Random();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		ArrayList<String> banqueTitre = new ArrayList<>(List.of("Sauvons les tortues", "Protéger les enfants migrants isolés", "Stopper la maladie de Charcot et les autres maladies neurodégénératives", "Pour la libération d'Olivier Dbois, seul otage françaus au Monde depuis le 8 Avril 2021", "Un référendum pour l'hôpital public !", "Pour la liberté d'informer sur l'agroalimentaire en Bretagne et ailleurs", "Soutien à Vincent, supprimer l'amende"));
		ArrayList<String> banqueDescription = new ArrayList<>(List.of("C'est honteux", "Avec vous tout est possible", "il faut cesser cela", "faites une bonne action, signez", "one two three Viva l'Algérie", "nous garantissons un anonymat strict", "ne pas signer, c'est cautionner."));
		ArrayList<String> banquePrenom = new ArrayList<>(List.of("Jean", "Emenline", "Antoine", "Pascal", "Christophe", "Michel", "Luffy", "Roger"));
		ArrayList<String> banqueNom = new ArrayList<>(List.of("Dupuis", "Dupont", "Dubois", "Michel", "Aubert", "Poulain", "Molli", "Andrivot"));
		ArrayList<String> banqueTheme = new ArrayList<>(List.of("Guerre", "Enfance", "Ecologie", "Politique", "Divers"));
		ArrayList<String> userCree = new ArrayList();
		// Create users

		for (int i =0; i<100; i++){
			int indexNom = (int)(Math.random()*banqueNom.size());
			int indexPrenom = (int)(Math.random()*banquePrenom.size());
			String nom = banqueNom.get(indexNom);
			String prenom = banquePrenom.get(indexPrenom);
			String userId = prenom +""+ i +""+ nom;
			userCree.add(userId);
			Entity e = new Entity("User", userId);
			e.setProperty("nom", nom );
			e.setProperty("prenom", prenom );
			e.setProperty("email", prenom + "." + nom + "@gmail.com");
			datastore.put(e);
			response.getWriter().print("<li> created user: " + e.getKey() + "<br>");
		}

		for (int j = 0; j < 300; j++) {
			String dateValue = RandomDate.randDate();
					long date_formated = 0;
					try {
						date_formated = Long.MAX_VALUE-(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue)).getTime();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int indexUser = (int)(Math.random()*userCree.size());
					String id_user = userCree.get(indexUser);
					String petitionId=  date_formated + ":" + id_user + ":" + j;
					int indexTheme = (int)(Math.random()*banqueTheme.size());
					int indexTitre = (int)(Math.random()*banqueTitre.size());
					int indexDescription = (int)(Math.random()*banqueDescription.size());
					String theme = banqueTheme.get(indexTheme);
					String titre = banqueTitre.get(indexTitre);
					String description = banqueDescription.get(indexDescription);
					Entity p = new Entity("Petition", petitionId);
					p.setProperty("theme", theme);
					p.setProperty("titre", titre);
					p.setProperty("description", description);
					p.setProperty("date", dateValue);
					p.setProperty("update_at", dateValue);
					p.setProperty("proprietaire",  id_user);

					int nbMaxSignataire = r.nextInt(50);
					int nbSignataire = 0;
					while (nbSignataire < nbMaxSignataire) {
						int indexSignataire = (int)(Math.random()*userCree.size());
						//String id_user = r.nextInt(50) + "" + r.nextInt(10);
						String id_signataire = userCree.get(indexSignataire);
						String s_date= RandomDate.randDate();
						try {
							date_formated = Long.MAX_VALUE-(new SimpleDateFormat("yyyy-MM-dd").parse(s_date)).getTime();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Entity s = new Entity("Signature", id_signataire+":"+date_formated+":"+petitionId);
						s.setProperty("petition",  petitionId);
						s.setProperty("proprietaire",  id_signataire);
						s.setProperty("date",  s_date);	
						datastore.put(s);
						nbSignataire++;
						response.getWriter().print("<li> signature created: " + id_signataire + "<br>");
						
					}
					
					
					p.setProperty("nbSignataire", nbSignataire);
					p.setProperty("objectifSignataire", r.nextInt(500)*100);
					// Create tag
					HashSet<String> listTag = new HashSet<String>();
					
					int nbMaxTag = r.nextInt(20);
					while (listTag.size() < nbMaxTag) {
						name_tag = "name_tag" + r.nextInt(50) + "" + r.nextInt(10);
						if (!listTag.contains(name_tag)) {
						listTag.add(name_tag);
						response.getWriter().print("<li> tag created: " + name_tag + "<br>");
						}
					}
					
					p.setProperty("tag", listTag);
					
					datastore.put(p);
					response.getWriter().print("<li> created petition: " + p.getKey() + "<br>");

		}





		/*
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 10; j++) {
				
				String userId= i + "" +j;
				Entity e = new Entity("User", userId);
				e.setProperty("nom", "Mon nom est " + j);
				e.setProperty("prenom", "Mon prénom est " + j);
				e.setProperty("email", "Mon adresse mail est " + j);
				
				
				datastore.put(e);
				response.getWriter().print("<li> created user: " + e.getKey() + "<br>");
				
				// Create petition
				int nbMaxPetition = r.nextInt(5);
				for (int k = 0; k < nbMaxPetition; k++) {
					
					String dateValue = RandomDate.randDate();
					long date_formated = 0;
					try {
						date_formated = Long.MAX_VALUE-(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue)).getTime();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					String petitionId=  date_formated + ":" + userId + ":" + k;
					Entity p = new Entity("Petition", petitionId);
					p.setProperty("theme", "Le thème est " + j);
					p.setProperty("titre", "Le titre est " + j);
					p.setProperty("description", "La description est " + j);
					p.setProperty("date", dateValue);
					p.setProperty("update_at", dateValue);
					p.setProperty("proprietaire",  userId);
					
					
					
					int nbMaxSignataire = r.nextInt(50);
					int nbSignataire = 0;
					while (nbSignataire < nbMaxSignataire) {
						String id_user = r.nextInt(50) + "" + r.nextInt(10);
						String s_date= RandomDate.randDate();
						try {
							date_formated = Long.MAX_VALUE-(new SimpleDateFormat("yyyy-MM-dd").parse(s_date)).getTime();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Entity s = new Entity("Signature", id_user+":"+date_formated+":"+petitionId);
						s.setProperty("petition",  petitionId);
						s.setProperty("proprietaire",  id_user);
						s.setProperty("date",  s_date);	
						datastore.put(s);
						nbSignataire++;
						response.getWriter().print("<li> signature created: " + id_user + "<br>");
						
					}
					
					
					p.setProperty("nbSignataire", nbSignataire);
					p.setProperty("objectifSignataire", r.nextInt(500)*100);
					// Create tag
					HashSet<String> listTag = new HashSet<String>();
					
					int nbMaxTag = r.nextInt(20);
					while (listTag.size() < nbMaxTag) {
						name_tag = "name_tag" + r.nextInt(50) + "" + r.nextInt(10);
						if (!listTag.contains(name_tag)) {
						listTag.add(name_tag);
						response.getWriter().print("<li> tag created: " + name_tag + "<br>");
						}
					}
					
					p.setProperty("tag", listTag);
					
					datastore.put(p);
					response.getWriter().print("<li> created petition: " + p.getKey() + "<br>");
						
				}
			}
		}*/
	}
}