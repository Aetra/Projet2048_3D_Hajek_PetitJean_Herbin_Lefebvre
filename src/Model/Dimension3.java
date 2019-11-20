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

/**
 *
 * @author aurelien
 */
public enum Dimension3 implements Parametres {

    INSTANCE;
    
    // AH : pensez à essayer de faire un héritage avec Grille
    // Cela pourrait rendre le code un peu plus propre...
    
   // private Grille[] mesGrilles = new Grille[ETAGE];
        private Grille[] mesGrilles;

    private Dimension3()
    {
        mesGrilles = new Grille[3];

        /*
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            // 0: Haut
            // 1: Moyen
            // 2: Bas

//           this.mesGrilles[i] = new Grille(i);
        }
        */
    }
    
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
    
    /*
    // permet d'ordonner toutes les grilles, de déplacer leurs blocs dans une direction donnée
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
                result = teleportation(MONTER);
            }
            else
            {
                result = teleportation(DESCENDRE);
            }
        } // Ici, si direction égale MONTER / DESCENDRE, appeler monter() ou descendre()
        
        return result;
    }
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
       
    }   // FIN de nouvelleCase()
    
    private void setValeurMax()
    {
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            this.mesGrilles[i].setValeurMax();
        }
    }
    
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
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.getValeurMax());
        System.exit(0);
    }

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
    
    private int actionRecursif(int direction, Case[] ascenseur, Case c1, boolean fusion, int lim)
    {
        Case c2 = extremeTab(direction,ascenseur);
        
        if( c2 != null )    // tant qu'il existe une case, on applique la récursivité !
        {
            if( c1 != null ) // si cette case peut être fusionnée par son précédant (si ce dernier existe)...
            {
                fusion = !c1.valeurEgale(c2);
                if(!fusion)
                {
                    c2.setValeur(c2.getValeur()*2); // fusion !
                }
            }
            
            
            // suppression de cette case dans le tableau
            ascenseur[c2.getGrille().getNumeroGrille()] = null;
            // suppression de cette case de sa propre grille
            this.mesGrilles[c2.getGrille().getNumeroGrille()].getGrille().remove(c2);

            lim = actionRecursif(direction,ascenseur,c2,fusion,lim);    
            // le contenu de c2 ne s'efface pas grâce à la présence de c2, transmise à travers la récursivité
        }
        
        
        // System.out.println("Lim: " + lim);
        // System.out.println("Case c1: " + c1);
        // System.out.println("Case c2: " + c2);
        if( (fusion && c1 != null) || (c2 == null && c1 != null) )
        {
            // System.out.println("Case c1: " + c1);
            ascenseur[lim] = c1;
            this.mesGrilles[lim].getGrille().add(c1);
            c1.setGrille(this.mesGrilles[lim]);
            
            
            if(direction == MONTER)
            {
                lim++;
            }
            else
            {
                lim--;
            }
        }
        
        return lim;

    }
            
    /*
    public boolean teleportation(int direction)
    {
        boolean deplacement = false;    // permet de dire si une case a bougé
        
            for (int y = 0; y < TAILLE; y++) // pour chaque ligne...
            {
                for(int i = 0; i < TAILLE; i++) // pour chaque colonne...
                {
                    Case[] ascenseur = new Case[ETAGE];
                    for (int z = 0; z < ETAGE; z++)
                    {
                        ascenseur[z] = this.mesGrilles[z].giveCase(i, y);
                        
                        // s'il existe au moins une case vide, alors un déplacement est forcément possible (et se fera donc)
                        if(ascenseur[z] == null)
                        {
                            deplacement = true;
                        }
                    }
                    // s'il s'agit de descendre alors il faut se diriger vers la gauche de l'ascenseur
                    // ATTENTION : une case "avec une valeur de 0" est null !
                    
                    /*
                    System.out.println("Tableau :");
                    for(int a = 0 ; a < ETAGE ; a++)
                    {
                        System.out.println(ascenseur[a]);
                    }
                    */
                  /*  
                    switch (direction)
                    {
                        case MONTER:
                            actionRecursif(MONTER, ascenseur, null, true, 0);
                            break;
                        case DESCENDRE:
                            actionRecursif(DESCENDRE, ascenseur, null, true, ETAGE-1);
                            break;
                        default:
                            break;
                    }
                    
                }
            }
        this.setValeurMax();
        return deplacement;
    }
*/
    

    public void init(Grille[] dim3) {
        System.out.println(dim3[0]);
        this.mesGrilles[0] = dim3[0];
        this.mesGrilles[1] = dim3[1];
        this.mesGrilles[2] = dim3[2];
    }
    
    public Case[][] convertHash(Grille g) {
        HashSet<Case> set = g.getGrille();
        Case[][] convert = new Case[3][3];
        for (Case c : set) {
            convert[c.getX()][c.getY()] = c;
        }
        return convert;
    }

  public boolean fusionGauche() throws CloneNotSupportedException {
        boolean b1 = this.teleportationSameCase(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
        boolean b2 = this.teleportationSameCase(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
        boolean b4 = this.teleportationEmptyCase(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);
        
        return b1 || b2 || b3 || b4;
    }
    
    public boolean fusionDroite() throws CloneNotSupportedException {
        boolean b1 = this.teleportationSameCase(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
        boolean b2 = this.teleportationSameCase(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
        boolean b4 = this.teleportationEmptyCase(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);
        
        return b1 || b2 || b3 || b4;
       }
    
        public boolean teleportationEmptyCase(Grille left, Grille right, int compteur) throws CloneNotSupportedException { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] == null) {                         
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + compteur);
                        fusion.setGrille(left);
                        fusion.setLastX(fusion.getX());
                        fusion.setLastY(fusion.getY());
                        // ne peas mettre lastGrille : sinon le mouvement se fera avec une grille en moins !
                        left.getGrille().add(fusion); // ajout de la nouvelle case
                                              
                        right.getGrille().remove(r[x][y]);
                        b = true;
                    } 
                }
            }
        }
        return b;
    }
        public Grille[] getMultiGrille() {
        return mesGrilles;
    }
    public boolean teleportationSameCase(Grille left, Grille right, int compteur) throws CloneNotSupportedException { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] != null) { // si ma case dans la grille droite existe alors
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
                    } else { // la position est disponible donc je tp la case
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + compteur);
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

