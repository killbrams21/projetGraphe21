/*
 * Classe principale du projet
 */
package spaceconquest;

import spaceconquest.ObjetCeleste.*;
import spaceconquest.Parties.Mode;
import spaceconquest.Partie;
import spaceconquest.Race.Race;

/**
 *
 * @author simonetma
 */
public class SpaceConquest {
    
    private static Partie partie;

    
    public static Race getTour() {
        return partie.getTour();
    }
    
    public static Mode getMode() {
        return partie.getMode();
    }
    

    
    public static void tourSuivant() {
        partie.tourSuivant();
    }
    
    public static void main(String[] args) {
        //on cree la partie
        partie = new Partie(2);
        
        //ajout des éléments clé de la partie
        partie.placerLicoLand(1, 1);
        partie.placerLicoShip(6, 1);
        partie.placerZombificator(1, 2);
        
        //placement des objets célestes
        partie.placerObjetCeleste(new Etoile(), 3, 2);
        partie.placerObjetCeleste(new Asteroide(), 4, 2);
        partie.placerObjetCeleste(new Asteroide(), 5, 2);
        partie.placerObjetCeleste(new Asteroide(), 6, 2);
                
        //on definit le mode de jeu
        partie.setMode(Mode.manuel);
        //on lance l'IHM
        partie.start();
        
        System.out.println(partie.getCarte().getGrapheLicorne());
        
        
    }
    
}
