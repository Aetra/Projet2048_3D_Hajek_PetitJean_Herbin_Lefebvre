package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;

/**
 *
 * @author aurelien
 */
public class Dimension3 implements Parametres {
    
    // AH : pensez à essayer de faire un héritage avec Grille
    // Cela pourrait rendre le code un peu plus propre...
    
    public Grille[] mesGrilles = new Grille[ETAGE];
    
    /*
    public Dimension3(Grille[] dim3)
    {
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            // 0: Haut
            // 1: Moyen
            // 2: Bas
            this.mesGrilles[i] = new Grille(i);
        }
    }
    */
    
    // Surcharge
    public Dimension3()
    {
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            // 0: Haut
            // 1: Moyen
            // 2: Bas
            this.mesGrilles[i] = new Grille(i);
        }
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
    
    
    // permet d'ordonner toutes les grilles, de déplacer leurs blocs dans une direction donnée
    public boolean lanceurDeplacerCases(int direction)
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
                lim++;
            else
                lim--;
        }
        
        return lim;

    }
            
    
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
    
}
