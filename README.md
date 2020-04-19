# Chuck Norris Joke App

## Projet

Il s'agit d'un projet individuel réalisé en cours de Développement sur smartphone Andoid à l'ESIEE Paris.

Ce projet consiste à développer une application `Android` qui affichera des blagues sur Chuck Norris. Dans ce projet, nous aurrons à utiliser : 
* Des listes dynamiques
* Des structures de données json
* L'appel d'API en ligne 
* Du code asynchrone
* Activity instance state
* Le partage de données avec d'autres applications 

### Part 1 - Create a UI List component 

* Création d'une liste statique de `Jokes` dans un nouvel objet `StaticList`.
* Création d'un `RecyclerView` permettant l'affichage de données de manière performante.
* Création d'un `CustomAdapter` que l'on appelle `JokeAdapteur` et qui a pour rôle d'adapter les données (volumineuses) à la `RecyclerView`.
* Création d'un fichier `joke_layout.xml` contenant les données à afficher (pour le moment dans un `TextView`).

### Part 2 - Fetch jokes 

* Création d'une nouvelle mise en forme pour les `Jokes` qui est désormais un format à part entière.
* Récupération des `Jokes` sur l'`API` à l'adresse https://api.chucknorris.io/jokes/random.

### Part 3 - Display jokes on screen 

* Ajout de `Jokes` dans l'application par appui sur un `Button`.
* Ajout d'une barre de chargement et d'un délais pour charger de nouvelles `Jokes` dans l'application.
* Suppression du `Button` d'appel de nouvelles `Jokes` et chargement automatique de nouvelles `Jokes` par scroll vers le bas, lorsque l'on atteint la fin de la liste.

### Part 4 - Make UI great again 

* Gestion de la rotation par enregistrement des `Jokes` déjà chargées.
* Ajout d'un `Button` partager (présent dans l'application mais non fonctionnel)
* Ajout d'un `Button` favoris qui se coche et se décoche (présent dans l'application mais non fonctionnel)
