/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.accueil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class AccueilController implements Initializable {

    @FXML
    private StackPane parentContainer;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Pane buttonSolo;
    @FXML
    private Pane buttonMulti;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelHighlight(MouseEvent event) {
        Pane source = (Pane) event.getSource();
        source.setEffect(null);
    }

    @FXML
    private void highlightButton(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();


        // Setting the brightness value
        colorAdjust.setBrightness(1);


        Pane source = (Pane) event.getSource();
        source.setEffect(colorAdjust);
    }

    @FXML
    private void startModeSolo(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/solo/ThemeBasique.fxml"));
        Scene scene = buttonSolo.getScene();
        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.millis(600),kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }

    @FXML
    private void startModeMulti() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/multijoueurCo/multiCoFXML.fxml"));
        Scene scene = buttonSolo.getScene();
        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.millis(600),kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
}
