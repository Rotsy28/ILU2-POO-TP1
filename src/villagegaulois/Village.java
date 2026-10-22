package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;


public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
	        throw new VillageSansChefException(
	                "Impossible d'afficher les villageois : le village n'a pas de chef.");
	    }
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private class Marche{
		private Etal[] etals;

		private Marche(int nb_etals) {
			etals = new Etal[nb_etals];
			for (int i=0; i< etals.length; i++) {
				etals[i]= new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
			}		
		}
		 
		private int trouverEtalLibre() {
			int i=0;
			while (i< etals.length && etals[i].isEtalOccupe()) {
				i++;
			}
			if (i< etals.length) {
				return i;
			}else {
				return -1;
			}
		}
		
		private Etal[] trouverEtals(String produit) {
			int cpt=0;
			int ind=0;
			for(int i=0; i< etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					cpt++;
				}
			}
			Etal[] etalsproduit= new Etal[cpt];
			for(int i=0; i< etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsproduit[ind]= etals[i];
					ind++;
				}
			}
			return etalsproduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
		    int i = 0;
		    while (i < etals.length) {
		        if (etals[i].isEtalOccupe()
		                && etals[i].getVendeur().equals(gaulois)) {
		            return etals[i];
		        }
		        i++;
		    }
		    return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide=0;
		    chaine.append("Le marché du village \"" + Village.this.nom
		            + "\" possède plusieurs étals :\n");
			for(int i=0; i< etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide > 0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    StringBuilder chaine = new StringBuilder();

	    chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
	            + nbProduit + " " + produit + ".\n");

	    int indice = marche.trouverEtalLibre();

	    if (indice != -1) {
	        marche.utiliserEtal(indice, vendeur, produit, nbProduit);
	        chaine.append("Le vendeur " + vendeur.getNom() + " vend des "
	                + produit + " à l'étal n°" + (indice + 1) + ".\n");
	    }

	    return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
	    StringBuilder chaine = new StringBuilder();
	    Etal[] etalsTrouves = marche.trouverEtals(produit);

	    if (etalsTrouves.length == 0) {
	        chaine.append("Il n'y a pas de vendeur qui propose des "
	                + produit + " au marché.\n");
	    } else if (etalsTrouves.length == 1) {
	        chaine.append("Seul le vendeur "
	                + etalsTrouves[0].getVendeur().getNom()
	                + " propose des " + produit + " au marché.\n");
	    } else {
	        chaine.append("Les vendeurs qui proposent des "
	                + produit + " sont :\n");

	        for (int i = 0; i < etalsTrouves.length; i++) {
	            chaine.append("- " + etalsTrouves[i].getVendeur().getNom() + "\n");
	        }
	    }

	    return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
	    return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
	    Etal etal = rechercherEtal(vendeur);

	    if (etal != null) {
	        return etal.libererEtal();
	    }

	    return "";
	}
	
	public String afficherMarche() {
		
	    return marche.afficherMarche();
	}
}





