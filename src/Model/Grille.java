/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


public class Grille implements Parametres, Cloneable, java.io.Serializable{

    private HashSet<Case> grille;
    private int valeurMax = 0;
    private boolean deplacement;
    private int numeroGrille;
    
        private ArrayList<Case> casesDestroy = new ArrayList<>();


    public Grille() {
        this.grille = new HashSet<>();
    }
    public Grille(int numeroGrille) {
        this.grille = new HashSet<>();
        this.numeroGrille=numeroGrille;
    }
 

    /**
     * Permet d'obtenir une copie de la grille.
     * @return l'instance d'une copie de grille
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException { 
        Grille cloned = (Grille) super.clone();
        return cloned;
    }
    
    /**
     * Retourne la liste des cases temporairement effaçées.
     * @return une liste de cases
     */
    public ArrayList<Case> getCasesDestroy() {
        return casesDestroy;
    }

    /**
     * retourne le numéro de la grille
     * @return un entier
     */
    public int getNumeroGrille()
    {
        return numeroGrille;
    }
    
    /**
     * Fixe la grille selon des paramètres données.
     * @param grille une liste de cases
     */
    public void setGrille(HashSet<Case> grille) {
        this.grille = grille;
    }

    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "\n";
        }
        return result;
    }
    
    /**
     * Convertie les coordonnées de la grille en lignes HTML.
     * @return un code en HTML
     */
    public String toHTML() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "<html>";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "<br/>";
        }
        result += "</html>";
        return result;
    }

    /**
     * Retourne le contenu de la grille.
     * @return une liste de cases
     */
    public HashSet<Case> getGrille() {
        return grille;
    }

    /**
     * Retourne la valeur maximale contenue
     * @return un entier
     */
    public int getValeurMax() {
        return valeurMax;
    }

    /**
     * Indique si les conditions de défaites ont été remplie pour cette grille ou non. Retourne vrai si elles sont été remplies ou faux dans le cas contraire.
     * @return Vrai ou Faux
     */
    public boolean partieFinie() {
        if (this.grille.size() < TAILLE * TAILLE) {
            return false;
        } else {
            for (Case c : this.grille) {
                for (int i = 1; i <= 2; i++) {
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

    /**
     * Déplace toutes les cases dans une direction donnée dans la grille.
     * @param direction la direction où on souhaite faire déplacer les cases.
     * @return Vrai si au moins une case s'est déplacée ou Faux dans le cas contraire.
     * @throws CloneNotSupportedException 
     */
    public boolean lanceurDeplacerCases(int direction) throws CloneNotSupportedException {
        Case[] extremites = this.getCasesExtremites(direction);
        deplacement = false; // pour vérifier si on a bougé au moins une case après le déplacement, avant d'en rajouter une nouvelle
        for (int i = 0; i < TAILLE; i++) {
            switch (direction) {
                case HAUT:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                case BAS:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                case GAUCHE:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                default:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
            }
        }
        return deplacement;
    }


    private void fusion(Case c) {
        c.setValeur(c.getValeur() * 2);
        if (this.valeurMax < c.getValeur()) {
            this.valeurMax = c.getValeur();
        }
        deplacement = true;
    }
    
    /**
     * Met à jour la plus haute valeur contenue dans la grille.
     */
    public void setValeurMax()
    {
        int max = 0;
        for(Case c : this.grille)
        {
            if(max < c.getValeur())
                max = c.getValeur();
        }
        this.valeurMax = max;
    }
/*
    private void deplacerCasesRecursif(Case[] extremites, int rangee, int direction, int compteur) throws CloneNotSupportedException {
        if (extremites[rangee] != null) {
            if ((direction == HAUT && extremites[rangee].getY() != compteur)
                    || (direction == BAS && extremites[rangee].getY() != TAILLE - 1 - compteur)
                    || (direction == GAUCHE && extremites[rangee].getX() != compteur)
                    || (direction == DROITE && extremites[rangee].getX() != TAILLE - 1 - compteur)) {
                this.grille.remove(extremites[rangee]);
                switch (direction) {
                    case HAUT:
                        extremites[rangee].setY(compteur);
                        break;
                    case BAS:
                        extremites[rangee].setY(TAILLE - 1 - compteur);
                        break;
                    case GAUCHE:
                        extremites[rangee].setX(compteur);
                        break;
                    case DROITE:
                        extremites[rangee].setX(TAILLE - 1 - compteur);
                        break;
                }
                this.grille.add(extremites[rangee]);
                deplacement = true;
            }
            Case voisin = extremites[rangee].getVoisinDirect(-direction);
            if (voisin != null) {
                if (extremites[rangee].valeurEgale(voisin)) {
                    // ajoute dans le tableau la case a détruire
                    getCasesDestroy().add(0, (Case) voisin.clone());
                    // modifie les coordonnées pour le traitement qui suit
                        // d'où il provient
                    getCasesDestroy().get(0).setLastX(getCasesDestroy().get(0).getX());
                    getCasesDestroy().get(0).setLastY(getCasesDestroy().get(0).getY());
                        // où il était censé arriver
                    getCasesDestroy().get(0).setX(extremites[rangee].getX());
                    getCasesDestroy().get(0).setY(extremites[rangee].getY());
                    
                    this.fusion(extremites[rangee]);
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    // suppression de la case dans la grille
                    this.grille.remove(voisin);
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                }
            }
        }
    } */
    
    private void deplacerCasesRecursif(Case[] extremites, int rangee, int direction, int compteur) throws CloneNotSupportedException {
        if (extremites[rangee] != null) {
            // position avant changement
            extremites[rangee].setLastX(extremites[rangee].getX());
            extremites[rangee].setLastY(extremites[rangee].getY());
            if ((direction == HAUT && extremites[rangee].getY() != compteur)
                    || (direction == BAS && extremites[rangee].getY() != TAILLE - 1 - compteur)
                    || (direction == GAUCHE && extremites[rangee].getX() != compteur)
                    || (direction == DROITE && extremites[rangee].getX() != TAILLE - 1 - compteur)) {
                this.grille.remove(extremites[rangee]);
                switch (direction) {
                    case HAUT:
                        extremites[rangee].setY(compteur);
                        break;
                    case BAS:
                        extremites[rangee].setY(TAILLE - 1 - compteur);
                        break;
                    case GAUCHE:
                        extremites[rangee].setX(compteur);
                        break;
                    default:
                        extremites[rangee].setX(TAILLE - 1 - compteur);
                        break;
                }
                this.grille.add(extremites[rangee]);
                deplacement = true;
            }
            Case voisin = extremites[rangee].getVoisinDirect(-direction);
            if (voisin != null) {
                if (extremites[rangee].valeurEgale(voisin)) {
                    // ajoute dans le tableau la case a détruire
                    getCasesDestroy().add(0, (Case) voisin.clone());
                    // modifie les coordonnées pour le traitement qui suit
                        // d'où il provient
                    getCasesDestroy().get(0).setLastX(getCasesDestroy().get(0).getX());
                    getCasesDestroy().get(0).setLastY(getCasesDestroy().get(0).getY());
                        // où il était censé arriver
                    getCasesDestroy().get(0).setX(extremites[rangee].getX());
                    getCasesDestroy().get(0).setY(extremites[rangee].getY());
                    
                    this.fusion(extremites[rangee]);
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    // suppression de la case dans la grille
                    this.grille.remove(voisin);
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                }
            }
        }
    }
    

    /**
     * Retourne les cases situées les plus à l'extrême d'une direction donnée.
     * @param direction la direction où on souhaite connaître les extrêmités
     * Si direction = HAUT : retourne les 4 cases qui sont le plus en haut (une pour chaque colonne)
     * Si direction = DROITE : retourne les 4 cases qui sont le plus à droite (une pour chaque ligne)
     * Si direction = BAS : retourne les 4 cases qui sont le plus en bas (une pour chaque colonne)
     * Si direction = GAUCHE : retourne les 4 cases qui sont le plus à gauche (une pour chaque ligne)
     * Attention : le tableau retourné peut contenir des null si les lignes/colonnes sont vides
     * @return une liste de cases
     */
    public Case[] getCasesExtremites(int direction) {
        Case[] result = new Case[TAILLE];
        for (Case c : this.grille) {
            c.setLastGrille(c.getNumGrille()); // Mise a jour pour le côté JFX (obligatoire)
            switch (direction) {
                case HAUT:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() > c.getY())) { // si on n'avait pas encore de case pour cette rangée ou si on a trouvé un meilleur candidat
                        result[c.getX()] = c;
                    }
                    break;
                case BAS:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() < c.getY())) {
                        result[c.getX()] = c;
                    }
                    break;
                case GAUCHE:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() > c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
                default:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() < c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
            }
        }
        return result;
    }
    
    // AH : j'ai déplacé les méthodes victory() et gameOver() vers Dimension3

    /**
     * Génère aléatoirement une case dans la grille. Il y a 1 chance sur 2 que sa valeur soit égale à 2 ou 4.
     * @return Vrai si on a pu mettre une nouvelle case ou Faux dans le cas contraire.
     */
    public boolean nouvelleCase() {
        if (this.grille.size() < TAILLE * TAILLE) {
            ArrayList<Case> casesLibres = new ArrayList<>();
            Random ra = new Random();
            int valeur = (1 + ra.nextInt(2)) * 2;
            // on crée toutes les cases encore libres
            for (int x = 0; x < TAILLE; x++) {
                for (int y = 0; y < TAILLE; y++) {
                    Case c = new Case(x, y, valeur,this.getNumeroGrille());
                    if (!this.grille.contains(c)) { // contains utilise la méthode equals dans Case
                        casesLibres.add(c);
                    }
                }
            }
            // on en choisit une au hasard et on l'ajoute à la grille
            Case ajout = casesLibres.get(ra.nextInt(casesLibres.size()));
            ajout.setGrille(this);
            this.grille.add(ajout);
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 4)) { // Mise à jour de la valeur maximale présente dans la grille si c'est la première case ajoutée ou si on ajoute un 4 et que l'ancien max était 2
                this.valeurMax = ajout.getValeur();
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Cherche puis retourne une case d'après des coordonnées données. Retourne null si la case recherchée n'existe pas.
     * @param x abscisse
     * @param y ordonnée
     * @return la case recherchée.
     */
    public Case giveCase(int x, int y)
    {
        for (Case c : this.grille) {
            if( c.getX() == x && c.getY() == y )
            {
                return c;
            }
        }
        return null;
    }
    
}
