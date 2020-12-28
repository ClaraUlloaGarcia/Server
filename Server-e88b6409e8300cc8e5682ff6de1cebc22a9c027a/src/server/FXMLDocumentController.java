/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 *
 * @author ClaraU
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private PasswordField passwords;
    
    
    private Server server = new Server();
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        final String PASSWORD = "1234";
        if(passwords.getText().equals(PASSWORD)) {
            server.shutdownServer();
            System.exit(0);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Wrong Password."); 
            alert.show(); 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        server.start();
      
    }    
    
}
