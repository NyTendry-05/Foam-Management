package object;

import connexion.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormeUsuelle {
    private String idFormeUsuelle;
    private String nom;
    private double longueur;
    private double largeur;
    private double hauteur;
    private double prixVente;

    // Constructeur vide
    public FormeUsuelle() {
    }

    // Getters et Setters
    public String getIdFormeUsuelle() {
        return idFormeUsuelle;
    }

    public void setIdFormeUsuelle(String idFormeUsuelle) {
        this.idFormeUsuelle = idFormeUsuelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double getVolume() {
        return longueur * largeur * hauteur;
    }

    // CRUD Methods

    // Méthode CREATE
    public void create() throws Exception {
        String sql = "INSERT INTO forme_usuelle (nom, longueur, largeur, hauteur, prix_vente) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.getNom());
            statement.setDouble(2, this.getLongueur());
            statement.setDouble(3, this.getLargeur());
            statement.setDouble(4, this.getHauteur());
            statement.setDouble(5, this.getPrixVente());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode READ - Récupérer un FormeUsuelle par son ID
    public static FormeUsuelle getById(String idFormeUsuelle) throws SQLException {
        String sql = "SELECT * FROM forme_usuelle WHERE id_forme_usuelle = ?";
        FormeUsuelle formeUsuelle = null;

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idFormeUsuelle);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                formeUsuelle = new FormeUsuelle();
                formeUsuelle.setIdFormeUsuelle(resultSet.getString("id_forme_usuelle"));
                formeUsuelle.setNom(resultSet.getString("nom"));
                formeUsuelle.setLongueur(resultSet.getDouble("longueur"));
                formeUsuelle.setLargeur(resultSet.getDouble("largeur"));
                formeUsuelle.setHauteur(resultSet.getDouble("hauteur"));
                formeUsuelle.setPrixVente(resultSet.getDouble("prix_vente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return formeUsuelle;
    }

    // Méthode READ - Récupérer toutes les FormeUsuelle
    public static List<FormeUsuelle> getAll() throws SQLException {
        List<FormeUsuelle> formesUsuelles = new ArrayList<>();
        String sql = "SELECT * FROM forme_usuelle";

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                FormeUsuelle formeUsuelle = new FormeUsuelle();
                formeUsuelle.setIdFormeUsuelle(resultSet.getString("id_forme_usuelle"));
                formeUsuelle.setNom(resultSet.getString("nom"));
                formeUsuelle.setLongueur(resultSet.getDouble("longueur"));
                formeUsuelle.setLargeur(resultSet.getDouble("largeur"));
                formeUsuelle.setHauteur(resultSet.getDouble("hauteur"));
                formeUsuelle.setPrixVente(resultSet.getDouble("prix_vente"));

                formesUsuelles.add(formeUsuelle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return formesUsuelles;
    }

    // Méthode UPDATE
    public void update() throws Exception {
        String sql = "UPDATE forme_usuelle SET nom = ?, longueur = ?, largeur = ?, hauteur = ?, prix_vente = ? WHERE id_forme_usuelle = ?";
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.getNom());
            statement.setDouble(2, this.getLongueur());
            statement.setDouble(3, this.getLargeur());
            statement.setDouble(4, this.getHauteur());
            statement.setDouble(5, this.getPrixVente());
            statement.setString(6, this.getIdFormeUsuelle());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode DELETE
    public static void deleteById(String idFormeUsuelle) throws Exception {
        String sql = "DELETE FROM forme_usuelle WHERE id_forme_usuelle = ?";
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idFormeUsuelle);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static FormeUsuelle getHighestPrixVentePerVolume() throws SQLException {
        String sql = "SELECT *, (prix_vente / (longueur * largeur * hauteur)) AS prixVenteParVolume " +
                     "FROM forme_usuelle " +
                     "WHERE longueur * largeur * hauteur > 0 " + // Évite la division par zéro
                     "ORDER BY prixVenteParVolume DESC " +
                     "LIMIT 1";

        FormeUsuelle formeUsuelle = null;

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                formeUsuelle = new FormeUsuelle();
                formeUsuelle.setIdFormeUsuelle(resultSet.getString("id_forme_usuelle"));
                formeUsuelle.setNom(resultSet.getString("nom"));
                formeUsuelle.setLongueur(resultSet.getDouble("longueur"));
                formeUsuelle.setLargeur(resultSet.getDouble("largeur"));
                formeUsuelle.setHauteur(resultSet.getDouble("hauteur"));
                formeUsuelle.setPrixVente(resultSet.getDouble("prix_vente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return formeUsuelle;
    }

    public static FormeUsuelle getSmallestVolumeFormeUsuelle() throws SQLException {
        String sql = "SELECT *, (longueur * largeur * hauteur) AS volume " +
                     "FROM forme_usuelle " +
                     "WHERE longueur * largeur * hauteur > 0 " + // Évite la division par zéro
                     "ORDER BY volume ASC " +
                     "LIMIT 1";
    
        FormeUsuelle formeUsuelle = null;
    
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
    
            if (resultSet.next()) {
                formeUsuelle = new FormeUsuelle();
                formeUsuelle.setIdFormeUsuelle(resultSet.getString("id_forme_usuelle"));
                formeUsuelle.setNom(resultSet.getString("nom"));
                formeUsuelle.setLongueur(resultSet.getDouble("longueur"));
                formeUsuelle.setLargeur(resultSet.getDouble("largeur"));
                formeUsuelle.setHauteur(resultSet.getDouble("hauteur"));
                formeUsuelle.setPrixVente(resultSet.getDouble("prix_vente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return formeUsuelle;
    }
    

}
