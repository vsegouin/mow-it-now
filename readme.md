# Mow it now
&nbsp;&nbsp;&nbsp;&nbsp;Ce projet correspond est une réponse à un exercice technique demandé par Xebia.

## Tester le projet

Le projet propose deux interfaces :
 - Une interface graphique réalisé via javaFX.
 - Une interface en ligne de commande standard.
    
   
Vous pouvez télécharger le .jar exécutable ici :

&nbsp;&nbsp;&nbsp;&nbsp;- Version interface graphique : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-GUI.jar

&nbsp;&nbsp;&nbsp;&nbsp;- Version ligne de commande : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-CLI.jar

&nbsp;&nbsp;&nbsp;&nbsp;- Version où le choix est proposé : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-BOTH.jar

Pour éxecuter le programme un simple double clique sur la version GUI suffit a afficher le programme
Pour les deux autres versions il est nécéssaire d'écrire dans une fenetre de commande :
> java -jar chemin-vers-le-fichier.jar

**IMPORTANT** : la version CLI ne permet que la lecture d'un fichier et l'affichage du résultat, les opérations de manipulations ne sont disponible que via l'interface graphique

## Architecture du projet

Le projet est constitué de 4 grandes parties, chacunes séparées dans des packages :

- **_mower_**       : Contient le coeur applicatif.
- **_services_**    : Contient divers services, tels que l'injecteur de dépendance (Google Guice), le gestionnaire de langue pour l'internationalisation, ou le service permettant de lire un fichier.
- **_ui_**          : contient les différentes interfaces graphique (GUI pour l'application graphique, CLI pour l'application en ligne de commandes)
- **_controllers_** : Contient le controlleur principale qui va orchestrer l'ensemble des differentes parties du projet et maintenir le tout dans un ensemble cohérent.

## Compilation
Pour compiler le projet, une simple commande maven suffit.
>mvn package

Par défaut le projet compilera la version graphique. Si vous souhaitez compiler une autre version il existe 3 profil existant (graphical, command-line, both) qui compileront le projet avec l'interface choisis (both par defaut)

>mvn package -P [graphical|command-line|both]

Le projet a été pensé pour être le plus simple possible a utiliser et à réutiliser, c'est pour cela que les dépendances et les configurations sont réduites au minimum.

## Fonctionnalités 
  
  - Lecture et éxécution d'un fichier de commande.
  - Vérification de l'intégrité des commandes fournis.
  - Langue anglaise et française disponible.
  - Ecrire directement à l'intérieur du programme la séquence à effectuer au lieu de devoir charger le fichier.
  - Controler manuellement les tondeuses individuellement, en ajouter et en modifier en direct.
  - Visualiser l'historique des différents déplacement.
  
## Environnement technique
   
   - Langage : Java 1.8, CSS (cependant très peu ici)
   - Frameworks : Google Guice, JavaFX
   - Dépendance maven supplémentaire : Shade
   - Test : TestNG
   - Reporting : Clover
   - Qualité : Findbugs, Checkstyle, Sonarlint
   - Logiciel : Intellij, Gluon SceneBuilder
  
## Evolution possible
- Il est possible d'integrer spring boot et y ajouter une API Rest qui communiquera avec le controlleur principal afin d'en faire une appli web facilement déployable (éventuellement dans un conteneur léger type docker) avec une couche AngularJS ou autre framework front end.
- Ajouter la possibilité d'exporter les opération réalisés afin d'en générer un nouveau fichier.

## Reporting


Un site auto généré par maven a été réalisé et est accessible à cette adresse : http://vsegouin.github.io/mow-it-now/

Plugins utilisé : 
- maven-javadoc-plugin : Afin de générer une javadoc complète.
- maven-checkstyle-plugin : Afin d'avoir un rapport checkstyle (certaines règles ont été retirés)
- findbugs-maven-plugin : Génére un rapport findbugs.
- maven-surefire-plugin : Génére un rapport sur les tests unitaires.
- cobertura-maven-plugin : Afin d'avoir le rapport de couverture du code.
- maven-project-info-reports-plugin : Pour récuperer les informations globales du projet
