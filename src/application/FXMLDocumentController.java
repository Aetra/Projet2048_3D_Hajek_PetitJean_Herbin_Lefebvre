/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.CareTaker;
import Model.Case;
import Model.Dimension3;
import Model.Grille;
import Model.Originator;
import Model.Parametres;
import static Model.Parametres.tailleX;
import static Model.Parametres.tailleY;
import Model.Tuile2048;
import Model.TuileComposite;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem theme;
    @FXML
    private Button bTop;
   private Button bBot, bLeft, bRight, bTpg, bTpd;
    @FXML
    private Pane fond;
    @FXML
    private GridPane grille2;
    @FXML
    private GridPane grille1;
    @FXML
    private Label mvtScoreLabel;
<<<<<<< HEAD
=======
    @FXML
    private Label scoreToLabel;
    int direction;

>>>>>>> 372f7806002d291ab4efd9b2ead1081d7d9f2e22
    
    private Grille modelGrille1;
    private Grille modelGrille2;
    private Grille modelGrille3;
    
    // partie en 3 dimension
    private Grille[] dim3;
    private Dimension3 mesGrilles;

    //private Dimension3 grilleDim3 = new Dimension3(dim3);
    
    
    // Pour le bouton revenir en arrière
    private Originator originator;
    private CareTaker careTaker;


    private int x = 25, y = 295;
    private int objectifx = 25, objectify = 295;
    @FXML
    private GridPane grille;
    @FXML
    private Label scoreTotLabel;
    /**
     * Initializes the controller class.
     */
     // variables globales non définies dans la vue (fichier .fxml)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // TODO
        System.out.println("le contrôleur initialise la vue");
        fond.getStyleClass().add("fond");   
        // Initialisation de ma multi-grille
        modelGrille1 = new Grille(0);
        modelGrille2 = new Grille(1);
        modelGrille3 = new Grille(2);        
        dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
        mesGrilles = Dimension3.INSTANCE;
                
        // Pour le bouton revenir en arrière
        originator = new Originator();
        careTaker = new CareTaker();
         mesGrilles.init(dim3);
         /*
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            final FileInputStream fichierln = new FileInputStream("model.ser");
            ois = new ObjectInputStream(fichierln);
            mesGrilles = (Dimension3)ois.readObject();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally{
            try{
                if(ois != null){
                    ois.close();
                }
            }catch (final IOException exc){
                exc.printStackTrace();
            }
        }
        */

    }  

    private void afficherTuile() { // A chaque nouvelle case cela créé la tuile (de façon dynamique)
        System.out.println("AFFICHAGE DES TUILES");
        for (int k = 0; k < 3; k++) {
            for (Case c : this.dim3[k].getGrille()) {
                if (c.getPane() == null) { // la tuile vient d'être créé
                    switch(c.getValeur()) {
                        case 2:
                            c.setPane(new Pane());
                            c.setLabel(new Label());
                            c.getPane().getStyleClass().add("pane2");
                            c.getLabel().getStyleClass().add("tuile");
                            break;
                        case 4:
                            c.setPane(new Pane());
                            c.setLabel(new Label());
                            c.getPane().getStyleClass().add("pane4");
                            c.getLabel().getStyleClass().add("tuile");
                            break;
                    }
                    c.getLabel().setText(Integer.toString(c.getValeur()));
                }
            }
        }
    }

     private void positionTuile() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.dim3[i].getGrille()) {
                if (!fond.getChildren().contains(c.getPane())) { // la case vient d'être créé
                    // Position du pane sur le fond
                    c.getPane().setLayoutX(25 + 14 * i + i * 334 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                    c.getPane().setLayoutY(295 + (c.getY() * tailleY));
                    // Ajout du label dans le pane
                    c.getPane().getChildren().add(c.getLabel());
                    // Pane et label rendu visible
                    c.getPane().setVisible(true);
                    c.getLabel().setVisible(true);
                    // Ajout du pane sur le fond
                    fond.getChildren().add(c.getPane());
                }
            }
        }
    }
    
    
    
/**iICIIIIIIIIII */
    @FXML
    private void handleDragAction(MouseEvent event) {
       
     }

    @FXML
    private void handleButtonAction(MouseEvent event) {	
	System.out.println("Clic de souris sur le bouton start");
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            this.dim3[random].nouvelleCase();
        }
        this.afficherTuile();
        this.positionTuile();
        System.out.println(mesGrilles);
        
    }
    
    @FXML
    private void keyPressed(KeyEvent ke) throws CloneNotSupportedException {
        TuileComposite t = new TuileComposite();

        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
         //   scoreToLabel.setText(String.valueOf(mesGrilles.getValeurMax())); // mise à jour du compteur de mouvement

         System.out.println(mesGrilles.getValeurMax());
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.GAUCHE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                  // Méthode composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
            
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
               // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
        } else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.HAUT);
            if (b1 || b2 || b3) {
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
               this.nouvelleCase();
            }
            
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));  
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.BAS);
            if (b1 || b2 || b3) {
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
           }
           else if (touche.compareTo("l") == 0) { // FUSION GAUCHE
            //boolean fusionSuccess = mesGrilles.teleportation(DESCENDRE);
            //boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(Parametres.DESCENDRE);  
            boolean fusionSuccess = mesGrilles.fusionGauche();  

            System.out.println(fusionSuccess);
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();            
            }
        } else if (touche.compareTo("m") == 0) { // FUSION DROITE
            //boolean fusionSuccess = mesGrilles.teleportation(Parametres.MONTER);
            boolean fusionSuccess = mesGrilles.fusionDroite();                     

            //boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(direction);                     
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    
            }
        }
        this.updateTemplate(); // Pour la valeur du label (pour l'instant)
        System.out.println(mesGrilles);    
  }
       public synchronized void updateTemplate() {
        for (int k = 0; k < 3; k++) {
            for (Case c : this.dim3[k].getGrille()) {
                c.getLabel().setText(Integer.toString(c.getValeur()));
                switch(c.getValeur()) {
                    case 4:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane4");
                        break;
                    case 8:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane8");
                        break;
                    case 16:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane16");
                        c.getLabel().getStyleClass().add("tuile10");
                        break;
                    case 32:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane32");
                        break;
                    case 64:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane64");
                        break;
                        
                    case 128:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane128");
                        c.getLabel().getStyleClass().add("tuile100");
                        break;
                    case 256:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane256");
                        break;
                    case 512:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane512");
                        break;
                    case 1024:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane1024");
                        c.getLabel().getStyleClass().add("tuile1000");
                        break;
                    case 2048:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane2048");
                        break;
                    case 4096:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane4086");
                        break;
                    case 8192:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane8192");
                        break;
                    case 16384:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane16384");
                        break;
                }
            }  
        }
    }
    
       
    public void nouvelleCase() {
        ArrayList<Integer> grillePossible = new ArrayList<>();
        grillePossible.add(0); grillePossible.add(1); grillePossible.add(2);
        // si le tableau est vide cela signifie qu'on ne peut ajouter aucune case dans les grilles
        while (!grillePossible.isEmpty()) {
            int random = (int) (Math.random() * grillePossible.size()); 
                boolean newCase = dim3[grillePossible.get(random)].nouvelleCase();
                if (!newCase)
                    grillePossible.remove(random);
                else
                    break;
            
        }
        this.afficherTuile();
        this.positionTuile();
    }

    @FXML
    private void clickTop(MouseEvent event) throws CloneNotSupportedException {
            TuileComposite t = new TuileComposite();
           mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.HAUT);
            if (b1 || b2 || b3) {
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
               this.nouvelleCase();
            }
            this.updateTemplate(); // Pour la valeur du label (pour l'instant)
                 System.out.println(mesGrilles);  
    }

    @FXML
    private void clickBot(MouseEvent event) throws CloneNotSupportedException {
         TuileComposite t = new TuileComposite();
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));  
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.BAS);
            if (b1 || b2 || b3) {
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
                
            }
            this.updateTemplate(); // Pour la valeur du label (pour l'instant)
            System.out.println(mesGrilles);  
    }

    @FXML
    private void clickRight(MouseEvent event) throws CloneNotSupportedException {
         TuileComposite t = new TuileComposite();
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
               // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
                this.updateTemplate(); // Pour la valeur du label (pour l'instant)
        System.out.println(mesGrilles);  
            }
    }

    @FXML
    private void clickLeft(MouseEvent event) throws CloneNotSupportedException {
        TuileComposite t = new TuileComposite();

            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.GAUCHE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                  // Méthode composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();

            }
        this.updateTemplate(); // Pour la valeur du label (pour l'instant)
        System.out.println(mesGrilles);  
    }

    @FXML
    private void clickTpG(MouseEvent event) throws CloneNotSupportedException {
        TuileComposite t = new TuileComposite();
   
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(direction);  

            System.out.println(fusionSuccess);
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    

            }
         this.updateTemplate(); // Pour la valeur du label (pour l'instant)
        System.out.println(mesGrilles);  
         
    }

    @FXML
    private void clicktpD(MouseEvent event) throws CloneNotSupportedException {
      TuileComposite t = new TuileComposite();
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(direction);  
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                 t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    
                
            }
            this.updateTemplate(); // Pour la valeur du label (pour l'instant)
            System.out.println(mesGrilles);  
    }

   
       @FXML
    private void exit(ActionEvent event) {
         System.out.println("Les devs vous disent à bientôt!");
        ObjectOutputStream oos = null;
         if (mesGrilles.partieFinie()){
            mesGrilles.init(dim3);;
        }
        try{
            final FileOutputStream fichier = new FileOutputStream("model.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(this.mesGrilles);
            oos.flush();
        }catch (final IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(oos != null){
                    oos.flush();
                    oos.close();
            }
            }catch(final IOException ex){
                ex.printStackTrace();
            }
        }
        
        System.exit(0);
    }

    @FXML
    private void theme(ActionEvent event) {
    }

    @FXML
    private void popupcmd(ActionEvent event) {
        
    }
    
}
