import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import static java.lang.Double.parseDouble;

public class World {
    ArrayList<Aeroport> list = new ArrayList<>();

    public World(String fileName) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName)); // Créer un buffer pour lire le fichier
            String s = buf.readLine(); // Lire la première ligne du fichier
            while (s != null) {
                s = s.replaceAll("\"", "");// Enlever les guillemets qui séparent les champs de données GPS
                String fields[] = s.split(",");// Découper la ligne en différents champs séparés par une virgule

                if (fields[1].equals("large_airport")) { // Si le champ indique que c'est un grand aéroport
                    list.add(new Aeroport(fields[9], fields[2], fields[5], parseDouble(fields[12]), parseDouble(fields[11]))); // Ajouter l'aéroport à la liste
                }
                s = buf.readLine(); // Lire la prochaine ligne
            }
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there ?");
            //System.out.println(list.get(list.size()-1));
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        World world = new World("./Ressources/airport-codes_no_comma.csv");
        System.out.println(world.findNearestAirport(0, 0));
    }

    public Aeroport findNearestAirport(double latitude, double longitude) {
        Aeroport minimumAeroport = list.get(0); // Initialiser l'aéroport le plus proche à la première entrée de la liste
        double minimum = list.get(0).distance(latitude, longitude); // Initialiser la distance minimale à la distance entre le premier aéroport et les coordonnées données
        for (Aeroport a : list) {
            double minATester = a.distance(latitude, longitude); // Calculer la distance entre les coordonnées données et l'aéroport en cours de test
            if (minATester < minimum) { // Si cette distance est plus petite que la distance minimale actuelle
                minimum = minATester; // Mettre à jour la distance minimale
                minimumAeroport = a; // Mettre à jour l'aéroport le plus proche
            }
        }
        return minimumAeroport; // Retourner l'aéroport le plus proche
    }

    public Aeroport findByCode(String code) {
        return list.stream()// Utilisation de la méthode stream() pour parcourir les éléments de la liste
                .filter(a -> a.getIATA().equals(code))// Utilisation de la méthode filter() pour filtrer les éléments de la liste en fonction de la condition donnée
                .findFirst()// Utilisation de la méthode findFirst() pour retourner le premier élément de la liste filtrée
                .orElse(null);// Utilisation de la méthode orElse() pour retourner null si aucun élément n'a été trouvé
    }
}