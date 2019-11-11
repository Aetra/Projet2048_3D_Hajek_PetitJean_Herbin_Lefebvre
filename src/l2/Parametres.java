/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package l2;

/**
 *
 * @author Sylvain
 */
public interface Parametres {
    static final int HAUT = 1;
    static final int DROITE = 2;
    static final int BAS = -1;
    static final int GAUCHE = -2;
    static final int TAILLE = 4;
    static final int OBJECTIF = 2048;
    
    // AH: cette variable indique le nombre de grilles qui composera le 2848-3D
    static final int ETAGE = 3; 
    static final int MONTER = 5;
    static final int DESCENDRE = 6;
}
