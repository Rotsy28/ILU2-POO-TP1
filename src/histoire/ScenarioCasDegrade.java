package histoire;


import villagegaulois.Etal;
import personnages.Gaulois;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
        Etal etal = new Etal();
        
        // Test 1 : acheteur null
        System.out.println("Test 1 : acheteur null");
        etal.acheterProduit(1, null);

        // Test 2 : quantité non positive
        System.out.println("Test 2 : quantité nulle");
        Gaulois acheteur = new Gaulois("Obélix", 10);

        try {
            etal.acheterProduit(0, acheteur);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception : " + e);
        }

        // Test 3 : étal vide
        System.out.println("Test 3 : étal vide");

        try {
            etal.acheterProduit(1, acheteur);
        } catch (IllegalStateException e) {
            System.out.println("Exception : " + e);
        }
        
        Village village = new Village("le village des irréductibles", 10, 5);

        System.out.println("Test 4 : village sans chef ");
        try {
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.out.println("Erreur : " + e.getMessage());
        }


        System.out.println("Fin du test");
   

    }
}
