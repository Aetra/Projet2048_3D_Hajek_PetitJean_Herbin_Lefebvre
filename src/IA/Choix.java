/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import Model.Dimension3;

/**
 *
 * @author aurelien
 */

public class Choix {
    private int direction;
    private long poids;
    private Dimension3 game;
    
    public Choix(int direction,long poids,Dimension3 game)
    {
        this.direction = direction;
        this.poids = poids;
        this.game = game;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @return the poids
     */
    public long getPoids() {
        return poids;
    }

    /**
     * @return the game
     */
    public Dimension3 getGame() {
        return game;
    }
}
