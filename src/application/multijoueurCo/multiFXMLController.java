package application.multijoueurCo;

import BaseDeDonnee.BDD;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class multiFXMLController extends BDD implements Initializable {
    BDD db = new BDD();
    Connection co = db.openBDD();// initialisation de l'objet db

    @FXML
    private AnchorPane coPane;
    
    @FXML
    private TextField pseudoField;
    
    @FXML
    private PasswordField mdpField;
    @FXML
    private Label cdtAcces;
    
    @FXML
    private void connexion(ActionEvent event){
        boolean verifCompteExist = false;
        boolean cdt1;
        boolean cdt2;
        String pseudo;
        String password;
        
                /*      Fonctions verifiant les champs      */
        cdtValidationAcces(!pseudoField.getText().trim().isEmpty(),!mdpField.getText().trim().isEmpty());
        
                /*      Partie interagissant avec la base de données        */
        if(!pseudoField.getText().trim().isEmpty()){
            // partie qui va verfier si le compte existe
            pseudo = pseudoField.getText();
            try{
                cdt1 = verifPseudo(pseudo,this.co);
                System.out.println(cdt1);
            }catch(SQLException e){}
            
            if(!mdpField.getText().trim().isEmpty()){
            // partie qui va verfier si le mot de passe est le bon
            password = mdpField.getText();
            cdt2 = verifPswd(pseudo,password,this.co);
            System.out.println(cdt2);
            }
        }
    }
    
    @FXML
    private void inscription(ActionEvent event) throws IOException {      
        //String path = "/package/FXML.fxml";
        String path = "/application/multiInscription/inscriptionPanel.fxml";

        AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
        coPane.getChildren().setAll(pane);
    }

    @FXML
    private void quitter(ActionEvent event){
        // fonction qui permet de quitter
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void cdtValidationAcces(boolean cdt1,boolean cdt2){
        // fonction qui colore les bordures
        if(cdt1 == false || cdt2 ==false){
            cdtAcces.setText("Pseudo ou mot de passe incorrect");
            cdtAcces.setStyle("-fx-text-fill: red;");
            pseudoField.setStyle("-fx-border-color: red;");
            mdpField.setStyle("-fx-border-color: red;");
        }else{
            cdtAcces.setText("");
            cdtAcces.setStyle("-fx-text-fill: green;");
            pseudoField.setStyle("-fx-border-color: green;");
            mdpField.setStyle("-fx-border-color: green;");
        }
        if(cdt1==true){
            pseudoField.setStyle("-fx-border-color: orange;");
        }
    }

    
}
