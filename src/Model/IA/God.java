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

     /**
     * Choisie la direction du premier coup venant d'une des combinaisons envisageables. S'il y a plusieurs combinaisons, une seule sera choisie au hasard.
     * @return une direction sous forme d'un entier.
     * @throws CloneNotSupportedException 
     */
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

    /**
     * Enregistre une combinaison dans une liste. Si cette dernière ayant un poids identique à celles déjà enregistrés, elle est enregistrée. Si son poids y est plus petit, elle remplace toutes combinaisons enregistrée. Sinon, elle n'est pas enregistrée.
     * @param combinaison liste de 5 objets de la classe Choix
     */
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
    
    /**
     * Génère toutes les combinaisons sur 5 coups et enregistre leurs résultats. Chaque choix provoquera une simulation de l'instance du jeu en paramètre, puis une récursivitée : cette dernière s'arrête après 5 fois.
     * @param combinaison liste qui va contenir les objets de la classe Choix, résultant des différentes simulations de coups.
     * @param jeu instance d'un jeu, dont celle simulée sera transmise en paramètre, via la récursivité.
     * @throws CloneNotSupportedException 
     */
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
    
   /**
     * Simule un coup d'une direction donnée sur un jeu et calcule le poids du résultat obtenu. Ce processus sera répété 50 fois, afin de prendre en compte les nouvelles cases qui peuvent apparaître aléatoirement.
     * @param jeu instance du jeu, qui sera clonée ensuite par la méthode clone()
     * @param direction direction sous forme d'un entier, laquelle on s'intéresse pour la simulation d'un coup
     * @return un objet de la classe Choix, contenant : l'instance du jeu simulée, dont le poids est le plus faible ; la direction qui a été sujette au test ; le poids total issu de toutes les simulations effectuées ;
     * @throws CloneNotSupportedException 
     */
    private Choix simulationDirection(Dimension3 jeu, int direction) throws CloneNotSupportedException
    {
        int repeat = 50; // Nombre définissant le nombre fois auquel on va simuler le coup
        long poidsTotalSimulation = 0; // Poids total, issus des poids de tous les jeux qui auront été simulés.
        
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
    /**
     * Calcule le poids d'un instance du jeu. Plus ce poids est grand, moins cette instance présente d'options pour atteindre les conditions de victoire.
     * @param jeu instance du jeu
     * @return un entier, qui qualifie l'instance du jeu envoyée en paramètre.
     */
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
    
    /**
     * Donne la case voisine à une case envoyée en paramètre, par rapport à une direction donnée. Retourne Null si cette dernière n'en a pas.
     * @param direction direction par laquelle on souhaite chercher
     * @param c case auquel on souhaite connaître sa case voisine directe
     * @param jeu instance du jeu actuelle
     * @return instance de la case, voisine directement à celle envoyée en paramètre. Retourne Null en cas d'abence de voisin direct.
     */
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
    
     /**
     * Crée une copie indépendante du jeu envoyé par paramètre.
     * @param original instance du jeu actuel
     * @return une nouvelle instance du jeu en paramètre, indépendante de cette dernière.
     */
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
