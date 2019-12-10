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
public class Originator {
   private Dimension3 state;

   public void setState(Dimension3 state){
      this.state = state;
   }

   public Dimension3 getState(){
      return state;
   }

   public Object saveToMemento() throws CloneNotSupportedException{
      return new Memento(state);
   }

   public void getStateFromMemento(Memento memento) throws CloneNotSupportedException{
      state = memento.getSavedState();
   }
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
    
    public Dimension3 getSavedState() throws CloneNotSupportedException{
        return null;
        //return (Dimension3) state.clone();
    }
   }

}