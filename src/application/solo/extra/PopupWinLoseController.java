/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.solo.extra;

import BaseDeDonnee.BDD;
import application.solo.FXMLDocumentController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import static BaseDeDonnee.BDD.openBDD;
import java.sql.Connection;
import javafx.animation.Animation;
import javafx.animation.Timeline;

/**
 * FXML Controller class
 *
 * @author Val
 */
public class PopupWinLoseController extends BDD implements Initializable {

    Connection co = openBDD();
    
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
    @FXML
    private TextArea pseudoArenaField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendScore(MouseEvent event) {
        FXMLDocumentController fxml = new FXMLDocumentController();
       //insertLigneScore(String pseudoInsert, int mvt, int score, int chrono, Connection co)
       insertLigneScore(pseudoArenaField.getText(),fxml.bdGetMouvement(),fxml.bdGetSocreMax(),fxml.getCurrentTime(),this.co);
    }

    @FXML
    private void pausePlayChrono(MouseEvent event) {
        FXMLDocumentController fxml = new FXMLDocumentController();
        Timeline timeline = null;
        if (fxml.getHasGameStarted()) {
            if (Animation.Status.PAUSED == timeline.getStatus())
                timeline.play();
            else
            {
                timeline.pause();
            }
        }
    }
    
}
