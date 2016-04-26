/*
 * Gestion de la carte
 */
package spaceconquest;

import java.awt.Color;
import java.util.HashMap;   
import spaceconquest.Map.Case;
import spaceconquest.Map.Couleur;
import spaceconquest.Map.Couple;
import spaceconquest.ObjetCeleste.ObjetCeleste;
import spaceconquest.Race.Race;
import spaceconquest.Race.Vaisseau;



public class Carte {
    private int taille;                                                         //nombre de "colonne" de la map, (la map a 3 fois plus de lignes que de colonnes)
    private HashMap<Couple,Case> cases;                                         //listes des cases
    private Couple caseSelectionnee;                                            //case actuellement sélectionnée par le joueur
    
    //Constructeur
    public Carte(int _taille) {
        this.taille = _taille;
        this.cases = new HashMap<>(); 
        //initialisation de la map vide
        for(int i=1;i<= 3*_taille;i++) {
            for(int j=1;j<=_taille;j++) {
                this.cases.put(new Couple(i,j), new Case());
            }
        } 
        this.caseSelectionnee = null;
    }
    
    
    //getteur de la taille de la map
    public int getTaille() {
        return this.taille;
    }
    
    //getteur de la case en position i,j
    public Case getCase(int i,int j) {
        return this.cases.get(new Couple(i,j));
    }
    
    //getteur de la case en position c (couple)
    public Case getCase(Couple c) {
        return this.cases.get(c);
    }
    
    //met toutes les cases du plateau de jeu en blanc
    public void effacerColoration() {
        for(int i=1;i<= 3* this.getTaille();i++) {
            for(int j=1;j<=this.getTaille();j++) {
                this.getCase(i,j).setCouleur(Couleur.Blanc);
            }   
        } 
    }
    
    
    public void colorationMouvements(Couple c, Graphe g){
        int n=this.getTaille(); //taille du plateau
        int pos=(c.getX()-1)*n+c.getY(); //sommet correspondant aux coordonnées de départ
        int L=0; //entier des lignes
        int C=0; //entier des colonnes
        
        
        for (int i=1; i<=n*n*3; i++){
            if(g.getMatrice(pos, i)==2){ //on teste si le sommet initial est relié aux autres sommets du graphe avec une valeur de 2
                
                if(i%n==0){ //récupération des coordonnées en fonction du sommet
                    L=i/n;
                    C=n;
                }else{
                    L=(i/n)+1;
                    C=i%n;
                }
                this.getCase(L, C).setCouleur(Couleur.Jaune); //colorie en jaune
            }
            
            if(g.getMatrice(pos, i)==1){ //on teste si le sommet initial est relié aux autres sommets du graphe avec une valeur de 1
                
                if(i%n==0){ //récupération des coordonnées en fonction du sommet
                    L=i/n;
                    C=n;
                }else{
                    L=(i/n)+1;
                    C=i%n;
                }
                this.getCase(L, C).setCouleur(Couleur.Vert);  //colorie en vert
                
                for(int j=1; j<=n*n*3; j++){ 
                    if(g.getMatrice(i, j)==1 && j!=pos){ //on teste si les sommets reliés avec le sommet initial avec une valeur de 1 sont eux-mêmes reliés à d'autres sommets avec une valeur de 1, on enlève
                        if(g.getMatrice(j, pos)==0){
                           
                            if(j%n==0){ //récupération des coordonnées en fonction du sommet
                                L=j/n;
                                C=n;
                            }else{
                                L=(j/n)+1;
                                C=j%n;
                            }
                         this.getCase(L, C).setCouleur(Couleur.Jaune); //colorie en jaune
                        }
                        
                    }
                }
            }            
                
                
        }
    }
    
  
    
    //renvoie le graphe modélisant la grille hexagonale (sans contrainte)s
    public Graphe getGrapheGrille(){
        int n = this.getTaille();
        Graphe g = new Graphe(n*n*3);
        for (int L=1;L<=n*3;L++){
            for (int C=1;C<=n;C++){
                int pos=(L-1)*n+C;

                if(L+2<=n*3){
                    g.ajouterArc(pos, pos+2*n, 1);
                }

                if(L%2==1){
                    if(L+1<=n*3){
                        g.ajouterArc(pos, pos+n, 1);
                    }                   
                    if(L+1<=n*3&&C+1<=n){
                        g.ajouterArc(pos, pos+n+1, 1);
                    } 
                }

                if(L%2==0){
                   if(L+1<=n*3){
                        g.ajouterArc(pos, pos+n, 1);
                    }
                   if(L+1<=n*3&&C-1>=1){
                        g.ajouterArc(pos, pos+n-1, 1);
                    }   
                }
            }       
        }
        return g;
    }
    
    
    public Graphe getGrapheZombie(){
        Graphe g = this.getGrapheGrille();
        int n = this.getTaille();
            for (int L=1;L<=n*3;L++){
                for (int C=1;C<=n;C++){
                    int pos=(L-1)*n+C;
                    
                    Case c = this.getCase(L, C);
                    if(c.getObjetCeleste()!=null){
                        if(c.getObjetCeleste().getType()=="etoile"){
                            g.isolerSommet(pos);
                        }
                    }
                }    
            }
        return g;
    }
    
    
    public Graphe getGrapheLicorne(){
        Graphe g = this.getGrapheGrille();
        int n = this.getTaille();
            for (int L=1;L<=n*3;L++){
                for (int C=1;C<=n;C++){
                    int pos=(L-1)*n+C;
                    
                    Case c = this.getCase(L, C);
                    if(c.getObjetCeleste()!=null){
                        if(c.getObjetCeleste().getType()=="etoile"){
                            g.isolerSommet(pos);
                        }
                        if(c.getObjetCeleste().getType()=="asteroide"){
                            g.asteroideSommet(pos);
                        }
                    }
                }    
            }
        return g;
    }
    

        
        
         
        
        
    
    
    
    
    //ajoute un objet celeste (étoile, astéroide...) à la position i,j (Passer par la classe partie !)
    public void addObjetCeleste(ObjetCeleste obj, int i,int j) {
        this.getCase(i, j).addObjetCeleste(obj);
        if(obj != null) {
            obj.setPosition(new Couple(i,j));
        }
    }
    
    //ajoute un vaisseau à la position i,j (Passer par la classe partie !)
    public void addVaisseau(Vaisseau v,int i,int j) {
        this.getCase(i,j).addVaisseau(v);
        if(v !=null) {
            v.setPosition(new Couple(i,j));
        }
    }
    
    //fait bouger le vaisseau présent en case départ à la case arrivée (détruisant tout vaisseau présent à cette case)
    public void BougerVaisseau(Couple depart, Couple arrivee) {
        if(this.getCase(depart).getVaisseau() == null) {
            System.err.println("ERREUR : Aucun vaisseau en case "+depart);
            System.exit(0);
        }
        if(this.getCase(arrivee).getVaisseau() != null) {
            System.out.println("Le "+this.getCase(arrivee).getVaisseau() + " a été détruit !");
            this.getCase(arrivee).getVaisseau().setPosition(null);
        }
        this.getCase(arrivee).addVaisseau(this.getCase(depart).getVaisseau());
        this.getCase(depart).addVaisseau(null);
    }
    
    //méthode gérant ce qu'il se passe quand on clique sur une case en mode manuel
    public void selectionCase(Couple c) {
        if(c.equals(this.caseSelectionnee)) {
            //deselection de la case
            this.effacerColoration();
            this.caseSelectionnee = null;
        }
        else {
            //si une case avait déja été sélectionnée
            if(this.caseSelectionnee != null) {
                //ajouter des conditions de déplacement
                if(this.getCase(c).getCouleur()==Couleur.Vert.getCouleur() || this.getCase(c).getCouleur()==Couleur.Jaune.getCouleur()){
                //on fait bouger le vaisseau
                this.BougerVaisseau(this.caseSelectionnee, c);
                //on déselectionne la case
                this.getCase(this.caseSelectionnee).setCouleur(Couleur.Blanc);
                this.caseSelectionnee = null;
                //on passe le tour
                this.effacerColoration();
                SpaceConquest.tourSuivant();
                }
            }
            else{
                //si aucune case n'avait été selectionné
                //on vérifie que la case nouvellement sélectionné contient un vaisseau du joueur en cours
                if(this.getCase(c).getVaisseau() != null) {
                    if(this.getCase(c).getVaisseau().getRace() == SpaceConquest.getTour()) {
                        //on selectionne la case
                           if(SpaceConquest.getTour()==Race.Licorne){
                                this.colorationMouvements(c, this.getGrapheLicorne());
                                this.caseSelectionnee = c;
                            }else{
                                this.colorationMouvements(c, this.getGrapheZombie());
                                this.caseSelectionnee = c;
                           }
                    }
                                
                }
            }
        }
    }
}
    
