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
public class Memento {
   private Dimension3 state;

   public Memento(Dimension3 state){
      this.state = state;
   }

   public Dimension3 getState(){
      return state;
   }	
}