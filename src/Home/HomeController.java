/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Penjualan.DataAkhir;
import Penjualan.DataBisnis;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import expo.FormatUang;
import expo.GantiHalaman;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author LENOVO idepad GAMING
 */
public class HomeController implements Initializable {
    
    ObservableList<DataBisnis> db = FXCollections.observableArrayList();
    DataAkhir da = new DataAkhir();
    GantiHalaman h = new GantiHalaman();
    FormatUang fu = new FormatUang();
    boolean status1 = true;
    boolean status2 = true;
    
    @FXML
    private Button menuPenjualan;
    @FXML
    private Button menuKeluar;
    
    @FXML
    private LineChart lineChart;
    
    XYChart.Series<String, Integer> pengeluaran = new XYChart.Series<>();
    XYChart.Series<String, Integer> pendapatan = new XYChart.Series<>();
    
    
    @FXML
    private Label labelAnggaran;
    @FXML
    private Label labelPendapatan;
    @FXML
    private Label labelPengeluaran;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ambilDataAkhir();
        ambilDataBisnis();
        labelAnggaran.setText(fu.formatUang(da.getAnggaran()));
        labelPendapatan.setText(fu.formatUang(da.getPendapatan()));
        labelPengeluaran.setText(fu.formatUang(da.getPengeluaran()));
    }    
    
    @FXML
    private void rbPendapatan(ActionEvent event) {
        
        if(status1 == true){
            pendapatan.getData().add(new XYChart.Data<>("Jan", 194));
            pendapatan.getData().add(new XYChart.Data<>("Feb", 53));
            pendapatan.getData().add(new XYChart.Data<>("Mar", 272));
            pendapatan.getData().add(new XYChart.Data<>("Apr", 119));
            pendapatan.getData().add(new XYChart.Data<>("May", 184));
            pendapatan.getData().add(new XYChart.Data<>("Jun", 316));
            pendapatan.getData().add(new XYChart.Data<>("Jul", 270));
            pendapatan.getData().add(new XYChart.Data<>("Aug", 437));
            pendapatan.getData().add(new XYChart.Data<>("Sep", 221));
            pendapatan.getData().add(new XYChart.Data<>("Oct", 621));
            pendapatan.getData().add(new XYChart.Data<>("Nov", 725));
            pendapatan.getData().add(new XYChart.Data<>("Dec", 511));
            
            pendapatan.setName("Pendapatan");
            
            lineChart.getData().add(pendapatan);
            status1 = false;
        }
        else{
            pendapatan.getData().clear();
            status1 = !status1;
        }
    }

    @FXML
    private void rbPengeluaran(ActionEvent event) {
        
        if(status2 == true){
            pengeluaran.getData().add(new XYChart.Data<>("Jan", 100));
            pengeluaran.getData().add(new XYChart.Data<>("Feb", 80));
            pengeluaran.getData().add(new XYChart.Data<>("Mar", 146));
            pengeluaran.getData().add(new XYChart.Data<>("Apr", 166));
            pengeluaran.getData().add(new XYChart.Data<>("May", 260));
            pengeluaran.getData().add(new XYChart.Data<>("Jun", 310));
            pengeluaran.getData().add(new XYChart.Data<>("Jul", 424));
            pengeluaran.getData().add(new XYChart.Data<>("Aug", 120));
            pengeluaran.getData().add(new XYChart.Data<>("Sep", 481));
            pengeluaran.getData().add(new XYChart.Data<>("Oct", 491));
            pengeluaran.getData().add(new XYChart.Data<>("Nov", 129));
            pengeluaran.getData().add(new XYChart.Data<>("Dec", 214));
            
            pengeluaran.setName("Pengeluaran");

            lineChart.getData().add(pengeluaran); 
            status2 = false;
        }
        else{
            pengeluaran.getData().clear();
            status2 = !status2;
        }
               
    }

    @FXML
    private void menuPenjualan(ActionEvent event) {
        h.pindahHalaman(event, "/Penjualan/Penjualan.fxml");
    }

    @FXML
    private void menuKeluar(ActionEvent event) {
        
        Alert alertClose = new Alert(Alert.AlertType.CONFIRMATION);
        alertClose.setContentText("Anda akan keluar dari akun ini");
        
        alertClose.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                    h.pindahHalaman(event, "/Login/Login.fxml");
            }
        });
       
    }
    
    void ambilDataBisnis(){
        XStream xstream = new XStream(new StaxDriver());
        FileInputStream xmlKu = null;
        
        try{
            xmlKu = new FileInputStream("xmlDataBisnis.xml");
            int kodeKu;
            String pesanKu = "";
            
            while((kodeKu = xmlKu.read()) != -1){
                pesanKu += (char) kodeKu;
            }
            
            db = (ObservableList<DataBisnis>) xstream.fromXML(pesanKu);
            xmlKu.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    void ambilDataAkhir(){
        XStream xstream = new XStream(new StaxDriver());
        FileInputStream xmlKu = null;
        
        try{
            xmlKu = new FileInputStream("xmlDataAkhir.xml");
            int kodeKu;
            String pesanKu = "";
            
            while((kodeKu = xmlKu.read()) != -1){
                pesanKu += (char) kodeKu;
            }
            
            da = (DataAkhir) xstream.fromXML(pesanKu);
            xmlKu.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}