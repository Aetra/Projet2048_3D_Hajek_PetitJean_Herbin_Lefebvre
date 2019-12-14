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

/**
 * FXML Controller class
 *
 * @author Val
 */
public class PopupWinLoseController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendScore(MouseEvent event) {
    }

    @FXML
    private void pausePlayChrono(MouseEvent event) {
    }
    
}
