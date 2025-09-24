Latice

🎯 Description

Latice est une application JavaFX permettant de jouer au jeu de plateau Latice en version numérique.
Elle propose une interface graphique intuitive pour poser ses tuiles, gérer les tours de jeu et appliquer automatiquement les règles (vérification des coups valides, points, conditions de victoire, etc.).

⸻

🚀 Fonctionnalités
	•	Interface graphique en JavaFX
	•	Gestion des joueurs et du tour par tour
	•	Validation des coups selon les règles de Latice
	•	Comptage automatique des points
	•	Détection de fin de partie

⸻

📦 Installation

1. Prérequis
	•	Java 17 ou supérieur installé
	•	JavaFX SDK installé et accessible depuis votre système (sur Mac/Linux, ajoutez JavaFX au PATH ou configurez IntelliJ/Eclipse)

▶️ Exécution

Depuis un IDE (IntelliJ, Eclipse, VS Code)
	1.	Importez le projet comme un projet Maven/Java.
	2.	Configurez la Run Configuration en ajoutant les modules JavaFX :

	```
	--module-path /chemin/vers/javafx-sdk-XX/lib --add-modules javafx.controls,javafx.fxml
	```
	(Remplacez /chemin/vers/javafx-sdk-XX/lib par le chemin réel de votre SDK JavaFX.)

🛠️ Technologies utilisées
	•	Java 17+
	•	JavaFX pour l’interface graphique

⸻

📖 Règles du jeu (rappel rapide)
	•	Les tuiles doivent être placées à côté de tuiles existantes avec au moins un symbole ou une couleur en commun.
	•	Les points sont attribués en fonction des combinaisons formées.
	•	La partie se termine lorsqu’un joueur a posé toutes ses tuiles.

⸻

👨‍💻 Auteurs

Projet réalisé par Jules Gayet, Milan Loï, Mostapha Ayeb, Ahmed Mesri dans le cadre d’un projet d'apprentissage en Java/JavaFX, pour l'IUT de Limoges.
