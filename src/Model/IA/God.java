/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.IA;
import Model.Dimension3;
import Model.Case;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author aurelien
 */
public class God implements Model.Parametres {
    
    private final int xCoup = 5;    // final 5
    private Dimension3 game;
    
    private HashSet<ArrayList> listeCombinaisons = new HashSet<>();
    private long poidsTotalMin;
    private int[] tabDirections = new int[] {GAUCHE , DROITE , HAUT , BAS , MONTER , DESCENDRE};
    
    // Attention : game est un CLONE (aucun lien avec l'original) !!
    public God(Dimension3 game)
    {
        this.game = game;
    }
    
    @Override
    public String toString() {
        try {
            switch( omniscience() )
            {
                case GAUCHE:    return "Les dieux disent : Gauche (q)";
                case DROITE:    return "Les dieux disent : Droite (d)";
                case HAUT:      return "Les dieux disent : Haut (z)";
                case BAS:       return "Les dieux disent : Bas (s)";
                case MONTER:    return "Les dieux disent : Monter (m)";
                case DESCENDRE: return "Les dieux disent : Descendre (l)";
                default:        return "I'm sorry Dave...";
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(God.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Pas fou lerreur");
        return null;    
    }

    public int omniscience() throws CloneNotSupportedException
    {
        ArrayList<Choix> combinaison = new ArrayList<>();
        makeCombinaison(combinaison, this.game);
        
        Iterator i = this.listeCombinaisons.iterator();
        combinaison = (ArrayList<Choix>)i.next();
        i = combinaison.iterator();
        Choix directionFinal = (Choix) i.next();
        this.listeCombinaisons.clear();

        int result = directionFinal.getDirection();
        return result;
    }

    // va ajouter la combinaison à la liste des combinaisons en prenant soin d'un paramètre :
    // en fonction du poids de la combinaison, il faudra en enlever dans la liste des combinaisons
    // afin de pas surcharger la mémoire ; sinon il faudrait y stocker 20 000 combinaisons.
    private void ajoutListe(ArrayList<Choix> combinaison) 
    {
        // calcule du poids de la combinaison (taille : xCoup)
        long poidsTotal = 0;
        for (Choix i : combinaison) 
        {
            poidsTotal += i.getPoids();
        }
   
        if( this.listeCombinaisons.isEmpty() )
        {
            this.listeCombinaisons.add(combinaison);
            this.poidsTotalMin = poidsTotal;
        }
        else
        {
            if( poidsTotal <= this.poidsTotalMin )
            {
                if( poidsTotal == this.poidsTotalMin )    this.listeCombinaisons.add(combinaison);
                else
                {
                    this.poidsTotalMin = poidsTotal;
                    this.listeCombinaisons.clear();
                    this.listeCombinaisons.add(combinaison);
                }
            }
        }
    }
    
    // on prend le poids du premier jeu...
    // pour chaque direction : on le simule avec un jeu, on en évalue le poids, 
    // et on le soustrait avec le poids du premier jeu, afin d'obtenir la "satisfaction".
    // on la met dans un tableau de int : [ DIRECTION , satifaction ] ; on obtient 1 "coup".
    // Chaque coup simulé, est entreposé dans une liste de coups, nommée la "combinaison".
    // Une fois qu'on atteint X coups (la taille de la combinaison est définie par X),
    // la "combinaison" est mise DIRECTEMENT dans la liste de combinaisons (par valeur),
    // grace à ajoutListe. Tant que la combinaison n'est pas complète, la récursivité continue
    // et ce, pour chaque direction que l'on souhaite analyser
    //      (  d'abord makeTrump() --> résultat (un "coup"), 
    //      puis ledit résultat dans la combinaison, puis récursivité avec le transfert de cette combinaison 
    //      (en paramètre / par référence)  ).
    // combo indique combien de coups il reste, avant que la combinaison soit complétée.
    
    private void makeCombinaison(ArrayList<Choix> combinaison, Dimension3 jeu) throws CloneNotSupportedException
    {
        
        if( combinaison.size() < this.xCoup )
        {
            for( int i = 0 ; i < tabDirections.length ; i++)
            {
                // Dimension3 simulation = this.clone(jeu);
                Choix coup = simulationDirection(jeu,tabDirections[i]);
                // Choix coup = new Choix(tabDirections[i],calculPoids(simulation),simulation);
                
                // le coup doit aboutir à un jeu différent, à une conséquence !
                if( this.calculPoids(jeu) != this.calculPoids(coup.getGame()) )
                {
                    ArrayList<Choix> newCombinaison = (ArrayList<Choix>)combinaison.clone();
                    newCombinaison.add(coup);
                    makeCombinaison( newCombinaison , coup.getGame() );
                }
            }
        }
        else
        {
            ajoutListe(combinaison);
        }
    }
    
    // au lieu de demander d'aller dans une direction, puis analyser le poids du jeu, UNE seule fois (processus)
    // il est intéressant de le faire X fois : chaque mouvement provoque une nouvelle case ;
    // la présence d'un facteur aléatoire force, à répéter le processus X fois.
    // on ne garde que le poids maximal, parmis toutes ces répétitions : c'est le cas pessimiste.
    private Choix simulationDirection(Dimension3 jeu, int direction) throws CloneNotSupportedException
    {
        int repeat = 50;
        long poidsTotalSimulation = 0;
        
        Dimension3 jeuResult = this.clone(jeu);
        if( jeuResult.lanceurDeplacerCases(direction) ) jeuResult.nouvelleCase();
        long poidsMin = calculPoids(jeuResult);

        for( int i = 0 ; i < repeat ; i++)
        {
            Dimension3 simulation = this.clone(jeu);
            if( simulation.lanceurDeplacerCases(direction) ) simulation.nouvelleCase();
            
            long poids = calculPoids(simulation);
            poidsTotalSimulation += poids;
            if(poids < poidsMin) {
                poidsMin = poids;
                jeuResult = simulation;
            }
        }   // on conserve le jeu le plus optimisme
        
        return new Choix(direction,poidsTotalSimulation,jeuResult);
    }
    
    // indique le poids totale du jeu, soit la "satisfaction" d'une situation donnée
    // mettre un clone à partir de CookieMachine !!
    private long calculPoids(Dimension3 jeu)
    {
        // parametres reglables
        // 5    2   3   <--
        // 10   2   5
        int poidsBase = 20, poidsVoisin = 2, poidsVal = 5;
        
        
        long result = 0;
        
        // Pour chaque grille...
        for( int i = 0 ; i < ETAGE ; i++)
        {
            // le poids de base est le nombre de cases remplies par grille
            result += jeu.getMesGrilles()[i].getGrille().size() * poidsBase;
            
            // pour chaque case remplie, par grille, le nombre de ses voisins sera ajouté au poids
            for( Case c : jeu.getMesGrilles()[i].getGrille() )
            {
                // GAUCHE DROITE HAUT BAS
                for( int a = 0 ; a < 4 ; a++)
                {
                    // le fait de ne pas avoir de voisin, n'est pas très satisfaisant
                    if (c.getVoisinDirect(tabDirections[a]) != null) {
                        // un voisin fusionable est assez satisfaisant
                        result += Math.abs(c.getVoisinDirect(tabDirections[a]).getValeur() - c.getValeur());
                        
                        if(Math.abs(c.getVoisinDirect(tabDirections[a]).getValeur() - c.getValeur()) == 0)
                        {
                            result -= c.getValeur() * poidsVal;
                        }
                    } else {
                        result *= poidsVoisin;
                    }
                }
                
                // MONTER DESCENDRE
                for( int a = 4 ; a < 6 ; a++)
                {
                    // le fait de ne pas avoir de voisin, n'est pas très satisfaisant
                    if (getVoisinEtage(a,c,jeu) != null) {
                        // un voisin fusionable est assez satisfaisant
                        result += Math.abs(getVoisinEtage(a,c,jeu).getValeur() - c.getValeur());
                        
                        if(Math.abs(getVoisinEtage(a,c,jeu).getValeur() - c.getValeur()) == 0)
                        {
                            result -= c.getValeur() * poidsVal;
                        }
                    } else {
                        result *= poidsVoisin;
                    }
                    result -= c.getValeur() * poidsVal;
                }
            }
        }   // fin du parcours de toutes les grilles
        
        return result;
    }
    
    private Case getVoisinEtage(int direction, Case c, Dimension3 jeu)
    {
        if (direction == MONTER) 
        {
            for (int i = c.getGrille().getNumeroGrille() - 1 ; i >= 0; i--) 
            {
                if( jeu.getMesGrilles()[i].giveCase(c.getX(), c.getY()) != null )
                {
                    return jeu.getMesGrilles()[i].giveCase(c.getX(), c.getY());
                }
            }
        }
        else
        {
            for (int i = c.getGrille().getNumeroGrille() + 1 ; i < ETAGE; i++) 
            {
                if( jeu.getMesGrilles()[i].giveCase(c.getX(), c.getY()) != null )
                {
                    return jeu.getMesGrilles()[i].giveCase(c.getX(), c.getY());
                }
            }
        }
        return null;
    }
    
    
    private Dimension3 clone(Dimension3 original)
    {
        Dimension3 newResult = new Dimension3();
        
        for( int i = 0 ; i < ETAGE ; i++ )
        {
            for (Case c : original.getMesGrilles()[i].getGrille() ) 
            {
            Case cN = new Case(c.getX(), c.getY(), c.getValeur(), c.getNumGrille());
                newResult.getMesGrilles()[i].getGrille().add(cN);
                cN.setGrille(newResult.getMesGrilles()[i]);
            }
        }
        
        return newResult;
    }
}
