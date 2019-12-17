package application.solo;
import Model.CareTaker;
import Model.Case;
import Model.Dimension3;
import Model.Grille;
import Model.Originator;
import Model.Tuile2048;
import Model.TuileComposite;
import Model.IA.God;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Val
 */
public class FXMLDocumentController implements Initializable, Serializable, Model.Parametres{;
    private boolean hasGameStarted = false;
    @FXML
    private Pane fond, scorePane, scorePane2, chronoPane, goBack;
    private AnchorPane container;
    @FXML
    private GridPane grille,grille1,grille2;
    @FXML
    private Button hide;
    @FXML
    private MenuBar menuBar; 
    @FXML
    private MenuItem reglesGames, rankG, thm1,thm2,thm3,thm4,fichierMenu,rankingMenu,helpMenu,leave,load;
    @FXML
    private Button bBot, bLeft, bRight, bTpg, bTpd,bTop;
    @FXML
    private Pane playButton;
    @FXML
    private Label mvtScoreLabel, scoreToLabel, tp, playLabel, scoreLabel, mvtLabel,iaLabel;
    @FXML
    private Label lTp,lUp,lDown,lMove,lRight,lLeft,lBot,lTop;
    @FXML
    private Label labelSeconds,labelMinutes,labelHours;
    private Timeline timeline;
    private static final Integer STARTTIME = 0;
    private static final Integer MINUTESINANHOUR = 59;
    private static final Integer SECONDSINAMINUTE = 59;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;
    private Integer timeMinutes  = STARTTIME;
    private Integer timeHours = STARTTIME;
    int direction;
    
    private Grille modelGrille1;
    private Grille modelGrille2;
    private Grille modelGrille3;
    
    //3 dimension
    private Grille[] dim3;
    private Dimension3 mesGrilles;
    
    
    // Pour le MenuItem revenir en arrière
    private Originator originator;
    private CareTaker careTaker;


    private int x = 25, y = 295;
    private int objectifx = 25, objectify = 295;

   
    @FXML
    private ToggleGroup chgtStyles;
    @FXML
    private ToggleGroup chgtStyles1;
    @FXML
    private ImageView backHome;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // TODO
        
        System.out.println("le contrôleur initialise la vue");
        fond.getStyleClass().add("fond");  
        scorePane.getStyleClass().add("scorePane");
        scorePane2.getStyleClass().add("scorePane2");   
        playLabel.getStyleClass().add("playLabel");
        grille.getStyleClass().add("grille");   
        chronoPane.getStyleClass().add("chronoPane");
        playButton.getStyleClass().add("playButton");
        bBot.getStyleClass().add("bBot");
        bLeft.getStyleClass().add("bLeft");
        bRight.getStyleClass().add("bRight");
        bTop.getStyleClass().add("bTop");
        bTpg.getStyleClass().add("bTpg");
        bTpd.getStyleClass().add("bTpd");
        hide.getStyleClass().add("hide");
        grille.getStyleClass().add("grille");
        grille1.getStyleClass().add("grille1");
        grille2.getStyleClass().add("grille2");
        menuBar.getStyleClass().add("menuBar");
        goBack.getStyleClass().add("goBack");
        iaLabel.getStyleClass().add("iaLabel");
        mvtLabel.getStyleClass().add("mvtLabel");
        scoreLabel.getStyleClass().add("scoreLabel");
        fichierMenu.getStyleClass().add("fichierMenu");
        rankingMenu.getStyleClass().add("rankingMenu");
        helpMenu.getStyleClass().add("helpMenu");


        // Initialisation de ma multi-grille
        modelGrille1 = new Grille(0);
        modelGrille2 = new Grille(1);
        modelGrille3 = new Grille(2);        
        dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
        mesGrilles = new Dimension3();
                
        // Pour le bouton revenir en arrière
        originator = new Originator();
        careTaker = new CareTaker();
        mesGrilles.initStart(dim3);
    }
    
    /** Retourne la durée total du chrono en secondes

     * @return  timeline
    */
     public double getCurrentTime() {
         return timeline.getCurrentTime().toSeconds();
     }
    
     /** retourne le score max de la 3 dimension
      * 
      * @return 
      */
     public int bdGetSocreMax(){
         return Integer.parseInt(scoreToLabel.getText());
     }
     /** retoune le nombre de mouvement
      * 
      * @return 
      */
     public int bdGetMouvement(){
         return Integer.parseInt(mvtScoreLabel.getText());
     }
     
     /** Fonction permettant de quitter et de sauvegarder la dimension3
      * 
      * @param event 
      */
    @FXML
    private void exit(ActionEvent event) {
         ObjectOutputStream oos = null;
         try {
            File f = new File(new File(new File(FXMLDocumentController.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()).getParent() + "\\save.ser");
            final FileOutputStream fichier = new FileOutputStream(f);
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(this.modelGrille1);
            oos.writeObject(this.modelGrille2);
            oos.writeObject(this.modelGrille3);
            System.out.println("ToutesLessauvegardes good");
            oos.flush();
            System.out.println("ToutesLessauvegardes good2");

        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

     }
    
    /** Fonction permetant de charger la dernière sauvegarde effectuée
     * 
     * @param event 
     */
        @FXML
    private void loading(ActionEvent event) {
        this.removeAll();
        ObjectInputStream ois = null;
        Grille[] grilles = new Grille[3];
        try {
            File f = new File(new File(new File(FXMLDocumentController.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()).getParent() + "\\save.ser");
            final FileInputStream fichierIn = new FileInputStream(f);
            ois = new ObjectInputStream(fichierIn);
            for (int i = 0; i < grilles.length; i++) {
                grilles[i] = (Grille) ois.readObject();
                System.out.println("Serialization");
                System.out.println(grilles[i]);
                //Enregistre DANS chq grille chelou pette couilles
            }
            this.mesGrilles.setMesGrilles(grilles);
            System.out.println(this.mesGrilles);
          } catch (final java.io.IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /** Fonction affichant les tuiles de départ sur l'interfaces graphique
     * Parcours les 3 grilles et chaque Cases
     * Récupère le pane et set la valeur à l'intérieur des tuiles
     */
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

    /** Fonction permettant la position des tuiles sur la grilles
     * Prend en compte les dimensions et le placement des grilles sur la fenêtre
     * Pren en compte l'espacement entre les grilles
     */
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
    
    
    
    @FXML
    private void handleDragAction(MouseEvent event) { 
     }

    /** Fonction permettant de cacher le label où les commandes sont répertoriés
     * 
     * @param e 
     */
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

     /** Met en pause le chrono
      *S'il est en pause on le play
      */
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

     /** Fonction permettant le retour en arriere vers la pages d'accueil
      * Lorsque l'utilisateur clique sur l'ImageView retour a Accueil.fxml
      * @param e
      * @throws IOException 
      */
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

     /** Permet de colorer avec un effet de highlight le bouton cliblé
      * 
      * @param e 
      */
    @FXML
    private void highlightButton(MouseEvent e) {
        ColorAdjust colorAdjust = new ColorAdjust();
        // Setting the brightness value
        colorAdjust.setBrightness(1);
        Pane source = (Pane) e.getSource();
        source.setEffect(colorAdjust);
    }

    /** Annule l'effet de Highlit du bouton
     * 
     * @param e 
     */
    @FXML
    private void cancelHighlight(MouseEvent e) {
        Pane source = (Pane) e.getSource();
        source.setEffect(null);
    }

    /** Fonction appelé au démarache d'une partie lorsque on appuie sur play
     * Restart pris en compte, on remet les grilles et label à son origine
     * on set graphiquement également à l'origine
     * @param event 
     */
    @FXML
    private void starting(MouseEvent event) {

         hasGameStarted = true;
         this.removeAll();
         modelGrille1 = new Grille(0);
         modelGrille2 = new Grille(1);
         modelGrille3 = new Grille(2);
         mesGrilles = new Dimension3();
         dim3 = new Grille[]{modelGrille1, modelGrille2, modelGrille3};
         mesGrilles.initStart(dim3);

         for (int i = 0; i < 2; i++) {
             int random = (int) (Math.random() * 3);
             this.dim3[random].nouvelleCase();
         }
         this.afficherTuile();
         this.positionTuile();
         mvtScoreLabel.setText("0");
         scoreToLabel.setText("0");
         System.out.println(dim3);
         FadeTransition ft = new FadeTransition(Duration.millis(500), chronoPane);
         ft.setFromValue(0.0);
         ft.setToValue(1.);
         ft.play();
         restartChrono();
         playLabel.setText("Restart");
    }
    
    /** Permet de détruire le model
     * 
     */
      private void removeAll() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.dim3[i].getGrille()) {
                fond.getChildren().remove((Node) c.getPane());
            }
        }
    }
    
      /** Fonction prenant en compte l'appuie de la touche par l'utilisateur
       * La touches doit être une de celle de listé dans la listes des commandes possibles
       * Si la touche est correct, appel des threads permettant le mouvement et la destruction d'une case
       * On met à jour les labels et update le template
       * @param ke
       * @throws CloneNotSupportedException
       * @throws IOException 
       */
    @FXML
    private void keyPressed(KeyEvent ke) throws CloneNotSupportedException, IOException{
        TuileComposite t = new TuileComposite();
        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche

         System.out.println(mesGrilles.getValeurMax());
            boolean b1 = this.dim3[0].lanceurDeplacerCases(GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(GAUCHE);
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
            boolean b1 = this.dim3[0].lanceurDeplacerCases(DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(DROITE);
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
            boolean b1 = this.dim3[0].lanceurDeplacerCases(HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(HAUT);
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
            boolean b1 = this.dim3[0].lanceurDeplacerCases(BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(BAS);
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
           else if (touche.compareTo("l") == 0) { // FUSION Down
            boolean fusionSuccess=mesGrilles.lanceurDeplacerCases(DESCENDRE);
            if (fusionSuccess) {
                mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();            
            }
        } else if (touche.compareTo("m") == 0) { // FUSION Up
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(MONTER);             
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
        scoreToLabel.setText(String.valueOf(mesGrilles.getValeurMax())); 
        System.out.println(mesGrilles);    
  }
    /** Fonction  permettant le déplacement vers le haut
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
        @FXML
    private void clickTop(MouseEvent event) throws CloneNotSupportedException, IOException {
           TuileComposite t = new TuileComposite();
           mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(HAUT);
            if (b1 || b2 || b3) {
                // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
               this.nouvelleCase();
            }
            this.updateTemplate();
            System.out.println(mesGrilles);  
    }

    /** Fonction  permettant le déplacement vers le bas
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
    @FXML
    private void clickBot(MouseEvent event) throws CloneNotSupportedException, IOException {
         TuileComposite t = new TuileComposite();
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));  
            boolean b1 = this.dim3[0].lanceurDeplacerCases(BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(BAS);
            if (b1 || b2 || b3) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
                
            }
            this.updateTemplate();
            System.out.println(mesGrilles);  
    }

    /** Fonction  permettant le déplacement vers la droite
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
    @FXML
    private void clickRight(MouseEvent event) throws CloneNotSupportedException, IOException {
         TuileComposite t = new TuileComposite();
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
               // Patern composite
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
                this.updateTemplate();
        System.out.println(mesGrilles);  
            }
    }

    /** Fonction  permettant le déplacement vers la gauche
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
    @FXML
    private void clickLeft(MouseEvent event) throws CloneNotSupportedException, IOException {
        TuileComposite t = new TuileComposite();
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean b1 = this.dim3[0].lanceurDeplacerCases(GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(GAUCHE);
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
        this.updateTemplate();
        System.out.println(mesGrilles);  
    }

    /** Fonction  permettant le déplacement vers la descente, soit vers la gauche
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique et gestion de la fusion
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
    @FXML
    private void clickTpG(MouseEvent event) throws CloneNotSupportedException, IOException {
        TuileComposite t = new TuileComposite();
        mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(DESCENDRE);  
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    

            }
         this.updateTemplate();
        System.out.println(mesGrilles);  
         
    }

    /** Fonction  permettant le déplacement vers la Monter, soit vers la droite
     * Clique sur le bouton nécessaire
     * Si clique déclanchement des threads et mises à jours de l'interface graphique et gestion de la fusion
     * @param event
     * @throws CloneNotSupportedException
     * @throws IOException 
     */
    @FXML
    private void clicktpD(MouseEvent event) throws CloneNotSupportedException, IOException {
      TuileComposite t = new TuileComposite();
      mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(MONTER);  
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                 t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();    
                
            }
            this.updateTemplate();
            System.out.println(mesGrilles);  
    }
    
       /** Fonction permettant la mise à jour de l'interface graphique
        * Appelé lors d'un événement déclenché par l'utilisateur
        */
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
    

    /** Fonction gérant a la fois le déroulement du jeu et la nouvelle case
     * On crée une nouvelle Case si sur la grille c'est disponible
     */
    public void nouvelleCase() throws IOException {
      if(mesGrilles.getValeurMax()<OBJECTIF){
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
      else{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/solo/extra/PopupWinLose.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FINAL RULES");
        stage.setScene(new Scene(root1));  
        stage.show();
      }
    }
       
    /** Fonction permettant lors du clique sur le MenuItem régles d'afficher ces dernières
     * La fenêtre se met au dessus de l'application
     * @param event
     * @throws IOException 
     */
    @FXML
    private void regles(ActionEvent event) throws IOException {
         try {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/solo/extra/GameRules.fxml"));
         Parent root1 = (Parent) fxmlLoader.load();
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setTitle("Rules");
         stage.setScene(new Scene(root1));
         stage.setOnCloseRequest((WindowEvent event1) -> {
             fond.setEffect(null);
         });
         stage.show();
     } catch (Exception e) {
        e.printStackTrace();
     }
 }
    /**Fonction permettant l'utilisation de l'ia Conseil
     * Nécessite le clique sur le menuItem Je ne sais pas joue IA dans le menu help
     * Crée un objet God qui renvoie sa méthode omniscience dans un label 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void iaJustHelp(ActionEvent event) throws IOException {
       iaLabel.setText("");
       God oranos= new God(mesGrilles);
       iaLabel.setText("Vous avez appelé l'aide des dieux.   " +oranos);
       
    }
    /** Fonction permettant l'utilisation de l'IA player
     * On précise lorsque l'IA a joué avec un label iaLabel
     * On crée un objet God qui renvoie dans une variable ces instructions
     * on compare les instructions et définit déclenche les threads de mouvements dans la direction indiqué par l'IA
     * @param event
     * @throws IOException
     * @throws CloneNotSupportedException 
     */
    @FXML
    private void iaPlay(ActionEvent event) throws IOException, CloneNotSupportedException {
          iaLabel.setText("");
          God oranos= new God(mesGrilles);
          iaLabel.setText("Dieux à joué de la meilleur possible selon les 5 prochains coups");

          String textStockIa= ""+oranos;
          System.out.println(""+textStockIa);
          TuileComposite t = new TuileComposite();
          if(textStockIa.equals("Les dieux disent : Bas (s)"))
          {
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));  
            boolean b1 = this.dim3[0].lanceurDeplacerCases(BAS);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(BAS);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(BAS);
            if (b1 || b2 || b3) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();           
            }
                  
          }
          else if(textStockIa.equals("Les dieux disent : Haut (z)"))
          {
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(HAUT);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(HAUT);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(HAUT);
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
          else if(textStockIa.equals("Les dieux disent : Gauche (q)"))
          {
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean b1 = this.dim3[0].lanceurDeplacerCases(GAUCHE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(GAUCHE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(GAUCHE);
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
          else if(textStockIa.equals("Les dieux disent : Droite (d)"))
          {
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1));
            boolean b1 = this.dim3[0].lanceurDeplacerCases(DROITE);
            boolean b2 = this.dim3[1].lanceurDeplacerCases(DROITE);
            boolean b3 = this.dim3[2].lanceurDeplacerCases(DROITE);
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
          else if(textStockIa.equals("Les dieux disent : Monter (m)"))
          {
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(MONTER);  
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();                 
            }
              
          }
          else if(textStockIa.equals("Les dieux disent : Descendre (l)"))
          {
            boolean fusionSuccess = mesGrilles.lanceurDeplacerCases(DESCENDRE);  
            if (fusionSuccess) {
                t.add(new Tuile2048(this.dim3[0]));
                t.add(new Tuile2048(this.dim3[1]));
                t.add(new Tuile2048(this.dim3[2]));
                t.threadMovement();
                t.threadMovementCaseDead(fond);
                this.nouvelleCase();
            }
 
          }
          else{
              System.out.println("Sorry Dave: je comprend pas");
          }
            mvtScoreLabel.setText(Integer.toString(Integer.parseInt(mvtScoreLabel.getText()) + 1)); // mise à jour du compteur de mouvement
            this.updateTemplate();
            System.out.println(mesGrilles);  
        }
    
        @FXML
        private void checkrank(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/solo/extra/WClassement.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Classement");
            stage.setScene(new Scene(root1));  
            stage.show();
          }
    
    /** Fonction permettant la préparation des styles
     * 
     */
    private void prepStylesSheets(){
            scorePane.getStyleClass().add("scorePane");
            scorePane2.getStyleClass().add("scorePane2");
            playLabel.getStyleClass().add("playLabel");
            grille.getStyleClass().add("grille");
            chronoPane.getStyleClass().add("chronoPane");
            playButton.getStyleClass().add("playButton");
            bBot.getStyleClass().add("bBot");
            bLeft.getStyleClass().add("bLeft");
            bRight.getStyleClass().add("bRight");
            bTop.getStyleClass().add("bTop");
            bTpg.getStyleClass().add("bTpg");
            bTpd.getStyleClass().add("bTpd");
            hide.getStyleClass().add("hide");
            grille.getStyleClass().add("grille");
            grille1.getStyleClass().add("grille1");
            grille2.getStyleClass().add("grille2");
            menuBar.getStyleClass().add("menuBar");
            goBack.getStyleClass().add("goBack");
            iaLabel.getStyleClass().add("iaLabel");

            mvtLabel.getStyleClass().add("mvtLabel");
            scoreLabel.getStyleClass().add("scoreLabel");
            fichierMenu.getStyleClass().add("fichierMenu");
            rankingMenu.getStyleClass().add("rankingMenu");
            helpMenu.getStyleClass().add("helpMenu");
            
    }
    /** Fonction permettant de changer de theme
     * L'utilisateur clique sur le menuItem changer theme et fichier
     * On stock dans chaque menuItem la valeur d'un fichier css
     * Le pane général prend la valeur du fichier css
     * Le css est chargé et remplace les valeur de chaque composant préparé dans getStylesSheets
     * @param e 
     */
      @FXML
    private void switchThm(ActionEvent e) {
        fond.getStylesheets().clear();
        scorePane.getStylesheets().clear();
        scorePane2.getStylesheets().clear();
        playLabel.getStylesheets().clear();
        grille.getStylesheets().clear();
        chronoPane.getStylesheets().clear();
        playButton.getStylesheets().clear();
        bBot.getStylesheets().clear();
        bLeft.getStylesheets().clear();
        bRight.getStylesheets().clear();
        bTop.getStylesheets().clear();
        bTpg.getStylesheets().clear();
        bTpd.getStylesheets().clear();
        hide.getStylesheets().clear();
        grille.getStylesheets().clear();
        grille1.getStylesheets().clear();
        grille2.getStylesheets().clear();
        menuBar.getStylesheets().clear();
        goBack.getStylesheets().clear();
        mvtLabel.getStyleClass().clear();
        mvtLabel.getStyleClass().clear();
        scoreLabel.getStyleClass().clear();
        fichierMenu.getStyleClass().clear();
        rankingMenu.getStyleClass().clear();
        helpMenu.getStyleClass().clear();
        mvtLabel.getStyleClass().clear();

        RadioMenuItem source = (RadioMenuItem) e.getSource();
        if (source == thm1) {
           fond.getStylesheets().add("css/styles1.css");
           prepStylesSheets();
        } else if (source == thm2) {
            fond.getStylesheets().add("css/styles2.css");
            prepStylesSheets(); 
        } else if (source == thm3) {
            fond.getStylesheets().add("css/psychedelic.css");
            prepStylesSheets();

        } 
        else if (source == thm4) {
            fond.getStylesheets().add("css/styles.css");
            prepStylesSheets();
        } else {
            return;
        }

    }
 
    /**Retourne le boolean qui permemt de dire si la partie à commencée*/
    public boolean getHasGameStarted(){
        return this.hasGameStarted;
    }
}
