# mow-it-now
&nbsp;&nbsp;&nbsp;&nbsp;Ce projet correspond est une réponse à un exercice technique demandé par Xebia.

## Tester le projet

Le projet propose deux interfaces :
&nbsp;&nbsp;&nbsp;&nbsp;- Une interface graphique réalisé via javaFX.
&nbsp;&nbsp;&nbsp;&nbsp;- Une interface en ligne de commande standard.
    
Vous pouvez télécharger le .jar éxécutable ici :
&nbsp;&nbsp;&nbsp;&nbsp;- Version interface graphique : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-GUI.jar
&nbsp;&nbsp;&nbsp;&nbsp;- Version ligne de commande : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-CLI.jar
&nbsp;&nbsp;&nbsp;&nbsp;- Version où le choix est proposé : http://vsegouin.github.io/mow-it-now/bin/mow-it-now-both.jar

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
Pour compiler le projet, il suffit de modifier dans le pom.xml la propriété app.entrypoint afin de choisir le type d'interface qui sera proposé à l'utilisateur final
>mvn package

Le projet a été pensé pour être le plus simple possible a utiliser et à réutiliser, c'est pour cela que les dépendances et les configurations sont réduites au minimum.

## Fonctionnalités 
  
  - Lecture et éxécution d'un fichier de commande.
  - Vérification de l'intégrité des commandes fournis.
  - Langue anglaise et française disponible.
  - Ecrire directement à l'intérieur du programme la séquence à effectuer au lieu de devoir charger le fichier.
  - Controler manuellement les tondeuses individuellement, en ajouter et en modifier en direct.
  - Visualiser l'historique des différents déplacement.
  
## Environnement technique
   
   - Langage : Java 1.8, CSS (très peu ici)
   - Frameworks : Google Guice, JavaFX
   - Dépendance maven supplémentaire : Shade
   - Test : TestNG
   - Reporting : Clover
   - Qualité : Findbugs, Checkstyle, Sonarlint
   - IDE : Intellij
   
## Reporting

TestNG à été utilisé afin de réaliser les tests unitaires, la couverture de code est d'environ 60% sur l'ensemble du projet

Le résultat des tests et la couverture du code peut etre vu ici : http://vsegouin.github.io/mow-it-now/ 