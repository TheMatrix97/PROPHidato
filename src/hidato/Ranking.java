/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import sun.awt.ConstrainableGraphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;


/**
 *
 * @author marc.catrisse
 */
public class Ranking implements Serializable{
    private int sizemax;
    private ArrayList<Record> records; //lista que contiene los 10 mejores
    private Record peorRecord;
    private Configuracio conf;
    public Ranking(Configuracio conf){
        this.sizemax = 10;
        this.records = new ArrayList<>();
        this.peorRecord = null;
        this.conf = conf;
    }
    public ArrayList<Record> getRanking(){
        ordenar_para_mostrar();
        return records;
    }

    public void setSizemax(int sizemax) { //por si queremos modificar size max
        this.sizemax = sizemax;
    }

    public boolean addRecord(Record r){
        if(records.size() < sizemax){
            this.records.add(r);

        }else{ //cual se va fuera?
            Time temps = r.getTime();
            if(temps.get_time_millis() >= peorRecord.getTime().get_time_millis()) return false;
            records.remove(peorRecord);
            records.add(r);
        }
        actualizar_peor_tiempo();
        return true;
    }
    public Configuracio getConf(){
        return this.getConf();
    }

    private void actualizar_peor_tiempo(){
        for(Record aux : records){
            if (peorRecord == null || aux.getTime().get_time_millis() > peorRecord.getTime().get_time_millis()){
                peorRecord = aux;
            }
        }
    }

    private void ordenar_para_mostrar(){
        records.sort(new RecordComparador());
    }

    //class sexy para comparar, solo la usaremos en el ranking
    private class RecordComparador implements Comparator<Record>{ //Comparator<T> es una interface
        public int compare(Record r1, Record r2){
            long dif = r1.getTime().get_time_millis() - r2.getTime().get_time_millis();
            if(dif > 0) return 1;
            else if(dif == 0) return 0;
            else return -1;
        }
    }
}