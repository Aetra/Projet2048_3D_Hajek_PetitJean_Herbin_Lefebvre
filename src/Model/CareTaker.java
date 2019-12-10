/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class CareTaker {
   private ArrayList backMemento = new ArrayList();

    private int index = -1;
    
    
    public void setIndex(int ind){
        index=ind;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void addMemento(Object m){
        int l = backMemento.size();
        if (index != l){
//            System.out.println("On efface");
            backMemento.add(index + 1, m);
            for (int k = index + 2; k < l; k++){
                backMemento.remove(k);
            }            
        }
        else{
            backMemento.add(m);
        }
        index++;
    }
    
    public Object getMemento(int i){
        return backMemento.get(i);
    }
}