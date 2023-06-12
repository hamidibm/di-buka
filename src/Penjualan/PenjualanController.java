/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Penjualan;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import expo.FormatUang;
import expo.GantiHalaman;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO idepad GAMING
 */
public class PenjualanController implements Initializable {

    GantiHalaman h = new GantiHalaman();
    DataAkhir da = new DataAkhir();
    FormatUang fu = new FormatUang();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    
    ObservableList<DataBisnis> db = FXCollections.observableArrayList();
    int a = 0;
    int b = 0;
    
    @FXML
    private Button menuHome;
    @FXML
    private Button menuKeluar;
    @FXML
    private DatePicker dpTanggal;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private TextField tfJumlah;
    @FXML
    private ComboBox<String> cbHutang;
    @FXML
    private DatePicker dpTempo;
    
    @FXML private TableView<DataBisnis> tableView;
    
    ObservableList<String> listStatus = FXCollections.observableArrayList("Pendapatan", "Pengeluaran");
    ObservableList<String> listHutang = FXCollections.observableArrayList("Ya", "Tidak");
    
    public ObservableList<DataBisnis> list = FXCollections.observableArrayList( 
    );
    @FXML
    private TableColumn<DataBisnis, DatePicker> tableTanggal;
    @FXML
    private TableColumn<DataBisnis, ComboBox> tableStatus;
    @FXML
    private TableColumn<DataBisnis, Integer> tableJumlah;
    @FXML
    private TableColumn<DataBisnis, ComboBox> tableHutang;
    @FXML
    private TableColumn<DataBisnis, DatePicker> tableTempo;
    
    @FXML
    private Label labelWarning;
    @FXML
    private TextField tfAnggaran;
    @FXML
    private Button buttonSimpan1;
    @FXML
    private Button buttonPrint;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ambilDataBisnis();
        ambilDataAkhir();
        
        cbStatus.setItems(listStatus);
        cbHutang.setItems(listHutang);
        
        tableTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tableHutang.setCellValueFactory(new PropertyValueFactory<>("hutang"));
        tableTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        
        tableView.setItems(db);
    }    

    @FXML
    private void menuHome(ActionEvent event) {
        h.pindahHalaman(event, "/Home/Home.fxml");
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

    @FXML
    private void buttonSimpan(ActionEvent event) {
        try{
            Integer.parseInt(tfJumlah.getText());
            labelWarning.setText("");
        }
        
        catch(RuntimeException e){
            alert.setTitle("Error");
            alert.setHeaderText("Isi kembali");
            alert.setContentText("Inputkan data dalam bentuk angka !");
            alert.showAndWait();
            
            labelWarning.setText("Inputkan jumlah dalam bentuk angka !");
        }
        
        if(dpTanggal.getValue() != null && tfJumlah.getText() != null && cbHutang.getValue() != null){
            int status = 0;
            if("Ya".equals(cbHutang.getValue()) && dpTempo.getValue() != null || "Tidak".equals(cbHutang.getValue())){
                status = 1;
            }
            if("Pengeluaran".equals(cbStatus.getSelectionModel().getSelectedItem()) && status == 1){
                if(da.getAnggaran() < Integer.parseInt(tfJumlah.getText())){
                    labelWarning.setText("Anggaran tidak cukup ! Sisa anggaran saat ini adalah "+fu.formatUang(da.getAnggaran()));
                }
                else{
                    da.setAnggaran(da.getAnggaran() - Integer.parseInt(tfJumlah.getText()));
                    labelWarning.setText("");
                    da.setPengeluaran(da.getPengeluaran() + Integer.parseInt(tfJumlah.getText()));
                    tampilkanTabel();
                    simpanDataAkhir();
                    
                    dpTanggal.setPromptText("Tanggal");
                    cbStatus.setValue("Status");
                    tfJumlah.clear();
                    cbHutang.setValue("Hutang");
                    dpTempo.setPromptText("Jatuh Tempo");
                }
            }
            else if("Pendapatan".equals(cbStatus.getSelectionModel().getSelectedItem()) && status == 1){
                da.setPendapatan(da.getPendapatan() + Integer.parseInt(tfJumlah.getText()));
                tampilkanTabel();
                simpanDataAkhir();
                
                dpTanggal.setPromptText("Tanggal");
                cbStatus.setValue("Status");
                tfJumlah.clear();
                cbHutang.setValue("Hutang");
                dpTempo.setPromptText("Jatuh Tempo");
            }   
        }
    }
    
    @FXML
    private void buttonSimpan1(ActionEvent event) {
        try{
            Integer.parseInt(tfAnggaran.getText());
            labelWarning.setText("");
            
            
        }
        
        catch(RuntimeException e){
            alert.setTitle("Error");
            alert.setHeaderText("Isi kembali !");
            alert.setContentText("Inputkan anggaran dengan benar");
            alert.showAndWait();
            
            labelWarning.setText("Inputkan anggaran dalam bentuk angka !");
        }
        
        da.setAnggaran(da.getAnggaran() + Integer.parseInt(tfAnggaran.getText()));
        simpanDataAkhir();
        tfAnggaran.clear();
    }
    
    @FXML
    private void buttonPrint(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Pastikan kabel terpasang");
        a.setTitle("Error");
        a.setHeaderText("Printer Offline");
        a.showAndWait();
    }

    void simpanDataBisnis(){
        XStream xstream = new XStream(new StaxDriver());
        String dataKu = xstream.toXML(db);
        FileOutputStream xmlKu = null;
        
        try{
            xmlKu = new FileOutputStream("xmlDataBisnis.xml");
            byte[] byteKu = dataKu.getBytes("UTF-8");
            xmlKu.write(byteKu);
            xmlKu.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
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
    
    void simpanDataAkhir(){
        XStream xstream = new XStream(new StaxDriver());
        String dataKu = xstream.toXML(da);
        FileOutputStream xmlKu = null;
        
        try{
            xmlKu = new FileOutputStream("xmlDataAkhir.xml");
            byte[] byteKu = dataKu.getBytes("UTF-8");
            xmlKu.write(byteKu);
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
    
    void tampilkanTabel(){
        String tmpTempo = cbHutang.getSelectionModel().getSelectedItem();
        
        if ("Tidak".equals(tmpTempo)){
            tmpTempo = "-";
        }
        else{
            tmpTempo = dpTempo.getValue().toString();
        }
        
        DataBisnis d = new DataBisnis(dpTanggal.getValue(), 
                                      cbStatus.getSelectionModel().getSelectedItem(), 
                                      fu.formatUang(Integer.parseInt(tfJumlah.getText())),
                                      cbHutang.getSelectionModel().getSelectedItem(),
                                      tmpTempo);
        
        labelWarning.setText("");
        tableView.getItems().add(d);
        simpanDataBisnis();
    } 
}