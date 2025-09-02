package connexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {

    public static Connection getConnexion() {
        Connection connexion = null;
        try {
            Class.forName("org.postgresql.Driver");

            connexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foam",
             "postgres", "postgre");
        } catch (Exception e) {
            e.printStackTrace();  // Affiche la stack trace complète pour le débogage
        }
        return connexion;
    }

}
