# tinypetvmoblmk

<h2>Introduction</h2><br>
Il nous a été confié comme tâche de réaliser un site de pétition sous google app engine.
Ce read-me va faire un tour du travail réalisé et des difficultées rencontrées.
<br>
Lien vers le projet Google Cloud Platform : https://tinypetvimaox.ew.r.appspot.com/
Ce projet a été réalisé par KABA Mariame, BERNARD-LEROUX Oxana et MOREAU Vivien.
<br>
<h2>Tentative AngularJS</h2><br>
Dans un premier temps, nous avons voulu réaliser la partie front end de ce projet en AngularJS car il s’agit du framework javascript que nous maîtrisons le mieux et qui peut faire appel à une API Rest. Pour la partie CSS, nous avons utilisé la librairie bootstrap. 
Après avoir obtenu un affichage qui nous convenait, nous avons tenté de lier les contrôleurs à l’API. Ce que nous pensions n’être qu’un “simple branchement” entre le front et le back nous a causé beaucoup d’erreurs que nous n’avons pas réussi à corriger. 
<br>
<h2>Reprise projet</h2><br>
Suite à une succession d’échecs sur l’intégration du back end avec le front end en AngularJS, nous avons repris un projet déjà existant. La connexion sur ce projet ne fonctionnant pas, nous avons eu à tenter de nombreuses choses et nous a permis de comprendre le fonctionnement du projet et plus globalement le fonctionnement d’un projet sur le Google Cloud Plateforme. Notamment les interactions entre les fichiers html et l’api.
La plue-value apportée à ce projet reste très faible, étant donné qu’il était déjà très complet.
Comme nous allons le détailler ci-dessous, nous avons voulu ajouter de meilleures fixtures.
<br>
<h2>Tentative Fixtures</h2><br>
Voyant les fixtures basiques et peu parlantes, nous nous sommes donné pour mission d’en réaliser des plus parlantes, plus réalistes. Grâce à cette mission, nous avons pu mieux comprendre les interactions avec le datastore. Ces fixtures reposent sur un principe de “banque de données", c'est-à-dire des Listes avec plusieurs données (nom, prénom, thème, tag, titre…). Lors de l’exécution de l’initialisation des données, il devait y avoir des utilisateurs, des pétitions et des signatures créées en piochant dans ces banques de données, mais pour des raisons inconnues, lorsque nous avons voulu les lancer sur GCP, nous avons eu une erreur 500.
Ces fixtures sont visibles en commentaires dans le fichier java : PetitionInit
<br>
<h2>Difficultés rencontrées</h2><br>
<strong>AngularJS</strong><br>
Ne connaissant pas mithril nous avons voulu essayer de faire la même chose sous angularJS. Faute d’exemples et de documentation sur ce genre de projets, nous étions complètement perdus pour la liaison avec le backend. Nous avons tenté quelque chose de semblable à ce qui se fait en mithril, sans succès.
<br>
<strong>Connexion Google</strong><br>
Nous avons perdu énormément de temps à tenter de faire marcher la connexion google, elle n’a pu fonctionner que quelques jours avant le rendu. Rendant le reste du travail précipité et bâclé. 
<br>
<strong>Lenteur de Google Cloud Plateforme</strong><br>
Nous avons perdu énormément de temps à git pull et deploy. Sans parler des nombreux bugs inexplicables (des fois une modification apparaissait au bout de 5 mvn package + deploy. De même, la navigation sur le site et entre les différents onglets est très lente.
<br>
<strong>Fixtures</strong><br>
Il semblerait que le datastore ait beaucoup de mal à accueillir un push de données important. Les erreurs 500 ont été régulières, même avec des fixtures qui marchaient auparavant, rendant les tests très laborieux.
<br>
<br>
<h2>Fonctionnalités du projet</h2>
 <ul><li>Création de pétitions</li>
 <li>Affichage des pétitions lancées/signées</li>
 <li>Parcourir les pétitions les plus récentes</li>
 <li>Parcourir le top les pétitions signées</li>
 <li>Rechercher les pétitions par titre ou tag</li></ul>
