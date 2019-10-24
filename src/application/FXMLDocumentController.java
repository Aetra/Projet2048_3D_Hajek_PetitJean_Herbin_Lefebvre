package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Case;
import Model.Grille;
import Model.Dimension3;
import Model.Grille;
import Model.Parametres;
       
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Val
 */
public class FXMLDocumentController implements Initializable {

    ArrayList<Pane[][]>listTabPane=new ArrayList<>();
    ArrayList<Label[][]>listTabLabel=new ArrayList<>();
    @FXML
    private Pane fond;
    @FXML
    private GridPane grille2;
    @FXML
    private GridPane grille1;
    @FXML
    private GridPane grille;
    @FXML
    private Label mvtScoreLabel;
    @FXML
    private Label scoreTotLabel;
    
       // mes changements
    private Grille modelGrille1 = new Grille(0);
    private Grille modelGrille2= new Grille(1);
    private Grille modelGrille3 = new Grille(2);
    private Grille[] dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
    private Dimension3 grilleDim3 = new Dimension3(dim3);
    

//  private final Pane p = new Pane(); // panneau utilisé pour dessiner une tuile "2"
    //private final Label c = new Label("2");

    private int tailleXT=334/3;
    private int tailleYT=334/3;
    private int x = 25, y = 295;
    private int objectifx = 25, objectify = 295;
    /**
     * Initializes the controller class.
     */
     // variables globales non définies dans la vue (fichier .fxml)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // TODO
        System.out.println("le contrôleur initialise la vue");
        //this.creationTuile();
      //  fond.getChildren().add(creationTuile());
        fond.getStyleClass().add("fond");
        
 /*       
        
        p.getStyleClass().add("pane");
        p.getChildren().add(c);



        GridPane.setHalignment(c, HPos.CENTER);
        fond.getChildren().add(p);

        // on place la tuile en précisant les coordonnées (x,y) du coin supérieur gauche
        
        p.setLayoutX(x);
        p.setLayoutY(y);
        p.setVisible(true);
        c.setVisible(true);
        */
    }    

    private void creationTuile() {
        System.out.println("test Creation tuiles");
        for (int k = 0; k < 3; k++) {
            Pane[][] listP = new Pane[3][3];
            Label[][] listL = new Label[3][3];
            for (Case c : this.dim3[k].getGrille()) {
                if (c.getPane() == null) {
                    System.out.println("test int");
                    c.setPane(new Pane());
                    c.setLabel(new Label());
                   // c.getPane().getStyleClass().add("pane");
                    c.getLabel().getStyleClass().add("tuile");
                }
            }
        }
    }  
    
    
    private void colorTuile() {
        synchronized(this.dim3) {
            for (int i = 0; i < 3; i++) {
                for (Case c : this.dim3[i].getGrille()) {
                    System.out.println(c);
                    if (c.getLastX() == -1) {
                        System.out.println("JE SUIS ENTRE");
                        //GridPane.setHalignment(c.getLabel(), HPos.CENTER);

                        System.out.println("1");

                        //c.getPane().setLayoutX(24 + 18 * i + i * 397 + (c.getX() * tailleXT)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                        //c.getPane().setLayoutY(191 + (c.getY() * tailleYT));

                        //fond.getChildren().add(c.getPane());
                        //c.getPane().getChildren().add(c.getLabel());
                        //c.getPane().setVisible(true);
                        //c.getLabel().setVisible(true);
                    }
                    c.getLabel().setText(Integer.toString(c.getValeur()));
                }
            }
        }
    }

    @FXML
    private void handleDragAction(MouseEvent event) {
        System.out.println("Glisser/déposer sur la grille avec la souris");
        double x = event.getX();//translation en abscisse
        double y = event.getY();//translation en ordonnée
        if (x > y) {
            for (int i = 0; i < grille.getChildren().size(); i++) { //pour chaque colonne
                for (int j = 0; j < grille.getRowConstraints().size(); j++) { //pour chaque ligne
                System.out.println("ok1");
                grille.getChildren().remove(i);

                Node tuile = grille.getChildren().get(i);
                 if (tuile != null) {
                 int rowIndex = GridPane.getRowIndex(tuile);
                 int rowEnd = GridPane.getRowIndex(tuile);
                 }
                 }
            }
        } else if (x < y) {
            System.out.println("ok2");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Pane p = new Pane();
                    p.getStyleClass().add("pane");
                    grille.add(p, i, j);
                    p.setVisible(true);
                    grille.getStyleClass().add("gridpane");
                }
            }
        }
    }
    @FXML
    private void keyPressed(KeyEvent ke) {
         System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.GAUCHE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                this.threadMouvement();
                this.nouvelleCase();
            }
            
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                this.threadMouvement();
                this.nouvelleCase();
            }
        } else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.HAUT);
            if (b1 || b2 || b3) {
                this.threadMouvement();
                this.nouvelleCase();
            }
            
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));  
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.BAS);
            if (b1 || b2 || b3) {
                this.threadMouvement();
                this.nouvelleCase();
            }              
            
        }
        System.out.println(grilleDim3);
    }
        
 public void threadMouvement() {
        Grille[] cloned = this.dim3.clone();
        for (int i = 0; i < 3; i++) {    
            for (Case c  : this.dim3[i].getGrille()) {
                final int fi = i;
                Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
                    @Override
                    public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                        // Après mouvement
                        int objectifx = 24 + 18 * fi + fi * 397 + (c.getX() * tailleXT);
                        int objectify = 191 + tailleYT * c.getY();
                        //int test = 191 + 191 * c.getY();
                        // Avant mouvement
                        int x = 24 + 18 * fi + fi * 397 + (c.getLastX() * tailleXT);
                        int y = 191 + tailleYT * c.getLastY();
                        while (x != objectifx || y != objectify) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                            if (x < objectifx) {
                                x += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                            } else {
                                x -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                            }
                            if (y < objectify) {
                                y += 1; // si on va vers le , on modifie la position de la tuile pixel par pixel vers la droite
                            } else {
                                y -= 1; // si on va vers le , idem en décrémentant la valeur de x
                            }
                            // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                            final int varX = x;
                            final int varY = y;
                            Platform.runLater(new Runnable() { // classe anonyme
                                    @Override
                                    public void run() {
                                        /*System.out.println("Case de traitement : " + c);
                                        System.out.println("OBJECTIF:");
                                        System.out.println("objectifx:"+objectifx);
                                        System.out.println("X:        "+varX);
                                        System.out.println("objectify:"+objectify);
                                        System.out.println("Y        :"+varY);*/
                                       // c.getPane().relocate(varX, varY);
                                        //c.getPane().setVisible(true); 
                                    }
                                }
                            );
                            Thread.sleep(1);
                        } // end while
                        return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
                    } // end call

                };
                Thread th = new Thread(task); // on crée un contrôleur de Thread
                th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
                th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
            }
        }
    } 

  public void nouvelleCase() {
        // function weird
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
        System.out.println("COLORIAGE");
        this.creationTuile();
        this.colorTuile();
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {	
	for(int i=0; i<2; i++){ 
            Random r=new Random();
            r.nextInt((2-4)+1);
           //this.creationTuile();
        }
    }
 }
