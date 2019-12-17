/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;

/**
 *
 * @author Sylvain
 */
public class Case implements Parametres, Cloneable, java.io.Serializable {

    private int x, y, valeur, numeroGrille, lastX, lastY, lastGrille;;
    private Grille grille;
    @FXML
    private transient Pane pane;
    @FXML
    private transient Label label;
     public Case(int abs, int ord, int v, int numeroGrille) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
        this.numeroGrille = numeroGrille;
        // Côté JFX : valeur par défaut à l'initialisation
        this.lastX = abs;
        this.lastY = ord;
        this.lastGrille = numeroGrille;
    }
    /**
     * Associe la case à une grille du jeu.
     * @param g l'instance d'une grille 
     */
     public void setGrille(Grille g) {
        this.grille = g;
    }
    /**
     * Permet d'obtenir une copie de la case.
     * @return l'instance d'une copie de case
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException { 
        Case caseClone = (Case) super.clone(); 
        return caseClone;
    }
        
    /**
     * Retourne l'instance de la grille associé à la case.
     * @return l'instance de la grille associé à la case
     */
    public Grille getGrille() {
        return this.grille;
    }
    /**
     * Retourne l'instance du label.
     * @return l'instance du label associé à l'ig
     */
      public Label getLabel() {
        return label;
    }

    /**
     * @param l the l to set the label
     */
    public void setLabel(Label l) {
        this.label = l;
    }
     /**
     * Retourne l'instance de la derniere grille à l'intant t-1.
     * @return l'instance de la grille à l'instant t-1
     */
     public int getLastGrille() {
        return lastGrille;
    }
     /**
     * définit l'instance de la derniere grille à l'intant t-1.
     */ 
    public void setLastGrille(int lastGrille) {
        this.lastGrille = lastGrille;
    }

     /**
     * Retourne l'instance du Pane l'intant t-1.
     * @return l'instance de la grille à l'instant t-1
     */
     public Pane getPane() {
        return pane;
    }

    /** Fonction redefinissant le Pane
     * 
     * @param p 
     */
    public void setPane(Pane p) {
        this.pane = p;
    }
    
     /**
     * Retourne la valeur de x a l'intant t-1.
     * @return la valeur de x a l'instant t-1
     */
    public int getLastX() {
        return lastX;
    }

    /**
     * @param lastX the lastX to set
     */
    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    /**
     * @return the lastY
     */
    public int getLastY() {
        return lastY;
    }

    /**
     * @param lastY the lastY to set
     */
    public void setLastY(int lastY) {
        this.lastY = lastY;
    }
/**
     * Retourne le numéro de la grille associée à la case.
     * @return le numéro d'une grille
     */
    public int getNumGrille() {
        return numeroGrille;
    }

    /**
     * Associe un numéro de grille associée à la case.
     * @param numeroGrille le numéro d'une grille
     */
    public void setNumGrille(int numeroGrille) {
        this.numeroGrille = numeroGrille;
    }
    
    /**
     * Retourne l'abscisse où se trouve la case.
     * @return la coordonnée x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne l'ordonné où se trouve la case.
     * @return la coordonnée y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Fixe une abscisse à la case.
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Fixe une ordonnée à la case.
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Fixe une valeur à la case.
     * @param valeur
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur de la case.
     * @return valeur de la case
     */
    public int getValeur() {
        return this.valeur;
    }

    /**
     * Indique si deux cases sont identiques. Retourne Vrai si elles sont identiques. Faux dans le cas contraire.
     * @param obj l'instance d'une case à comparer
     * @return Vrai ou Faux
     */
    @Override
    public boolean equals(Object obj) { // la méthode equals est utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublons (teste parmi tous les candidats qui ont le même hashcode)
        if (obj instanceof Case) {
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }
    
    /**
     * Retourne le hashcode de la case. Le hashcode est un numéro d'identité unique pour chaque objet.
     * @return hashCode de la case
     */
    @Override
    public int hashCode() { // détermine le hashcode
        return this.x * 7 + this.y * 13;
    }

    /**
     * Indique si deux cases ont la même valeur. Retourne Vrai si elles sont identiques. Faux dans le cas contraire.
     * @param c l'instance d'une case à comparer
     * @return Vrai ou Faux
     */
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    /**
     * Cherche et retourne une case voisine directe. Renvoie un objet null s'il n'y en a pas.
     * @param direction une direction où on souhaite y chercher un voisin direct
     * @return la case voisine directe
     */
    public Case getVoisinDirect(int direction) {
        if (direction == HAUT) {
            for (int i = this.y - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == BAS) {
            for (int i = this.y + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == GAUCHE) {
            for (int i = this.x - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        } else if (direction == DROITE) {
            for (int i = this.x + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    /*
    @Override
    public String toString() {
        return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
    }*/
    @Override
    public String toString() {
        String s = "Case(X " + this.x + ",Y " + this.y + ",Val " + this.valeur + ", num Grille " + this.numeroGrille + ")";
        s = s + "last x " + this.getLastX() + ", last y " + this.lastY + ", last grille " + this.lastGrille + ".";
        return s;
    }

}
