package main;

import java.util.List;

import gestion.EtatStockFinancier;
import gestion.Reste;
import gestion.Stock;
import object.Block;
import object.FormeUsuelle;

public class App {
    public static void main(String[] args) {
        try {

            // Block.insertNouveauBlock( 15, 10, 10, 1450);

            // FormeUsuelle formeUsuelle=new FormeUsuelle();
            // formeUsuelle.setNom("Eponge");
            // formeUsuelle.setLongueur(0.1);
            // formeUsuelle.setLargeur(0.1);
            // formeUsuelle.setHauteur(0.1);
            // formeUsuelle.setPrixVente(1);

            // formeUsuelle.create();

            // String[] ids=new String[3];
            // ids[0]="FORM000001";
            // ids[1]="FORM000002";
            // ids[2]="FORM000003";

            // double[] qtes=new double[3];
            // qtes[0]=80;
            // qtes[1]=50;
            // qtes[2]=1000;

            // // Récupérer tous les blocs qui ne sont pas découpés
            // Reste.transformer("BLOC000001",ids , qtes, 50, 2, 1);


            // Stock stock=Block.getBlockById("BLOC000003").getMinVolumeStockFromReste();
            // System.out.println("ID de la Forme Usuelle: " + stock.getFormeUsuelle().getIdFormeUsuelle());
            // System.out.println("Nom de la Forme Usuelle: " + stock.getFormeUsuelle().getNom());
            // System.out.println("Quantité: " + stock.getQuantite());
            // System.out.println(stock.getFormeUsuelle().getPrixVente()/stock.getFormeUsuelle().getVolume());

            // EtatStockFinancier es=new EtatStockFinancier();


            // // Afficher les prix totaux
            // System.out.println("Prix Total Stock Réel : " + es.getPrixTotalStockReel());
            // System.out.println("Prix Total Stock Théorique Max Prix Revient : " + es.getPrixTotalStockMaxPrixRevient());
            // System.out.println("Prix Total Stock Théorique Éponge : " + es.getPrixTotalStockTheoriqueEponge());

            // // Afficher les détails des stocks réels
            // System.out.println("\nDétails du Stock Réel :");
            // for (Stock stock : es.getStockReel()) {
            //     System.out.println("Forme Usuelle ID : " + stock.getFormeUsuelle().getIdFormeUsuelle());
            //     System.out.println("Nom : " + stock.getFormeUsuelle().getNom());
            //     System.out.println("Quantité : " + stock.getQuantite());
            //     System.out.println("Prix Total : " + stock.getPrixTotal());
            //     System.out.println();
            // }

            // // Afficher les détails des stocks théoriques max prix revient
            // System.out.println("\nDétails du Stock Théorique Max Prix Revient :");
            // for (Stock stock : es.getStockTheoriqueMaxPrixRevient()) {
            //     System.out.println("Forme Usuelle ID : " + stock.getFormeUsuelle().getIdFormeUsuelle());
            //     System.out.println("Nom : " + stock.getFormeUsuelle().getNom());
            //     System.out.println("Quantité : " + stock.getQuantite());
            //     System.out.println("Prix Total : " + stock.getPrixTotal());
            //     System.out.println();
            // }

            // // Afficher les détails des stocks théoriques éponge
            // System.out.println("\nDétails du Stock Théorique Éponge :");
            // for (Stock stock : es.getStockTheoriqueEponge()) {
            //     System.out.println("Forme Usuelle ID : " + stock.getFormeUsuelle().getIdFormeUsuelle());
            //     System.out.println("Nom : " + stock.getFormeUsuelle().getNom());
            //     System.out.println("Quantité : " + stock.getQuantite());
            //     System.out.println("Prix Total : " + stock.getPrixTotal());
            //     System.out.println();
            // }

            // Parameters for transformer method
                // String idBlock = "BLOC000001"; // Using the first block 'BLOC000001' with 20x10x5 cm dimensions.
                // String[] idFormeUsuelle = {"FORM000001", "FORM000002"}; // 'Éponge' and 'Matelas King'
                // double[] quantite = {50, 1}; // 50 'Éponge' and 1 'Matelas King'

                // // New block dimensions to create (smaller block from the original block)
                // double longueur = 9; // 10 cm
                // double largeur = 10;  // 5 cm
                // double hauteur = 1;  // 2 cm

                // // Calling the transformer method
                // Reste.transformer(idBlock, idFormeUsuelle, quantite, longueur, largeur, hauteur);

                // Block.updatePrixRevientRecursif("BLOC000001",20);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
