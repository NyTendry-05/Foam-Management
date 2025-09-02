package gestion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import object.Block;
import object.FormeUsuelle;

public class Stock {
    private String idStock;
    private FormeUsuelle formeUsuelle; // Remplacer id_forme_usuelle par un objet
    private Block block; // Remplacer id_block par un objet
    private double quantite;
    private Date dateStock; // Utilisation de java.sql.Date

    // Constructeur vide
    public Stock() {
    }

    // Getters et Setters
    public String getIdStock() {
        return idStock;
    }

    public void setIdStock(String idStock) {
        this.idStock = idStock;
    }

    public FormeUsuelle getFormeUsuelle() {
        return formeUsuelle;
    }

    public void setFormeUsuelle(FormeUsuelle formeUsuelle) {
        this.formeUsuelle = formeUsuelle;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Date getDateStock() {
        return dateStock;
    }

    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    public double getPrixTotal(){
        return this.quantite*this.getFormeUsuelle().getPrixVente();
    }

    // Méthode pour créer un nouvel enregistrement dans la base de données
    public void create() throws Exception {
        String sql = "INSERT INTO stock (id_forme_usuelle, id_block, quantite, date_stock) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = Connexion.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // On insère les valeurs des objets formeUsuelle et block
            statement.setString(1, this.formeUsuelle.getIdFormeUsuelle()); // Assurez-vous que ce getter existe
            statement.setString(2, this.block.getIdBlock()); // Assurez-vous que ce getter existe
            statement.setDouble(3, this.quantite);
            statement.setDate(4, this.dateStock);
            
            // Exécute l'instruction SQL
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public double getPrixRevientStock(){
        return formeUsuelle.getVolume()*block.getPrixRevientParM3();
    }

    public double getPrixRevientStockTotal(){
        return this.getPrixRevientStock()*quantite;
    }

    

    public String getIdOrigineBlock(){
        if (this.getBlock().getIdOrigine()!=null) {
            return this.getBlock().getIdOrigine();
        }
        else{
            return this.getBlock().getIdBlock();
        }
    }


    public static List<Stock> getAll() throws Exception {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stock";

        try (Connection connection = Connexion.getConnexion();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Stock stock = new Stock();
                stock.setIdStock(resultSet.getString("id_stock"));

                // Utiliser getById pour récupérer les détails complets
                FormeUsuelle formeUsuelle = FormeUsuelle.getById(resultSet.getString("id_forme_usuelle"));
                stock.setFormeUsuelle(formeUsuelle);

                Block block = Block.getBlockById(resultSet.getString("id_block"));
                stock.setBlock(block);

                stock.setQuantite(resultSet.getDouble("quantite"));
                stock.setDateStock(resultSet.getDate("date_stock"));

                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return stocks;
    }



}
