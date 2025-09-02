package gestion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import object.Block;

public class EtatStockFinancier {
    List<Stock> stockReel;
    List<Block> blockNonDecoupe;
    List<Stock> stockTheoriqueMaxPrixRevient;
    List<Stock> stockTheoriqueEponge;
    
    double prixTotalStockReel; /*stock reel */
    
    double prixTotalStockMaxPrixRevient; /*stock maxprix revient */
    double montantTheoriqueMaxprix; /*stock maxprix revient + reel */


    double prixTotalStockTheoriqueEponge; /* eponge */
    double montantTheoriqueEponge; /* eponge + reel */

    
    public double getMontantTheoriqueMaxprix() {
        return montantTheoriqueMaxprix;
    }
    public void setMontantTheoriqueMaxprix(double montantTheoriqueMaxprix) {
        this.montantTheoriqueMaxprix = montantTheoriqueMaxprix;
    }

    public double getMontantTheoriqueEponge() {
        return montantTheoriqueEponge;
    }
    public void setMontantTheoriqueEponge(double montantTheoriqueEponge) {
        this.montantTheoriqueEponge = montantTheoriqueEponge;
    }
    public double getPrixTotalStockReel() {
        return prixTotalStockReel;
    }
    public void setPrixTotalStockReel(double prixTotalStockReel) {
        this.prixTotalStockReel = prixTotalStockReel;
    }
    public double getPrixTotalStockMaxPrixRevient() {
        return prixTotalStockMaxPrixRevient;
    }
    public void setPrixTotalStockMaxPrixRevient(double prixTotalStockMaxPrixRevient) {
        this.prixTotalStockMaxPrixRevient = prixTotalStockMaxPrixRevient;
    }
    public double getPrixTotalStockTheoriqueEponge() {
        return prixTotalStockTheoriqueEponge;
    }
    public void setPrixTotalStockTheoriqueEponge(double prixTotalStockTheoriqueEponge) {
        this.prixTotalStockTheoriqueEponge = prixTotalStockTheoriqueEponge;
    }
    public List<Stock> getStockReel() {
        return stockReel;
    }
    public void setStockReel(List<Stock> stockReel) {
        this.stockReel = stockReel;
    }
    public List<Block> getBlockNonDecoupe() {
        return blockNonDecoupe;
    }
    public void setBlockNonDecoupe(List<Block> blockNonDecoupe) {
        this.blockNonDecoupe = blockNonDecoupe;
    }
    public List<Stock> getStockTheoriqueMaxPrixRevient() {
        return stockTheoriqueMaxPrixRevient;
    }
    public void setStockTheoriqueMaxPrixRevient(List<Stock> stockTheoriqueMaxPrixRevient) {
        this.stockTheoriqueMaxPrixRevient = stockTheoriqueMaxPrixRevient;
    }
    public List<Stock> getStockTheoriqueEponge() {
        return stockTheoriqueEponge;
    }
    public void setStockTheoriqueEponge(List<Stock> stockTheoriqueEponge) {
        this.stockTheoriqueEponge = stockTheoriqueEponge;
    }
    



    public EtatStockFinancier() throws Exception {
        this.initialise();
    }

    public void initialise() throws Exception{
        this.setStockReel(Stock.getAll());
        this.setBlockNonDecoupe(Block.getBlocksNotDecoupe());
        this.initialiseStockMaxPrix();
        this.initialiseStockEponge();
        this.initialisePrixTotalStockReel();
        this.initialisePrixTotalMaxPrixRevient();
        this.initialisePrixTotalEponge();
        this.setMontantTheoriqueEponge(prixTotalStockReel+prixTotalStockTheoriqueEponge);
        this.setMontantTheoriqueMaxprix(prixTotalStockReel+prixTotalStockMaxPrixRevient);
    }

    public void initialiseStockMaxPrix() throws SQLException{
        List<Stock> stocks=new ArrayList<>();
        for (Block block : blockNonDecoupe) {
            Stock stock=block.getMAxProfitStockFromReste();
            stocks.add(stock);
        }
        this.setStockTheoriqueMaxPrixRevient(stocks);
    }

    
    public void initialiseStockEponge() throws SQLException{
        List<Stock> stocks=new ArrayList<>();
        for (Block block : blockNonDecoupe) {
            Stock stock=block.getMinVolumeStockFromReste();
            stocks.add(stock);
        }
        this.setStockTheoriqueEponge(stocks);
    }


    public void initialisePrixTotalStockReel(){
        double sum=0;
        for (Stock stock : stockReel) {
            sum+=stock.getPrixTotal();
        }
        this.setPrixTotalStockReel(sum);
    }

    
    public void initialisePrixTotalMaxPrixRevient(){
        double sum=0;
        for (Stock stock : stockTheoriqueMaxPrixRevient) {
            sum+=stock.getPrixTotal();
        }
        this.setPrixTotalStockMaxPrixRevient(sum);
    }


    
    public void initialisePrixTotalEponge(){
        double sum=0;
        for (Stock stock : stockTheoriqueEponge) {
            sum+=stock.getPrixTotal();
        }
        this.setPrixTotalStockTheoriqueEponge(sum);
    }
    
    
}
