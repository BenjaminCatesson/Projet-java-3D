import javafx.animation.AnimationTimer; //créer une animation
import javafx.scene.Group; //regrouper des éléments graphiques
import javafx.scene.image.Image; //utiliser des images
import javafx.scene.paint.Color; //utiliser des couleurs
import javafx.scene.paint.PhongMaterial; //appliquer des matériaux à des formes 3D
import javafx.scene.shape.Sphere; //créer des sphères 3D
import javafx.scene.transform.Rotate; //tourner les formes 3D

public class Terre extends Group { //déclare la classe Terre qui étend la classe Group

    private Sphere sphere; //déclare une variable sphere de type Sphere

    public Terre() {
        sphere = new Sphere(250.0); //initialise la variable sphere avec un rayon de 250
        this.getChildren().add(sphere); //ajoute la sphere à la liste des enfants de cette instance de Terre

        PhongMaterial material = new PhongMaterial(); //déclare une variable material de type PhongMaterial
        material.setDiffuseMap(new Image("file:./Ressources/earth2.jpg")); //applique une image diffuse à material en utilisant l'image spécifiée
        sphere.setMaterial(material); //applique le matériau material à sphere

        Rotate ry = new Rotate(1, Rotate.Y_AXIS); //déclare une variable ry de type Rotate qui tourne autour de l'axe Y à un angle de 1
        this.getTransforms().add(ry); //ajoute la transformation ry à la liste

        AnimationTimer animationTimer = new AnimationTimer() { //déclare une variable animationTimer de type AnimationTimer
            @Override
            public void handle(long t) { //surclasse la méthode handle pour gérer l'animation
                ry.setAngle(t * 1.15e-8); //modifie l'angle de rotation de ry en fonction de t
            }
        };
        animationTimer.start(); //démarre l'animation
    }

    public Sphere createSphere(Aeroport a, Color color) { //déclare une méthode createSphere qui prend en paramètre un objet Aeroport et une couleur
        Sphere circle = new Sphere(2); //déclare une variable circle de type Sphere avec un rayon de 2
        double teta = a.getlatitude(); //récupère la latitude de l'objet Aeroport
        double phi = a.getlongitude(); //récupère la longitude de l'objet Aeroport
        double Rayon = sphere.getRadius(); //récupère le rayon de la sphère sphere
        double X = Rayon * Math.cos(Math.toRadians(teta * 0.65)) * Math.sin(Math.toRadians(phi)); //calcule la coordonnée X de la sphère circle en fonction de teta, phi et Rayon
        double Y = -Rayon * Math.sin(Math.toRadians(teta * 0.65)); //calcule la coordonnée Y de la sphère circle en fonction de teta et Rayon
        double Z = -Rayon * Math.cos(Math.toRadians(teta * 0.65)) * Math.cos(Math.toRadians(phi)); //calcule la coordonnée Z de la sphère circle en fonction de teta, phi et Rayon

        circle.setTranslateX(X); //modifie la coordonnée X de la translation de la sphère circle
        circle.setTranslateY(Y); //modifie la coordonnée Y de la translation de la sphère circle
        circle.setTranslateZ(Z); //modifie la coordonnée Z de la translation de la sphère circle
        PhongMaterial material = new PhongMaterial(color); //déclare une variable material de type PhongMaterial avec la couleur donnée en paramètre
        circle.setMaterial(material); //applique le matériau material à la sphère circle
        return circle; //retourne la sphère circle
    }

    public void displayOrangeSphere(Aeroport a) { //déclare une méthode displayOrangeSphere qui prend en paramètre un objet Aeroport
        Sphere Orangesphere = createSphere(a, Color.ORANGE); //appelle la méthode createSphere avec l'objet Aeroport et la couleur Orange pour créer une sphère Orange
        this.getChildren().add(Orangesphere); //ajoute la sphère Orange à la liste des enfants de cette instance de Terre
    }

    public static void main(String[] args) {
    }
}
