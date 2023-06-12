/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Penjualan;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author LENOVO idepad GAMING
 */
public class DataBisnis {

    private LocalDate tanggal;
    private SimpleStringProperty status;
    private SimpleStringProperty jumlah;
    private SimpleStringProperty hutang;
    private SimpleStringProperty tempo;
    
    private int banyakData;
    
    public DataBisnis(LocalDate tanggal, String status, String jumlah, String hutang, String tempo){
        this.tanggal = tanggal;
        this.status = new SimpleStringProperty(status);
        this.jumlah = new SimpleStringProperty(jumlah);
        this.hutang = new SimpleStringProperty(hutang);
        this.tempo = new SimpleStringProperty(tempo);
    }

    /**
     * @return the tanggal
     */
    public LocalDate getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(SimpleStringProperty status) {
        this.status = status;
    }

    /**
     * @return the jumlah
     */
    public String getJumlah() {
        return jumlah.get();
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(SimpleStringProperty jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the hutang
     */
    public String getHutang() {
        return hutang.get();
    }

    /**
     * @param hutang the hutang to set
     */
    public void setHutang(SimpleStringProperty hutang) {
        this.hutang = hutang;
    }

    /**
     * @return the tempo
     */
    public String getTempo() {
        return tempo.get();
    }

    /**
     * @param tempo the tempo to set
     */
    public void setTempo(SimpleStringProperty tempo) {
        this.tempo = tempo;
    }
    
    /**
     * @return the banyakData
     */
    public int getBanyakData() {
        return banyakData;
    }

    /**
     * @param banyakData the banyakData to set
     */
    public void setBanyakData(int banyakData) {
        this.banyakData = banyakData;
    }
}
