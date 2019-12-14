/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.solo.extra;

import application.solo.FXMLDocumentController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import BaseDeDonnee.*;
import java.sql.Connection;
import javafx.animation.Animation;
import javafx.scene.control.TextArea;


/**
 * FXML Controller class
 *
 * @author Val
 */
public class PopupWinLoseController extends BDD implements Initializable {

    @FXML
    private Button send;
    @FXML
    private Pane chronoPane;
    @FXML
    private Label labelHours;
    @FXML
    private Label labelMinutes;
    @FXML
    private Label labelSeconds;
    
    private Connection co = openBDD();
    
    @FXML
    private TextArea insertPseudoField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendScore(MouseEvent event) {
        String pseudo = insertPseudoField.getText();
        FXMLDocumentController jeu = new FXMLDocumentController();
        insertLigneScore(pseudo, jeu.bdGetMouvement(), jeu.bdGetSocreMax(), jeu.getCurrentTime(), this.co);
    }

    @FXML
    private void pausePlayChrono(MouseEvent event) {
        FXMLDocumentController jeu = new FXMLDocumentController();
                if (jeu.getStartParty()) {
            if (Animation.Status.PAUSED == jeu.getTimeLine().getStatus())
                jeu.getTimeLine().play();
            else
            {
                jeu.getTimeLine().pause();
            }
        }
    }
    
}
