import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
// imports nécessaires pour la création d'une interface graphique utilisant JavaFX

// clé API = 1f945e3d94622f2d51a9fec7d07651f9
// http://api.aviationstack.com/v1/flights?access_key=1f945e3d94622f2d51a9fec7d07651f9&arr_iata=CDG

public class Interface extends Application {
    double PrePosY, PosY, PrePosX, PosX; // Déclaration des variables pour stocker les positions du curseur
    double posObjX, posObjY; // Déclaration des variables pour stocker les positions de l'objet
    double newPosObjY, newPosObjX; // Déclaration des variables pour stocker les nouvelles positions de l'objet

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The World"); // Définir le titre de la fenêtre

        Image img = new Image("file:./Ressources/space.png");//créer un objet image
        ImageView iv = new ImageView(img); // Créer un objet ImageView et lui attribuer l'image
        iv.getTransforms().add(new Translate(-img.getWidth() / 2, -img.getHeight() / 2)); // Translater l'image
        iv.fitHeightProperty(); // Définir la hauteur de l'image
        iv.fitWidthProperty(); // Définir la largeur de l'image

        Terre terre = new Terre(); // Créer un nouvel objet Terre
        Pane pane = new Pane(); // Créer un nouvel objet Pane

        Group rootterre = new Group(terre); // Créer un nouveau groupe et y ajouter l'objet Terre

        Group planete = new Group(); // Créer un nouveau groupe pour les planètes

        planete.getChildren().add(rootterre); // Ajouter l'objet Terre au groupe des planètes

        pane.getChildren().addAll(iv); // Ajouter l'imageView au Pane
        pane.getChildren().addAll(planete); // Ajouter le groupe de planètes au Pane

        Scene ihm = new Scene(pane, 1000, 700); // Créer une nouvelle scène avec le Pane et définir les dimensions

        primaryStage.setScene(ihm); // Affecter la scène à la fenêtre
        World w1 = new World("./Ressources/airport-codes_no_comma.csv"); // Créer un objet World

        PerspectiveCamera camera = new PerspectiveCamera(true); // Créer une caméra perspective
        camera.setTranslateZ(-1000); // Définir la distance de la caméra par rapport à l'objet
        camera.setNearClip(1); // Définir la distance minimale de la caméra
        camera.setFarClip(10000); // Définir la distance maximale de la caméra
        camera.setFieldOfView(35); // Définir le champ de vision de la caméra
        ihm.setCamera(camera); // Affecter la caméra à la scène

        primaryStage.show(); // Afficher la fenêtre

        ihm.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) { // Si l'événement est un clic de souris
                PrePosY = event.getY(); // Enregistrer la position Y précédente
                PrePosX = event.getY(); // Enregistrer la position X précédente
                System.out.println("Clicked on : (" + event.getSceneX() + ", " + event.getSceneY() + ")"); // Afficher les coordonnées du clic
            }
            PosY = event.getY(); // Enregistrer la position Y actuelle


            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (PrePosY > PosY) { // Si la position Y précédente est supérieure à la position Y actuelle
                    planete.getTransforms().add(new Rotate(-0.75, Rotate.X_AXIS)); // Faire tourner le groupe de planètes d'un angle de -0.75 sur l'axe X
                }
                else if (PrePosY < PosY) { // Sinon si la position Y précédente est inférieure à la position Y actuelle
                    planete.getTransforms().add(new Rotate(0.75, Rotate.X_AXIS)); // Faire tourner le groupe de planètes d'un angle de 0.75 sur l'axe X
                }
                PrePosY = event.getY(); // Enregistrer la position Y précédente
            }
        });

        ihm.addEventHandler(MouseEvent.ANY, event -> { // Ajouter un gestionnaire d'événements pour la souris
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) { // Si le bouton droit de la souris est cliqué
                PickResult pickResult = event.getPickResult(); // Récupérer les résultats de la sélection
                if (pickResult.getIntersectedNode() != null) { // Si un noeud est sélectionné
                    posObjY = pickResult.getIntersectedPoint().getY(); // Enregistrer la position Y de l'objet sélectionné
                    posObjX = pickResult.getIntersectedTexCoord().getX(); // Enregistrer la position X de l'objet sélectionné
                    System.out.println("Clicked droit : (" + pickResult + ")"); // Afficher les résultats de la sélection

                    newPosObjY = -180.0 * (posObjY - 0.5); // Calculer la nouvelle position Y de l'objet
                    newPosObjX = 360.0 * (posObjX - 0.5); // Calculer la nouvelle position X de l'objet

                    System.out.println("Coordonée clic droit : (" + posObjX + " ; " + posObjY + ")"); // Afficher les coordonnées de l'objet sélectionné
                    System.out.println("New Coordonée clic droit : (" + newPosObjX + " ; " + newPosObjY + ")"); // Afficher les nouvelles coordonnées de l'objet sélectionné

                    System.out.println(w1.findNearestAirport(newPosObjX, newPosObjY)); // Afficher l'aéroport le plus proche de l'objet sélectionné

                    terre.displayOrangeSphere(w1.findNearestAirport(newPosObjX, newPosObjY)); // Afficher une sphère orange à l'emplacement de l'aéroport le plus proche
                }
            }
        });
        primaryStage.addEventHandler(ScrollEvent.SCROLL, event -> { // Ajouter un gestionnaire d'événements pour le défilement de la roulette de la souris
            double delta = event.getDeltaY(); // Récupérer la valeur de défilement
            planete.translateZProperty().set(planete.getTranslateZ() + delta); // Translater le groupe de planètes selon la valeur de défilement
        });

    }

    public static void main(String[] args) {
        launch(args); // Lancer l'application
    }
}
