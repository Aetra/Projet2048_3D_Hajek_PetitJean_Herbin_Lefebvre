/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.layout.Pane;

/**
 *
 * @author Val
 */
public interface Tuile {
    
    /** On appelle le Thread de mouvement */
    public void threadMovement();
    
    /** On appelle le thread permettant la supression d'une case */
    public void threadMovementCaseDead(Pane fond);
}
