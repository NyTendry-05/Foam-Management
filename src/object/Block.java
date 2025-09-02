package object;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import gestion.Stock;

public class Block {
    private String idBlock;
    private String idMere;
    private String idOrigine;
    private double longueur;
    private double largeur;
    private double hauteur;
    private double prixRevient;
    private Date dateInsertion;
    private Date dateDecoupe;

    // Constructeur vide
    public Block() {}

    // Getters et Setters
    public String getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(String idBlock) {
        this.idBlock = idBlock;
    }

    public String getIdMere() {
        return idMere;
    }

    public void setIdMere(String idMere) {
        this.idMere = idMere;
    }

    public String getIdOrigine() {
        return idOrigine;
    }

    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) throws Exception {
        if (longueur<=0) {
            throw new Exception("la longueur doit etre superieur a a 0");
        }
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) throws Exception {
        if (largeur<=0) {
            throw new Exception("la largeur doit etre superieur a a 0");
        }
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) throws Exception {
        if (hauteur<=0) {
            throw new Exception("la hauteur doit etre superieur a a 0");
        }
        this.hauteur = hauteur;
    }

    public double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(double prixRevient) throws Exception {
        if (prixRevient<=0) {
            throw new Exception("le prix ne doit pas etre  <= 0 ");
        }
        this.prixRevient = prixRevient;
    }

    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public Date getDateDecoupe() {
        return dateDecoupe;
    }

    public void setDateDecoupe(Date dateDecoupe) {
        this.dateDecoupe = dateDecoupe;
    }

    // Nouvelle méthode pour obtenir le volume
    public double getVolume() {
        return longueur * largeur * hauteur;
    }

    public double getPrixRevientParM3() {
        double volume = getVolume();
        return (volume > 0) ? prixRevient / volume : 0; // Prix de revient par mètre cube
    }



    public static List<Block> getFille(String idBlock) throws Exception {
        List<Block> blocks = new ArrayList<>();
        retrieveFilleRecursively(idBlock, blocks);
        return blocks;
    }

    // Fonction récursive pour récupérer tous les descendants d'un bloc donné
    private static void retrieveFilleRecursively(String idBlock, List<Block> blocks) throws Exception {
        String sql = "SELECT * FROM block WHERE id_mere = ?";
        
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idBlock);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Block block = new Block();
                block.setIdBlock(resultSet.getString("id_block"));
                block.setIdMere(resultSet.getString("id_mere"));
                block.setIdOrigine(resultSet.getString("id_origine"));
                block.setLongueur(resultSet.getDouble("longueur"));
                block.setLargeur(resultSet.getDouble("largeur"));
                block.setHauteur(resultSet.getDouble("hauteur"));
                block.setPrixRevient(resultSet.getDouble("prix_revient"));
                block.setDateInsertion(resultSet.getDate("date_insertion"));
                block.setDateDecoupe(resultSet.getDate("date_decoupe"));

                blocks.add(block);

                // Appel récursif pour les fils de ce bloc
                retrieveFilleRecursively(block.getIdBlock(), blocks);
            }
        }
    }

    public static void updatePrixRevient(String idBlock, double prixRevientNew) throws Exception {

        if (prixRevientNew<=0) {
            throw new Exception("le nouveau prix ne doit pas etre  <= 0 ");
        }

        String sql = "UPDATE block SET prix_revient = ? WHERE id_block = ?";
    
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setDouble(1, prixRevientNew);  // Définir le nouveau prix de revient
            statement.setString(2, idBlock);         // Définir l'ID du bloc à mettre à jour
    
            statement.executeUpdate();  // Exécuter la mise à jour
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Relancer l'exception pour une gestion d'erreur éventuelle dans l'appelant
        }
    }


    public static void updatePrixRevientRecursif(String idBlock,double prixRevientNew) throws Exception{
        Block block1=Block.getBlockById(idBlock);
        
        double ratio=prixRevientNew/block1.getPrixRevient();

        updatePrixRevient(idBlock, prixRevientNew);
        
        List<Block> blocks=Block.getFille(idBlock);
        for (Block block : blocks) {
            updatePrixRevient(block.getIdBlock(),block.getPrixRevient()*ratio);
        }

    }
    



    // Création d'un nouveau bloc avec date d'insertion et découpe définies par les attributs
    public void create() throws Exception {
        String sql = "INSERT INTO block (id_mere, id_origine, longueur, largeur, hauteur, prix_revient, date_insertion, date_decoupe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.getIdMere());
            statement.setString(2, this.getIdOrigine());
            statement.setDouble(3, this.getLongueur());
            statement.setDouble(4, this.getLargeur());
            statement.setDouble(5, this.getHauteur());
            statement.setDouble(6, this.getPrixRevient());
            statement.setDate(7, this.getDateInsertion()); // Utilise dateInsertion de l'objet
            statement.setDate(8, this.getDateDecoupe());   // Utilise dateDecoupe de l'objet

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Marque un bloc comme découpé en définissant la date de découpe
    public static void decoupe(String idBlock) throws SQLException {
        String sql = "UPDATE block SET date_decoupe = ? WHERE id_block = ?";

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDate.now())); // Utilise dateDecoupe de l'objet
            statement.setString(2, idBlock);

            statement.executeUpdate();
        }
    }

    // Récupère tous les blocs non découpés (date_decoupe est NULL)
    public static List<Block> getBlocksNotDecoupe() throws Exception {
        List<Block> blocks = new ArrayList<>();
        String sql = "SELECT * FROM block WHERE date_decoupe IS NULL";

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Block block = new Block();
                block.setIdBlock(resultSet.getString("id_block"));
                block.setIdMere(resultSet.getString("id_mere"));
                block.setIdOrigine(resultSet.getString("id_origine"));
                block.setLongueur(resultSet.getDouble("longueur"));
                block.setLargeur(resultSet.getDouble("largeur"));
                block.setHauteur(resultSet.getDouble("hauteur"));
                block.setPrixRevient(resultSet.getDouble("prix_revient"));
                block.setDateInsertion(resultSet.getDate("date_insertion"));
                block.setDateDecoupe(resultSet.getDate("date_decoupe"));

                blocks.add(block);
            }
        }
        return blocks;
    }


    public static List<Block> getAll() throws Exception {
        List<Block> blocks = new ArrayList<>();
        String sql = "SELECT * FROM block";

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Block block = new Block();
                block.setIdBlock(resultSet.getString("id_block"));
                block.setIdMere(resultSet.getString("id_mere"));
                block.setIdOrigine(resultSet.getString("id_origine"));
                block.setLongueur(resultSet.getDouble("longueur"));
                block.setLargeur(resultSet.getDouble("largeur"));
                block.setHauteur(resultSet.getDouble("hauteur"));
                block.setPrixRevient(resultSet.getDouble("prix_revient"));
                block.setDateInsertion(resultSet.getDate("date_insertion"));
                block.setDateDecoupe(resultSet.getDate("date_decoupe"));

                blocks.add(block);
            }
        }
        return blocks;
    }

    // Récupère un bloc par son ID
    public static Block getBlockById(String idBlock) throws Exception {
        String sql = "SELECT * FROM block WHERE id_block = ?";
        Block block = null;

        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idBlock);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                block = new Block();
                block.setIdBlock(resultSet.getString("id_block"));
                block.setIdMere(resultSet.getString("id_mere"));
                block.setIdOrigine(resultSet.getString("id_origine"));
                block.setLongueur(resultSet.getDouble("longueur"));
                block.setLargeur(resultSet.getDouble("largeur"));
                block.setHauteur(resultSet.getDouble("hauteur"));
                block.setPrixRevient(resultSet.getDouble("prix_revient"));
                block.setDateInsertion(resultSet.getDate("date_insertion"));
                block.setDateDecoupe(resultSet.getDate("date_decoupe"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return block;
    }

    // Méthode utilitaire pour insérer un nouveau bloc avec date d'insertion définie à la date actuelle
    public static void insertNouveauBlock(double longueur, double largeur, double hauteur, double prixRevient) throws Exception {
        Block block = new Block();
        block.setLongueur(longueur);
        block.setLargeur(largeur);
        block.setHauteur(hauteur);
        block.setPrixRevient(prixRevient);
        block.setDateInsertion(Date.valueOf(LocalDate.now())); // Définit date d'insertion à aujourd'hui
        block.create();
    }


    public Stock getMAxProfitStockFromReste() throws SQLException{

        FormeUsuelle rentableFU=FormeUsuelle.getHighestPrixVentePerVolume();

        double volumeFU=rentableFU.getVolume();
        double volumeBlock=this.getVolume();

        // System.out.println(volumeBlock);
        // System.out.println(volumeFU);
        // System.out.println(volumeBlock/volumeFU);

        int quantite=(int) Math.floor(volumeBlock/volumeFU);

        Stock stocktheorique=new Stock();

        stocktheorique.setBlock(this);
        stocktheorique.setFormeUsuelle(rentableFU);
        stocktheorique.setQuantite(quantite);
        stocktheorique.setDateStock(Date.valueOf(LocalDate.now()));
        return stocktheorique;


    } 

    
    public Stock getMinVolumeStockFromReste() throws SQLException{

        FormeUsuelle rentableMV=FormeUsuelle.getSmallestVolumeFormeUsuelle();

        double volumeMV=rentableMV.getVolume();
        double volumeBlock=this.getVolume();

        // System.out.println(volumeBlock);
        // System.out.println(volumeMV);
        // System.out.println(volumeBlock/volumeMV);

        int quantite=(int) Math.floor(volumeBlock/volumeMV);

        Stock stocktheorique=new Stock();

        stocktheorique.setBlock(this);
        stocktheorique.setFormeUsuelle(rentableMV);
        stocktheorique.setQuantite(quantite);
        stocktheorique.setDateStock(Date.valueOf(LocalDate.now()));
        return stocktheorique;

        
        
    } 



}
