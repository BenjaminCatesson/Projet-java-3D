public class Aeroport {
    private final String IATA; // code IATA de l'aéroport
    private final String Name; // nom de l'aéroport
    private final String country; // pays de l'aéroport
    private final double longitude; // longitude de l'aéroport
    private final double latitude; // latitude de l'aéroport

    public String getIATA() { // méthode pour récupérer le code IATA de l'aéroport
        return IATA;
    }

    public double getlongitude() { // méthode pour récupérer la longitude de l'aéroport
        return longitude;
    }

    public double getlatitude() { // méthode pour récupérer la latitude de l'aéroport
        return latitude;
    }

    @Override
    public String toString() { // redéfinition de la méthode toString pour afficher les informations de l'aéroport
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", Name='" + Name + '\'' +
                ", country='" + country + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public Aeroport(String IATA, String name, String country, double latitude, double longitude) { // constructeur pour créer un nouvel objet Aeroport
        this.IATA = IATA;
        this.Name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double distance(double latitude, double longitude) // calcule la distance entre l'aéroport et les coordonnées données
    {
        double calcul = Math.pow((latitude - this.latitude), 2) + Math.pow((longitude - this.longitude) * Math.cos(Math.toRadians((latitude + this.latitude) / 2)), 2);
        return calcul;
    }

    public static void main(String[] args) {
        Aeroport A1 = new Aeroport("orl", "Orly", "France", 10.0, 20.5); // crée un objet Aeroport avec les informations donnéesSystem.out.println(A1); // affiche les informations de l'objet Aeroport créé
    }
}
