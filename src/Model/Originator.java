/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Val
 */
/**
 * Permet la gestion de CareTaker.
 */
public class Originator {
   private Dimension3 state;

   
   /**
     * On met à jour l'état de notre dimension3.
     * @param state
     * Paramètre de type Dimension 3. Celle à enregistrer.
     */
   public void setState(Dimension3 state){
      this.state = state;
   }

   public Dimension3 getState(){
      return state;
   }
    /**
     * Crée un memento pour l'état enregistré.
     * @return notre objet memento.
     */
   public Object saveToMemento() throws CloneNotSupportedException{
      return new Memento(state);
   }


    /**
     * Récupère l'objet sauvegardé dans le memento. 
     * @param m de type memento
     * @return l'état du memento enregistré
     * @throws java.lang.CloneNotSupportedException
    */
   public Dimension3 restoreFromMemento(Object m) throws CloneNotSupportedException{
        if (m instanceof Memento){
            Memento memento = (Memento)m;
            state = memento.getSavedState();
            //System.out.println("Originator: Etat après restauration: \n" + state);
        }
        return state;
    }
 
private static class Memento{
    private Dimension3 state;
    
    public Memento(Dimension3 stateToSave) throws CloneNotSupportedException{
      //  state = (Dimension3) stateToSave.clone();
    }
    
    /**
         * Renvoie la grille en 3 dimension qui a été save.
         * @return  null, erreur dans le clonage
         * @throws java.lang.CloneNotSupportedException
    */
    public Dimension3 getSavedState() throws CloneNotSupportedException{
        return null;
        //return (Dimension3) state.clone();
    }
   }

}