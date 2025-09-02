package gestion;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import object.*;

public class Reste {

    static double constanteTolerance=30;

    public static double getConstanteTolerance() {
        return constanteTolerance;
    }

    public static void transformer(String idBlock , String[] idFormeUsuelle , double[] quantite,double longueur,double largeur,double hauteur) throws Exception{

       
        for (int i = 0; i < quantite.length; i++) {
            if (quantite[i]<0) {
                throw new Exception("la quantite ne peut pas etre inferieur a 0");
            }
        }

        // block
        Block block=Block.getBlockById(idBlock);

        // if (block.getHauteur()<hauteur || block.getLargeur()< largeur || block.getLongueur() < longueur) {
        //     throw new Exception("les dimension du bloc restant ne peuvent pas etre superieurs a l original");
        // }


        Block newBlock=new Block();
        newBlock.setIdMere(idBlock);
        if (block.getIdOrigine()!=null) {
            newBlock.setIdOrigine(block.getIdOrigine());
        }else{
            newBlock.setIdOrigine(idBlock);
        }
        newBlock.setLongueur(longueur);
        newBlock.setLargeur(largeur);
        newBlock.setHauteur(hauteur);
        newBlock.setPrixRevient(block.getPrixRevientParM3()*newBlock.getVolume());
        newBlock.setDateInsertion(Date.valueOf(LocalDate.now()));

        double originalVolume=block.getVolume();
        double newVolume=newBlock.getVolume();


      

        if (newVolume>originalVolume) {
            throw new Exception("le ouveu volume est superieur a l ancien");
        }

        List<FormeUsuelle> formeUsuelles=new ArrayList<>();
        for (int i = 0; i < idFormeUsuelle.length; i++) {
            FormeUsuelle formeUsuelle=FormeUsuelle.getById(idFormeUsuelle[i]);
            formeUsuelles.add(formeUsuelle);
        }

        List<Stock> stocks=new ArrayList<>();
        int count=0;
        for (FormeUsuelle formeUsuelle : formeUsuelles) {
            if (quantite[count]<0) {
                throw new Exception("la quantite ne doit pas etre negative");
            }
            if (quantite[count]!=0) {
                Stock stock=new Stock();
                stock.setFormeUsuelle(formeUsuelle);
                stock.setBlock(block);
                stock.setQuantite(quantite[count]);
                stock.setDateStock(new Date(System.currentTimeMillis()));
                stocks.add(stock);
            }
            count ++;
        }

        double totalFormeUsuelleVolume=0;

        for (Stock stock : stocks) {
            totalFormeUsuelleVolume+=stock.getFormeUsuelle().getVolume()*stock.getQuantite();
        }

        double theorievolume=newVolume+totalFormeUsuelleVolume;
        System.out.println("newVolume="+newVolume);
        System.out.println("totalFU="+totalFormeUsuelleVolume);



        if (theorievolume>originalVolume) {
            throw new Exception("le volume des formes usuelles et du bloc restant est superieur au bloc original");
        }

        double pctdiff=((originalVolume-theorievolume)/originalVolume)*100;
        System.out.println("origvolume="+originalVolume);
        System.out.println("theorie volume="+theorievolume);
        System.out.println("pct diff="+pctdiff);

        if (pctdiff>constanteTolerance) {
            throw new Exception("la difference  volume origine et mateuriaux sorti superieur a "+constanteTolerance+"% "+pctdiff+"%");
        }

        Block.decoupe(block.getIdBlock());
        newBlock.create();
        for (Stock stock : stocks) {
            stock.create();
        }

        System.out.println("vita decoupe");



    }
    
}
