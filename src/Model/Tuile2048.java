/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author Val
 */
public class Tuile2048 implements Tuile, Parametres{
    private Grille grille;
    
    public Tuile2048 (Grille grille) throws CloneNotSupportedException {
        this.grille = (Grille) grille.clone();
    }

    /** Fonction Thread déclanchant l'effet de mouvement
     * On définit une tache paralléle pour l'execution de multi thread
     * on prend en compte l'avant et l'après mouvement
     * On utiise Platform.runLater pour pouvoir modifié le thread courant
     * On modifie la position de la tuile en prenant en compte l'abscisse et l'ordonnée
     * On met un Thread.sleep pour déterminer la durée du thread, il ne peut être inférieur à 1
     * Le thread se délenche en prenant compte des paramètre précédant
     */
    @Override
    public void threadMovement() {
        for (Case c  : grille.getGrille()) {
            final int numGrille = grille.getNumeroGrille();
            Task task = new Task<Void>() { 
                @Override
                public Void call() throws Exception { 
                    int objectifx = 25 + 14 * numGrille + numGrille * 334 + (c.getX() * tailleX);
                    int objectify = 295 + tailleY * c.getY();
                    int x = 25 + 14 * c.getLastGrille() + c.getLastGrille() * 334 + (c.getLastX() * tailleX);
                    int y = 295 + tailleY * c.getLastY();
                    while (x != objectifx || y != objectify) { 
                        if (x < objectifx) {
                            x += 1; 
                        } else if (x > objectifx) {
                            x -= 1; 
                        }
                        if (y < objectify) {
                            y += 1; 
                        } else if (y > objectify) {
                            y -= 1; 
                        }
                        final int varX = x;
                        final int varY = y;
                        Platform.runLater(new Runnable() { // classe anonyme
                                @Override
                                public void run() {
                                    c.getPane().relocate(varX, varY);
                                    c.getPane().setVisible(true); 
                                }
                            }
                        );
                        Thread.sleep((long) 1);
                    } 
                    return null; 
                } 

            };
            Thread th = new Thread(task); 
            th.setDaemon(true); 
            th.start(); 
        }
    }    
    
    /** Fonction thread permettant la destruction de la case qui a été fusionné auparavant
     * @param fond  On prend le fond background pour définir où on veut détruire
     * On récupère les coordonnées de la case et sur quel grille elle est
     * On on la détruit visuellement et également dans le model
     */
    @Override
    public void threadMovementCaseDead(Pane fond) {  
        for (Case c  : grille.getCasesDestroy()) {
            final int fi = grille.getNumeroGrille();
            fond.getChildren().remove((Node) c.getPane());
        }
        this.grille.getCasesDestroy().clear();
    }     
}