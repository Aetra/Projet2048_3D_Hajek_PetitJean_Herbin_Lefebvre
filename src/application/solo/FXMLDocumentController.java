/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.solo;
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

import com.sun.naming.internal.FactoryEnumeration;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class FXMLDocumentController implements Initializable {
    private boolean hasGameStarted = false;

    @FXML
    private Pane scorePane;
    @FXML
    private Button hide;
    @FXML
    private AnchorPane container;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem reglesGames;
    @FXML
    private MenuItem rankG;
    @FXML
    private Pane goBack;
    @FXML    
    private RadioMenuItem thm1;
    @FXML
    private RadioMenuItem thm2;
    @FXML
    private RadioMenuItem thm3;
    @FXML
    private ToggleGroup chgtStyle;
    @FXML
    private Button bTop;
    @FXML
    private Button bBot, bLeft, bRight, bTpg, bTpd, cmd;
    @FXML
    private Pane fond;
    @FXML
    private Pane playButton;
    @FXML
    private Label playLabel;
    @FXML
    private Pane chronoPane;
    @FXML
    private GridPane grille2;
    @FXML
    private GridPane grille1;
    @FXML
    private Label mvtScoreLabel, showcmd,tp;
    private Label lTp,lUp,lDown,lMove,lRight,lLeft,lBot,lTop;


    /* moi */
    private Timeline timeline;

    @FXML
    private Button soloMode;
    @FXML
    private Button multiMode;

    @FXML
    private Label labelSeconds;
    @FXML
    private Label labelMinutes;
    @FXML
    private Label labelHours;
    @FXML
    private ImageView logo;

    private static final Integer STARTTIME = 0;
    private static final Integer MINUTESINANHOUR = 59;
    private static final Integer SECONDSINAMINUTE = 59;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;
    private Integer timeMinutes  = STARTTIME;
    private Integer timeHours = STARTTIME;

    /* plus moi */

    @FXML
    private Label scoreToLabel;
    int direction;
    
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
        scorePane.getStyleClass().add("scorePane");   
        grille.getStyleClass().add("grille");   
        chronoPane.getStyleClass().add("chronoPane");
        playButton.getStyleClass().add("playButton");

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
     private void cacherLabel(ActionEvent e) {
        FadeTransition hideLab = new FadeTransition(Duration.millis(500), tp);
        FadeTransition showLab = new FadeTransition(Duration.millis(500), tp);
        if (tp.getOpacity() ==  1.) {
            hideLab.setFromValue(1.0);
            hideLab.setToValue(0.);
            hideLab.play();
        }
        else {
            showLab.setFromValue(0.0);
            showLab.setToValue(1.0);
            showLab.play();
        }

        //if (tp.isVisible())
          //  tp.setVisible(false);
        //else tp.setVisible(true);
     }

    /** Permet de relancer le chronomètre en partant de 0*/
     private void restartChrono() {
         if (timeline != null) {
             timeline.stop();
         }
         timeSeconds = STARTTIME;
         timeMinutes = STARTTIME;
         timeHours = STARTTIME;


         // update timerLabel
         labelSeconds.setText("00");
         labelMinutes.setText("00");
         labelHours.setText("00");
         timeline = new Timeline();
         timeline.setCycleCount(Timeline.INDEFINITE);

         timeline.getKeyFrames().add(
                 new KeyFrame(Duration.seconds(1),
                         new EventHandler() {
                             public void handle(Event event) {
                                 timeSeconds++;

                                 labelSeconds.setText(
                                         String.format("%02d",timeSeconds)
                                 );
                                 labelMinutes.setText(
                                         String.format("%02d",timeMinutes)
                                 );
                                 labelHours.setText(
                                         String.format("%02d",timeHours)
                                 );
                                 if (timeSeconds >= SECONDSINAMINUTE) {
                                     timeSeconds = STARTTIME;
                                     if (timeMinutes >= MINUTESINANHOUR) {
                                         timeMinutes = STARTTIME;
                                         timeHours ++;
                                     }
                                     else timeMinutes ++;

                                 }
                             }
                         }));
         timeline.playFromStart();
     }

     /** Met un pause le chrono*/
     @FXML
     private void pausePlayChrono(MouseEvent e) {
        if (hasGameStarted) {
            if (Animation.Status.PAUSED == timeline.getStatus())
                timeline.play();
            else
            {
                timeline.pause();
            }
        }
     }

    /** Retourne la durée total du chrono en secondes*/
     private double getCurrentTime() {
         return timeline.getCurrentTime().toSeconds();
     }

     @FXML
     private void backHome(MouseEvent e) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/application/accueil/Accueil.fxml"));
         Scene scene = goBack.getScene();

         root.translateXProperty().set(scene.getWidth());
         StackPane parentContainer = (StackPane) scene.getRoot();
         parentContainer.getChildren().add(root);

         Timeline timeline2 = new Timeline();
         KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_BOTH);
         KeyFrame kf = new KeyFrame(Duration.millis(600),kv);
         timeline2.getKeyFrames().add(kf);
         timeline2.setOnFinished(event -> {
             parentContainer.getChildren().remove(container);
         });

         timeline2.play();
     }

    @FXML
    private void highlightButton(MouseEvent e) {
        ColorAdjust colorAdjust = new ColorAdjust();


        // Setting the brightness value
        colorAdjust.setBrightness(1);


        Pane source = (Pane) e.getSource();
        source.setEffect(colorAdjust);
    }

    @FXML
    private void cancelHighlight(MouseEvent e) {
        Pane source = (Pane) e.getSource();
        source.setEffect(null);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {



         hasGameStarted = true;
         this.removeAll();
         modelGrille1 = new Grille(0);
         modelGrille2 = new Grille(1);
         modelGrille3 = new Grille(2);
         mesGrilles = Dimension3.INSTANCE;
         dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
         mesGrilles.init(dim3);
    /*
    for (int i = 0; i < 2; i++) {
        int random = (int) (Math.random() * 3);
        this.mesGrilles[random].nouvelleCase();
    }
    */
         for (int i = 0; i < 2; i++) {
             int random = (int) (Math.random() * 3);
             this.dim3[random].nouvelleCase();
         }
         this.afficherTuile();
         this.positionTuile();
         mvtScoreLabel.setText("0");
         System.out.println(dim3);


         FadeTransition ft = new FadeTransition(Duration.millis(500), chronoPane);
         ft.setFromValue(0.0);
         ft.setToValue(1.);
         ft.play();
         restartChrono();
         playLabel.setText("Restart");




    }
    
      private void removeAll() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.dim3[i].getGrille()) {
                fond.getChildren().remove((Node) c.getPane());
            }
        }
    }
    
    @FXML
    private void keyPressed(KeyEvent ke) throws CloneNotSupportedException {
        TuileComposite t = new TuileComposite();

        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche

         System.out.println(mesGrilles.getValeurMax());
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.GAUCHE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                  // Méthode composite
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
            
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
        } else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.HAUT);
            if (b1 || b2 || b3) {
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
               this.nouvelleCase();
            }
            
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            boolean b1 = this.dim3[0].lanceurDeplacerCases(Parametres.BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(Parametres.BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(Parametres.BAS);
            if (b1 || b2 || b3) {
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
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
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
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
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    
            }
        }
        this.updateTemplate(); // Pour la valeur du label (pour l'instant)
        scoreToLabel.setText(String.valueOf(mesGrilles.getValeurMax())); // mise à jour du compteur de mouvement

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
    private void regles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/solo/extra/GameRules.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FINAL RULES");
        stage.setScene(new Scene(root1));  
        stage.show();
           }
    
        @FXML
    private void checkrank(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/solo/extra/PopupWinLose.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FINAL RULES");
        stage.setScene(new Scene(root1));  
        stage.show();
           }

    @FXML
    private void switchThm(ActionEvent e) {
        fond.getStylesheets().clear();
        RadioMenuItem source = (RadioMenuItem) e.getSource();
        if (source == thm1) fond.getStylesheets().add("css/styles1.css");
        else if (source == thm2) fond.getStylesheets().add("css/styles2.css");
        else if (source == thm3) fond.getStylesheets().add("css/psychedelic.css");
        else return;

    }
        
}
