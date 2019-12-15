/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;


public enum Dimension3 implements Parametres, java.io.Serializable {

    INSTANCE;
    
    private Grille[] mesGrilles = new Grille[ETAGE];
    //private Grille[] mesGrilles;

    private Dimension3()
    {
          for( int i = 0 ; i < ETAGE ; i++ )
        {
            // 0: Grille du Haut
            // 1: Grille du Milieu
            // 2: Grille du Bas
           this.mesGrilles[i] = new Grille(i);
        }

    }
    
    /**
     * Retourne l'instance du jeu actuel.
     * @return l'instance du jeu
     */
    public static Dimension3 getInstance() {
        return INSTANCE;
    }
     
    @Override
    public String toString() {
        
        String result = "";
        
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            result += this.mesGrilles[i].toString() + "\n";
        }
        return result;
    }
    
    /**
     * Fait déplacer toutes les cases des grilles du jeu vers une direction donnée.
     * @param direction la direction où on souhaite faire déplacer les cases.
     * @return Vrai s'il y a eu au moins déplacement ou Faux dans le cas contraire.
     * @throws CloneNotSupportedException 
     */
    public boolean lanceurDeplacerCases(int direction) throws CloneNotSupportedException
    {
        boolean result = false;
        // Si la direction choisie est valide...
        // Sinon rien ne se passe !
        if(direction == DROITE || direction == GAUCHE || direction == BAS || direction == HAUT)
        {
            for( int i = 0 ; i < ETAGE ; i++ )
            {
                if(this.mesGrilles[i].lanceurDeplacerCases(direction))
                    result = true;
            }
        }
        else if( direction == MONTER || direction == DESCENDRE )
        {
            if( direction == MONTER )
            {
                result = fusionR();
            }
            else
            {
                result = fusionL();
            }
        } // Ici, si direction égale MONTER / DESCENDRE, appeler monter() ou descendre()
        
        return result;
    }
  
    /**
     * Indique si la partie est terminée.
     * @return Vrai si les conditions de défaites ont été remplies ou Faux dans le cas contraire.
     */
    public boolean partieFinie() {
        
        int over = 0;
        
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            if(this.mesGrilles[i].partieFinie())
                over++;
        }
        
        return ( over == ETAGE );
    }
    
    /**
     * Génère aléatoirement une case parmis une des grilles du jeu. Il y a 1 chance sur 2 que sa valeur soit égale à 2 ou 4.
     * @return Vrai si on a pu mettre une nouvelle case ou Faux dans le cas contraire.
     */
    public boolean nouvelleCase() 
    {
        ArrayList<Grille> mesGrillesLibres = new ArrayList<>();
        Random ra = new Random();
        int lgt = 0;
        
        
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            if( !this.mesGrilles[i].partieFinie() )
            {
                mesGrillesLibres.add(this.mesGrilles[i]);
            }
            else
            {
                lgt++;
            }
        }
        
        if( mesGrillesLibres.isEmpty() )
        {
            return false;
        }
        else
        {
            return( mesGrillesLibres.get(ra.nextInt(ETAGE-lgt)).nouvelleCase() );
        }  
    }
    
    private void setValeurMax()
    {
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            this.mesGrilles[i].setValeurMax();
        }
    }
    
    /**
     * Retourne la valeur la plus haute actuellement dans le jeu.
     * @return un entier
     */
    public int getValeurMax() {
        int max[] = new int[ETAGE];
        int maxV = 0;
        
        for( int i = 0 ; i < ETAGE ; i++)
        {
            max[i] = this.mesGrilles[i].getValeurMax();
            if( max[i] > maxV ) 
                maxV = max[i];
        }
        return maxV;
    }
    /*
    public boolean partieFinie() {
        if (getValeurMax() == OBJECTIF){
            return true;
        }
        else if (this.grille.size() < TAILLE * TAILLE * TAILLE) {
            return false;
        } else {
            for (Case c : this.grille) {
                for (int i = 1; i <= 3; i++) {
                    if (c.getVoisinDirect(i) != null) {
                        if (c.valeurEgale(c.getVoisinDirect(i))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    */
    
    /**
     * Affiche que les conditions de victoire ont été remplies puis ferme le jeu.
     */
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.getValeurMax());
        System.exit(0);
    }

    /**
     * Affiche que les conditions de défaite ont été remplies puis ferme le jeu.
     */
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.getValeurMax());
        System.exit(1);
    }
    
    // Si MONTER: renvoie la case la plus à droite
    // Si DESCENDRE: renvoie la case la plus à gauche
    private Case extremeTab(int direction, Case[] ascenseur)
    {
        switch (direction)
        {
            case DESCENDRE: // le début est la dernière case du tableau
                for(int z = 0 ; z < ETAGE ; z++)
                {
                    if( ascenseur[z] != null )
                    {
                        return ascenseur[z];
                    }
                }
            break;
                
            case MONTER: // le début est la première case du tableau
                for(int z = ETAGE - 1 ; z >= 0 ; z--)
                {
                    if( ascenseur[z] != null )
                    {
                        return ascenseur[z];
                    }
                }
            break;
            
            default:
                break;
        }
        return null;
    }
    
    /**
     * Initialise les grilles du jeu selon les paramètres données.
     * @param dim3 un tableau de grilles
     */
    public void initStart(Grille[] dim3) {
        System.out.println(dim3[0]);
        this.mesGrilles[0] = dim3[0];
        this.mesGrilles[1] = dim3[1];
        this.mesGrilles[2] = dim3[2];
    }
    
    /**
     * Transforme une grille en un tableau de cases.
     * @param g une grille du jeu
     * @return un tableau de cases.
     */
    public Case[][] convertHash(Grille g) {
        HashSet<Case> set = g.getGrille();
        Case[][] convert = new Case[3][3];
        for (Case c : set) {
            convert[c.getX()][c.getY()] = c;
        }
        return convert;
    }
    
    /**
     * Fusionne les cases de la grille de haut en bas.
     * @return Vrai si toutes les cases ont été fusionnées ou Faux dans le cas contraire.
     * @throws CloneNotSupportedException 
     */
    public boolean fusionL() throws CloneNotSupportedException {
        boolean b1 = this.teleportationSameCase(this.getMesGrilles()[0], this.getMesGrilles()[1], -1);
        boolean b2 = this.teleportationSameCase(this.getMesGrilles()[1], this.getMesGrilles()[2], -1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMesGrilles()[0], this.getMesGrilles()[1], -1);
        boolean b4 = this.teleportationEmptyCase(this.getMesGrilles()[1], this.getMesGrilles()[2], -1);
        return b1 || b2 || b3 || b4;
    }
    
    /**
     * Fusionne les cases de la grille de bas en haut.
     * @return Vrai si toutes les cases ont été fusionnées ou Faux dans le cas contraire.
     * @throws CloneNotSupportedException 
     */
    public boolean fusionR() throws CloneNotSupportedException {
        boolean b1 = this.teleportationSameCase(this.getMesGrilles()[2], this.getMesGrilles()[1], 1);
        boolean b2 = this.teleportationSameCase(this.getMesGrilles()[1], this.getMesGrilles()[0], 1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMesGrilles()[2], this.getMesGrilles()[1], 1);
        boolean b4 = this.teleportationEmptyCase(this.getMesGrilles()[1], this.getMesGrilles()[0], 1);
        
        return b1 || b2 || b3 || b4;
    }
    
    /**
     * Retourne les grilles du jeu.
     * @return un tableau de grille
     */
    public Grille[] getMesGrilles()
    {
        return this.mesGrilles;
    }
    
    /**
     * Fixe les grilles du jeu en fonction des paramètres.
     * @param multiGrille un tableau de grilles
     */
    public void setMesGrilles(Grille[] multiGrille) {
        this.mesGrilles = mesGrilles;
    }
    
    /**
     * Déplace toutes les cases d'une grille à une autre. Leurs destinations sont des cases vides.
     * @param left la grille de départ
     * @param right la grille de destination
     * @param c la direction que prennent les cases
     * @return Vrai s'il y a eu au moins un déplacement ou Faux dans le cas contraire
     * @throws CloneNotSupportedException 
     */
    public boolean teleportationEmptyCase(Grille left, Grille right, int c) throws CloneNotSupportedException { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] == null) {                         
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + c);
                        fusion.setGrille(left);
                        fusion.setLastX(fusion.getX());
                        fusion.setLastY(fusion.getY());
                        // ne pas mettre lastGrille : sinon le mouvement se fera avec une grille en moins !
                        left.getGrille().add(fusion); // ajout de la nouvelle case
                                              
                        right.getGrille().remove(r[x][y]);
                        b = true;
                    } 
                }
            }
        }
        return b;
    }

    /**
     * Déplace toutes les cases d'une grille à une autre. Leurs destinations sont des cases remplies.
     * @param left la grille de départ
     * @param right la grille de destination
     * @param c la direction que prennent les cases
     * @return Vrai s'il y a eu au moins un déplacement ou Faux dans le cas contraire
     * @throws CloneNotSupportedException 
     */
    public boolean teleportationSameCase(Grille left, Grille right, int c) throws CloneNotSupportedException { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si la case dans la grille droite existe alors
                    if (l[x][y] != null) { // si la case dans la grille droite existe alors
                        if (l[x][y].valeurEgale(r[x][y])) { // si ces cases ont la mêmes valeurs alors je fusionne
                            Case fusion = (Case) l[x][y].clone();
                            fusion.setValeur(fusion.getValeur()*2); // Fusion
                            fusion.setGrille(left);
                            fusion.setLastGrille(r[x][y].getNumGrille()); // NEW
                            fusion.setLastX(fusion.getX());
                            fusion.setLastY(fusion.getY());

                            right.getCasesDestroy().add(r[x][y]);
                            // supprime les anciennes cases qui vont être mis-à-jour
                            left.getGrille().remove(l[x][y]);
                            right.getGrille().remove(r[x][y]);
                            // ajoute la nouvelle case
                            left.getGrille().add(fusion);
                            b = true;
                        } else { // MAJ JFX
                            l[x][y].setLastX(l[x][y].getX());
                            l[x][y].setLastY(l[x][y].getY());                            
                        }
                    } else { // la position est disponible donc la case est téléportée
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + c);
                        fusion.setLastGrille(r[x][y].getNumGrille());
                        fusion.setGrille(left);
                        fusion.setLastX(fusion.getX());
                        fusion.setLastY(fusion.getY());
                        
                        right.getGrille().remove(r[x][y]);
                        left.getGrille().add(fusion);
                        b = true;
                    }
                }
            }
        }
        return b;
    }
 }    

