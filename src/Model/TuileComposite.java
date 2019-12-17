/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
 *
 * @author Val
 */
public class TuileComposite implements Tuile{
    private ArrayList<Tuile> tuileComposite = new ArrayList<>();

    /** On appel la fonction threadMovement
     * On utilise Composite pour définir le comportement de l'élément ciblé de la liste*/
    @Override
    public void threadMovement() {
        for(Tuile t : tuileComposite) { 
            t.threadMovement();
        }
    }
    
    /** On appel et la fonction threadMovementCaseDead 
     * On utilise Composite pour définir le comportement de l'élément ciblé de la liste*/
    @Override
    public void threadMovementCaseDead(Pane fond) {
        for(Tuile t : tuileComposite) { 
            t.threadMovementCaseDead(fond); 
        }
    }
    
    /** Fonction permettant l'ajout de la tuile */
    public void add(Tuile t) { 
        tuileComposite.add(t); 
    } 
    
    /** Fonction permettant de détruire la tuile */
    public void remove(Tuile t) { 
        tuileComposite.remove(t); 
    } 
    
}
