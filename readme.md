# Mow it now
Ce projet correspond est une réponse à un exercice technique demandé par Xebia.

## Tester le projet

Le projet propose deux interfaces :
 - Une interface graphique réalisé via javaFX.
 - Une interface en ligne de commande standard.
    
   
Vous pouvez télécharger le .jar exécutable ici :
 - Version interface graphique : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-GUI.jar
 - Version ligne de commande : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-CLI.jar
 - Version où le choix est proposé : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-BOTH.jar

Pour exécuter le programme un simple double-clique sur la version GUI suffit à afficher le programme
Pour les deux autres versions il est nécessaire d'écrire dans une fenêtre de commande :
> java -jar chemin-vers-le-fichier.jar

**IMPORTANT** : La version CLI ne permet que la lecture d'un fichier et l'affichage du résultat, les opérations de manipulations ne sont disponibles que via l'interface graphique

## Architecture du projet

Le projet est constitué de 4 grandes parties, chacune séparée dans des packages :

- **_mower_**       : Contient le coeur de l'application, il contiendra les éléments des tondeuses.
- **_services_**    : Contient divers services, tels que l'injecteur de dépendance (Google Guice), le gestionnaire de langue pour l'internationalisation, ou le service permettant de lire un fichier.
- **_ui_**          : Contient les différentes interfaces graphique (GUI pour l'application graphique, CLI pour l'application en ligne de commandes)
- **_controllers_** : Contient le contrôleur principal qui va orchestrer l'ensemble des différentes parties du projet et maintenir le tout dans un ensemble cohérent.

## Compilation
Pour compiler le projet, une simple commande maven suffit.
>mvn package

Par défaut le projet compilera la version graphique. Si vous souhaitez compiler une autre version il existe 3 profils existants (graphical, command-line, both) qui compileront le projet avec l'interface choisie (both par défaut)

>mvn package -P [graphical|command-line|both]

Le projet a été pensé pour être le plus léger et le plus simple possible à utiliser et à réutiliser, c'est pour cela que les dépendances et les configurations sont réduites au minimum.

## Fonctionnalités 
  
  - Lecture et exécution d'un fichier de commandes.
  - Vérification de l'intégrité des commandes fournies.
  - Langue anglaise et française disponible.
  - Écrire directement à l'intérieur du programme la séquence à effectuer au lieu de devoir charger le fichier.
  - Contrôler manuellement les tondeuses individuellement, en ajouter et en modifier en direct.
  - Visualiser l'historique des différents déplacements.
  
## Environnement technique
  
  - Langage : Java 1.8, CSS (cependant très peu ici)
  - Frameworks : Google Guice, JavaFX
  - Dépendances Maven supplémentaires : Shade
  - Test : TestNG
  - Reporting : Clover
  - Qualité : Findbugs, Checkstyle, Sonarlint
  - Logiciel : Intellij, Gluon SceneBuilder
  
## Evolution possible
- Il est possible d'intégrer Spring boot et y ajouter une API Rest qui communiquera avec le contrôleur principal afin d'en faire une appli web facilement déployable (éventuellement dans un conteneur léger type docker) avec une couche AngularJS ou autres Framework front end.
- Ajouter la possibilité d'exporter les opérations réalisés afin d'en générer un nouveau fichier.

## Reporting


Un site généré par Maven a été réalisé et est accessible à cette adresse : http://vsegouin.github.io/mow-it-now/

Plugins utilisés : 
- Maven-javadoc-plugin : Afin de générer une javadoc complète.
- Maven-checkstyle-plugin : Afin d'avoir un rapport checkstyle (certaines règles ont été retirés).
- Findbugs-maven-plugin : Génère un rapport findbugs.
- Maven-surefire-plugin : Génère un rapport sur les tests unitaires.
- Cobertura-maven-plugin : Afin d'avoir le rapport de couverture du code.
- Maven-project-info-reports-plugin : Pour récupérer les informations globales du projet
