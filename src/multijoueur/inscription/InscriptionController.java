package multijoueur.inscription;

import BaseDeDonnee.*;
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

public class InscriptionController extends BDD implements Initializable {
    BDD db = new BDD();
    Connection co = db.openBDD();// initialisation de l'objet db
    
    @FXML
    private AnchorPane inscPane;
    @FXML
    private TextField pseudoField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label pseudoLabel;
    @FXML
    private Label pswdLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /** Fonction d'inscription permettant l'inscription et qu'il n'y ait pas d'erreur dans la bd
     * On vérifie le pseudo et mot de passe rentré dans pseudoField passwordField pour définir si le compte est déjà existant 
     * On enregistre ensuite dans la BD les valeurs renseignés dans 2 champs pseudo et password
     * @param event 
     */
    @FXML
    private void inscription(ActionEvent event) {
            /*      Variables       */
        boolean verifCompteExist = false;
        boolean lenghtPseudo = false; // voir si le compte existe ou pas
        boolean cdtMdp = false; 
        String pseudo;
        String password;

            
            /*      Partie interagissant avec la base de données        */
        if(!pseudoField.getText().trim().isEmpty() || !passwordField.getText().trim().isEmpty()){
            // boucle conditionelle qui verifie que les 2 champs sont pleins
            pseudo = pseudoField.getText();
            password = passwordField.getText();
            try{
                verifCompteExist = verifPseudo(pseudo,this.co);// on regarde si le compte existe
                lenghtPseudo = lenghtPseudo(pseudo); // on regarde la longueur et on renvoie un boolean
                
                if(verifCompteExist == true ||lenghtPseudo == false){
                    // si on a un compte déjà existant
                    pseudoField.setStyle("-fx-border-color: red;");
                    pseudoLabel.setStyle("-fx-text-fill: red;");
                    pseudoLabel.setText("Votre pseudo n'est pas disponible dans ce cas essayez "+suggest(pseudo)+" ou il doit contenir minimum 7 caratères.");
                }else{
                    // pour dire que le compte n'existe pas
                    pseudoField.setStyle("-fx-border-color: green;");
                    pseudoLabel.setText("");
                }
          
                cdtMdp = criterePswd(password);
                if(cdtMdp == false){
                    passwordField.setStyle("-fx-border-color: red;");
                    pswdLabel.setText("Longueur minimum du mot de passe : 7 caractères et avoir minimum une contrainte : @, (, ), £, $");
                    pswdLabel.setStyle("-fx-text-fill: red;");
                }else{
                    passwordField.setStyle("-fx-border-color: green;");
                    pswdLabel.setText("");
                }
                
                if(cdtMdp == true && verifCompteExist == false && lenghtPseudo == true){
                    // si mon mot de passe correspond au critère, si mon compte n'existe pas déjà et si sa longueur est de minimum 7
                   setNewAccount(pseudo, password,co);
                   // init way pour aller vers ailleurs
                }

            }catch(SQLException e){}
        }else{
                pseudoField.setStyle("-fx-border-color: red;");
                passwordField.setStyle("-fx-border-color: red;");
                pswdLabel.setStyle("-fx-text-fill: red;");
                pswdLabel.setText("Champs vide");
            }
        
    }

    /** Fonction permettant le retour en arriere vers la fenêtre de connexion
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void back(ActionEvent event) throws IOException {
        String path = "/multijoueur/connexion/multiCoFXML.fxml";
       
        AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
       
        inscPane.getChildren().setAll(pane);
    }
    
    /** Fonction renvoyant un booléan si le passwordd correspond aux critères de sécurités
     * 
     * @param password
     * @return 
     */
    private static boolean criterePswd(String password){
        boolean cdt1 = false; // longueur du mdp
        boolean cdt2 = false; // complexité
        boolean cplxite = password.contains("@") || password.contains("$") || password.contains("£") || password.contains(")")|| password.contains("(") ;
       
        boolean retour=false;
        
        /*  Code    */
        if(password.length()>=7){cdt1 = true;}     
        if(cplxite == true){cdt2 = true;}else{System.out.println("pb pb pb");}
        if(cdt1 == true && cdt2 == true){retour = true;}
        
        return retour;
    }
    
    /** Fonction renvoyant la longueur du pseudo afin qu'il corresponde aux critères
     * 
     * @param pseudo
     * @return 
     */
    private static boolean lenghtPseudo(String pseudo){
        // cette fonction permet de mettre une longueur minimum à notre pseudo
        boolean lenght = false;
        if(pseudo.length()>=7){
            lenght = true;
        }
        
        return lenght;
    }
    
    
    /** Permet de suggérer un pseudo a l'utilisateur, renvoie un nouveau pseudo
     * 
     * @param pseudo
     * @return
     * @throws SQLException 
     */
    private  String suggest(String pseudo) throws SQLException{
        // fonction qui va proposer un pseudo non utiliser
       int suffixeNum;
       String pseudoModif = pseudo;
       while(verifPseudo( pseudoModif,this.co)== true){
           // tant que verifPseudo == true on va chercher quelque chose qui n'existe pas
           suffixeNum = (int) (Math.random()*1000);
           pseudoModif = pseudo + String.valueOf(suffixeNum);
       }
        return pseudoModif;
    }
    
}
