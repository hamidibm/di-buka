/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Signup.DataAkun;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import expo.GantiHalaman;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author LENOVO idepad GAMING
 */
public class LoginController implements Initializable {
    
    GantiHalaman h = new GantiHalaman();
    DataAkun d = new DataAkun();

    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField tfPass;
    @FXML
    private Label labelUser;
    @FXML
    private Label labelPass;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ambilDataAkun();
    }    

    @FXML
    private void buttonLogin(ActionEvent event) {
        
        if(tfUser.getText().equals(d.getUser()) && tfPass.getText().equals(d.getPass())){
            h.pindahHalaman(event, "/Home/Home.fxml");
        }
        else if (tfUser.getText().isEmpty() || tfPass.getText().isEmpty()){
            if (tfUser.getText().isEmpty()){
                labelUser.setText("Isi Username");
            }
            else{
                labelUser.setText("");
            }
            
            if (tfPass.getText().isEmpty()){
                labelPass.setText("Isi password");
            }
            else{
                labelPass.setText("");
            }
        }
        else{
            labelUser.setText("");
            labelPass.setText("");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Isi ulang kembali");
            a.setContentText("Username dan Password tidak sesuai !");
            a.showAndWait();
        }
    }

    @FXML
    private void hlDaftar(ActionEvent event) {
        h.pindahHalaman(event, "/Signup/Signup.fxml");
    }
    
    void ambilDataAkun(){
        XStream xstream = new XStream(new StaxDriver());
        FileInputStream xmlKu = null;
        
        try{
            xmlKu = new FileInputStream("xmlDataAkun.xml");
            int kodeKu;
            String pesanKu = "";
            
            while((kodeKu = xmlKu.read()) != -1){
                pesanKu += (char) kodeKu;
            }
            
            d = (DataAkun) xstream.fromXML(pesanKu);
            xmlKu.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}