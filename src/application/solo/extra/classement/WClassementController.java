/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.solo.extra.classement;

import BaseDeDonnee.BDD;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class WClassementController extends BDD implements Initializable {

    @FXML
    private ListView<?> listv1;
    @FXML
    private TextArea champDeRecherchePseudo;
    @FXML
    private Label pasDeCorrespondance;
    
    Connection co = openBDD();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /** Permet d'afficher les scores dans le tableau
     * @param event
     * @throws SQLException 
     * On récupère le pseudo, le compare
     * Pour ensuite pouvoir insérer le contenu de notre BD dans affichageScore
     */
    @FXML
    private void affichageScoreDsTableau(MouseEvent event) throws SQLException {
        boolean cdtNothing = true;
        
        /*Je teste dans un premier temps si il y'a une valeur ou non dans le champ*/
        if(!champDeRecherchePseudo.getText().trim().isEmpty()){
            cdtNothing = false;
        }
        /*2 postulats soit on a rien du coups il affiche tout soit il fait une recherche spécifique*/
        
        /*Il n'a rien mais appuie sur le bouton*/
        if(cdtNothing == true){
        
        }
        /*Il cherche de manière spécifique*/
        if(cdtNothing == false){
            String pseudoSearch = "'"+champDeRecherchePseudo.getText()+"'";
            boolean pseudo = verifPseudo(pseudoSearch,this.co);
            /*Ici 2 cas soit la recherche aboutis soit il c'est trompé*/
            if(pseudo == true){
                double chrono = recupChrono(pseudoSearch,this.co);
                int score = recupScore(pseudoSearch,this.co);
                int move = recupMove(pseudoSearch,this.co);
                
                
            }
            
            /*Dans ce cas on affiche tout avec une erreur via le label pasDeCorrespondance*/
            if(pseudo == false){
                pasDeCorrespondance.setText("Nous n'avons pas trouvé "+pseudoSearch);
            }
        
        }
    }   
    
    
  
}
