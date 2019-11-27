
package BaseDeDonnee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDD implements Parametre{
    
    /*      Fonction qui permet d'ouvrir la base de données     */
    public static Connection openBDD(){
        Connection con = null;
        
     try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            con = DriverManager.getConnection( connectUrl, user, password );
            //System.out.println("Database Connected ");
            //System.out.println(con); 
        }
        catch(Exception e){
                //System.out.println("Erreur");
            }
        return con;
    }
        
    /*      Fonction qui permet de chercher le pseudo dans la base de donnée et rendre un boolean  */
    protected static boolean verifPseudo(String pseudoVerif,Connection co)throws SQLException{
            boolean exist = false;
            String pseudo;
            pseudo = "'"+pseudoVerif+"'";
            //System.out.println("Je regarde si "+pseudoVerif+" existe");
            Statement verif = co.createStatement();
            ResultSet res = verif.executeQuery("SELECT COUNT(*) FROM compte WHERE Pseudo = "+pseudo);
            while(res.next()){
                if(res.getInt("COUNT(*)") >=1){
                    // si le pseudo existe alors on a res = 1.
                    exist = true;
                }
            }
            //System.out.println("Je regarde si il existe ou non"+exist);
            return exist;
    }

    /*      Fonction qui permet de check le mot de passe est renvoie false si le mot de passe ne correspond pas */
    protected static boolean verifPswd(String pseudoVerif,String pswdVerif,Connection co){
        String keyWord = "";
        boolean cdtMdp = false;
        String pseudo = "'"+pseudoVerif+"'";

        try{
            // on commence par creer une requete 
            Statement isCo = co.createStatement();
            String querry ="SELECT `Mot de passe` FROM `compte` WHERE `Pseudo` = "+pseudo;
            ResultSet res = isCo.executeQuery(querry);
            
            //On parcours le resultat
            while(res.next()){
                String test = res.getString("Mot de passe");
                //System.out.println(test);
                // on test si le resultat rendu par res est vrai ou faux.
                if(test.equalsIgnoreCase(pswdVerif)){
                    cdtMdp = true;
                }
            }
            
            }catch(SQLException e){}
        return cdtMdp;
    }
 
    /*      Fonction qui permet de mettre un couple pseudo-password dans la base de donée   */
    protected static void setNewAccount(String setPseudo, String setPswd, Connection co){
        String pseudo = "'"+setPseudo+"'";
        String pswd = "'"+setPswd+"'";
        try{
        Statement create = co.createStatement();
        create.executeUpdate("INSERT INTO compte"+" VALUES ("+pseudo+","+pswd+")");
            System.out.println("done");
        }catch(SQLException e){}
    }

    /*      Fonction qui permet d'inserer une ligne de score*/
    protected static void insertLigneScore(String pseudoInsert, int mvt, int score, Connection co){
                /*  Variables */
        String pseudo = "'"+pseudoInsert+"'";
        boolean firstStrike = true; //c'est notre première partie 
       //String pseudo = "'"+pseudo+"'"; // cela va nous permettre de chercher notre pseudo dans le tableau de score
       
        try{
            
            Statement verifFirstStrike = co.createStatement();
            ResultSet res = verifFirstStrike.executeQuery("SELECT COUNT(*) FROM scoreboard WHERE Pseudo = "+pseudo);
           
            while(res.next()){
                if(res.getInt("COUNT(*)") >=1){
                    // si on a déjà un score on passe à false
                    firstStrike = false;// si COUNT(*)>=1 alors on a déjà joué donc il faut UPDATE le score
                   }
            }
            
                /*  Condition 1 : première partie INSERT */
            if(firstStrike == true){
                Statement insertScore = co.createStatement();
                insertScore.executeUpdate("INSERT INTO scoreboard VALUES ("+pseudo+","+mvt+","+score+")");           
            }
            
                 /*  Condition 2 : meilleur score UPDDATE */
            if(firstStrike == false){
                int scoreBDD = recupScore(pseudo,co); // c'est le score que l'on recupère si on a déjà joué
                System.out.println("Voici score BDD "+ scoreBDD);
                int moveBDD = recupMove(pseudo,co); // c'est le mouvement que l'on recupère si déjà joué
                System.out.println("Voici move BDD " + moveBDD);
                if(scoreBDD < score){
                    /*  Si notre score est supérieur à celui en du tableau alors on UPDATE le score   */
                    Statement updateScore = co.createStatement();
                    updateScore.executeUpdate("UPDATE scoreboard SET Score = '"+score+"' WHERE Pseudo = "+pseudo);
                }
                if(scoreBDD == score){
                    /*  Si notre score est égal à celui dans le tableau des scores on regarde les mouvements  */                  
                    if( mvt < moveBDD){
                        // si mvt bdd superieur mvt
                        System.out.println(moveBDD +" > "+mvt);
                        Statement updateMove = co.createStatement();
                        /*  Si notre mouvement est supérieur à celui du tableau alors UPDATE mouvement*/
                        updateMove.executeUpdate("UPDATE scoreboard SET Mouvement = '"+mvt+"' WHERE Pseudo = "+pseudo);
                    }
                }
                
            }
        }catch(SQLException e){}
    }

    /*  Fonction qui va recuperer et renvoyer le score de notre tableau de score en fonction du pseudo  */
    protected static int recupScore(String pseudoBDD,Connection co){
       int scoreRecup = 0;
       try{
       Statement isCo = co.createStatement();
        String querry ="SELECT `Score` FROM `scoreboard` WHERE `Pseudo` = "+pseudoBDD;
        ResultSet res = isCo.executeQuery(querry);
        while(res.next()){
            scoreRecup = res.getInt("Score");
            System.out.println("/n Voici score recup "+scoreRecup);
        }
       }catch(SQLException e){}
        return scoreRecup;
    }
    
    /*  Fonction qui va chercher et renvoyer le mouvement de notre tableau de score en fonction du pseudo   */
    protected static int recupMove(String pseudoBDD, Connection co){
       int moveRecup = 0;
        try{
            Statement isCo = co.createStatement();
            String querry ="SELECT `Mouvement` FROM `scoreboard` WHERE `Pseudo` = "+pseudoBDD;
            ResultSet res = isCo.executeQuery(querry);
            while(res.next()){
                moveRecup = res.getInt("Mouvement");
                System.out.println("/n Voici mvt " + moveRecup);
        }

       }catch(SQLException e){} 
        return moveRecup;
    }



}
