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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.lang.Thread;

/**
 * FXML Controller class
 *
 * @author Niccolas
 */
public class WClassementController extends BDD implements Initializable {

    @FXML
    private ListView<?> listv1;
    private TextArea champDeRecherchePseudo;
    private Label pasDeCorrespondance;
    
    Connection co = openBDD();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**  Affiche dans la fenêtre le contenue de la BD dans la ListView 
     * 
     * @param event
     * @throws SQLException
     * @throws RuntimeException
     * @throws NullPointerException 
     */
    @FXML
    private void affichageScoreDsTableau(MouseEvent event) throws SQLException,RuntimeException,NullPointerException{
        ArrayList<String> tableau = null;
        
        /*2 postulats soit on a rien du coups il affiche tout soit il fait une recherche spécifique*/
        
        /*Je teste dans un premier temps si il y'a une valeur ou non dans le champ*/
        if(!champDeRecherchePseudo.getText().trim().isEmpty() == true){ // pose prob
          //  tableau = renvoieTableauBDD(this.co);
            pasDeCorrespondance.setText("Nous n'avons pas trouvé votre pseudo : "+champDeRecherchePseudo.getText());
        }

        /*Il cherche de manière spécifique*/
                if(!champDeRecherchePseudo.getText().trim().isEmpty() == false){
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
                pasDeCorrespondance.setText("Nous n'avons pas trouvé votre pseudo : "+pseudoSearch);
            }
        
        }
    }    


    protected static boolean checkVide(boolean check){
        return check;
    }

}

