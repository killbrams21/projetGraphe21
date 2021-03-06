/*
 * Classe de gestion des graphes
 */
package spaceconquest;

import java.util.HashMap;
import spaceconquest.Map.Couple;

/**
 *
 * @author simonetma
 */

public class Graphe {
    private int nbSommet;
    private HashMap<Couple,Integer> matrice;
    private Boolean orienté;
    
    //constructeur
    public Graphe(int n) {
        this.nbSommet = n;
        this.matrice = new HashMap<>();
        this.orienté = false;
    }
    
    //renvoie le nombre de sommet du graphe    
    public int getNbSommet() {
        return this.nbSommet;
    }
    
    
    //ajoute un arc (modification de la matrice) entre 2 sommets
    public void ajouterArc(int deb,int fin,int val){
        this.modifierMatrice(deb, fin, val);
        if (!this.getOrientation()){
            this.modifierMatrice(fin,deb,val);
        }
    }  
    
    //isole un sommet du graphe
    public void isolerSommet(int som){
        for(int i =1;i<=this.getNbSommet();i++){
            this.modifierMatrice(som, i, 0);
            this.modifierMatrice(i, som, 0);
        }
    }   
    
    public void asteroideSommet(int som){
        for(int i=1;i<=this.getNbSommet();i++){
            for(int j=1; j<=this.getNbSommet();j++){
                if(this.getMatrice(i, j)==1&&j==som){
                    this.modifierMatrice(i, j, 2);
                }
            }
        }   
    }
    
    //*************** gestion de la matrice d'adjacence ***********************
    //Modifie la valeur (i,j) de la matrice d'adjacence du graphe
    public void modifierMatrice(int i,int j,int valeur) {
        if(i<=0 || j<=0) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient ("+i+","+j+") !");
        }
        else if(i>this.nbSommet || j>this.nbSommet) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient ("+i+","+j+") !");
        }
        else
        {
            Couple c = new Couple(i,j);
            this.matrice.put(c, valeur);
        }
    }
    
    //renvoie la valeur du coefficient (i,j) de la matrice d'adjacence (0 par défaut)
    public int getMatrice(int i,int j) {
        if(i<=0 || j<=0) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient ("+i+","+j+") !");
        }
        else if(i>this.nbSommet || j>this.nbSommet) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient ("+i+","+j+") !");
        }
        else {
            Couple c = new Couple(i,j);
            if(this.matrice.containsKey(c)) {
                return this.matrice.get(c);
            }
        }
        return 0;
    }
    
    
    

    //renvoie l'orientation
    public boolean getOrientation() {
        return this.orienté;
    }
    
    //affiche la matrice d'adjaceance
    @Override
    public String toString() {
        String ret = "<html><center>Matrice du graphe :<br><br>";
        for(int i=1;i<=this.nbSommet;i++) {
            for(int j=1;j<=this.nbSommet;j++) {
                Couple c = new Couple(i,j);
                if(this.matrice.containsKey(c)) {
                    ret += this.matrice.get(c);
                }
                else {
                    ret += "0";
                }
                if(j<this.nbSommet) {
                    ret+= " ";
                }
            }
            if(i<this.nbSommet) {
                ret+="<br>";
            }
        }
        ret += "</center></html>";
        return ret;
    }
    
    
    //
}
