/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Model.IA.God;
import java.util.Scanner;
import Model.Case;

 
public class Main implements Parametres {

       
   public static void main(String[] args) throws CloneNotSupportedException {
  
       Grille modelGrille1=new Grille(0);
       Grille modelGrille2=new Grille(1);
       Grille modelGrille3=new Grille(3);
       
       Grille[] dim3;
        dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
       Dimension3 g = new Dimension3();
       boolean b = g.nouvelleCase();
       b = g.nouvelleCase();
       System.out.println(g);
       
       Scanner sc = new Scanner(System.in);
                
        System.out.println(g);
        
        while (!g.partieFinie()) {
            System.out.println("Déplacer vers la Droite (d), Gauche (q), Haut (z), ou Bas (s), Monter (m) et Descendre (l)");
            String s = sc.nextLine();
            s.toLowerCase();
            if (!(s.equals("d") || s.equals("droite")
                    || s.equals("q") || s.equals("gauche")
                    || s.equals("z") || s.equals("haut")
                    || s.equals("s") || s.equals("bas")
                    || s.equals("m") || s.equals("monter")
                    || s.equals("l") || s.equals("descendre")
                    || s.equals("IA") || s.equals("GOD")
                    )) {
                System.out.println("Vous devez écrire d pour Droite, q pour Gauche, z pour Haut ou s pour Bas");
                System.out.println("Vous devez écrire m pour Monter ou l pour Descendre");
            } else {
                int direction;

                if(s.equals("IA"))
                {
                    God oranos = new God(g);
                    System.out.println("Vous avez appelé l'aide des dieux:");
                    System.out.println("Ulysse... ");  
                    System.out.println(oranos);
                }
                else{
                if (s.equals("d") || s.equals("droite")) 
                {
                    direction = DROITE;
                } 
                else if (s.equals("q") || s.equals("gauche")) 
                {
                    direction = GAUCHE;
                } 
                else if (s.equals("z") || s.equals("haut")) 
                {
                    direction = HAUT;
                } 
                else if (s.equals("s") || s.equals("bas")) 
                {
                    direction = BAS;
                } 
                else if (s.equals("m") || s.equals("monter")) 
                {
                    direction = MONTER;  
                }
                    else if(s.equals("l") || s.equals("descendre"))
                {
                    direction = DESCENDRE;
                }
                else 
                {
                    direction = 0;
                }
                boolean b2 = g.lanceurDeplacerCases(direction);
                if (b2) {
                    b = g.nouvelleCase();
                    if (!b) g.gameOver();
                }
                System.out.println(g);
                if (g.getValeurMax()>=OBJECTIF) g.victory();
            }
            }
        }
        g.gameOver();
    }
}