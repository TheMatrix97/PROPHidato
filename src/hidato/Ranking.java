/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;


/**
 *
 * @author marc.catrisse
 */
public class Ranking implements Serializable{
    private int sizemax;
    private ArrayList<Record> records; //lista que conté els 10 millors
    private Record peorRecord;
    private Configuracio conf;

    //Creadora. Genera un ranking the fins a 10 jugadors, donada una configuració. Hi ha un ranking diferent per configuració que s'hagi jugat
    public Ranking(Configuracio conf){
        this.sizemax = 10;
        this.records = new ArrayList<>();
        this.peorRecord = null;
        this.conf = conf;
    }
    //Retorna un ranking donat
    public ArrayList<Record> getRanking(){
        ordenar_para_mostrar();
        return records;
    }

    //Quan es completa un tauler es guarda el record del jugador.
    public boolean addRecord(Record r){
        if(records.size() < sizemax){
            this.records.add(r);

        }else{
            Time temps = r.getTime();
            if(temps.get_time_millis() >= peorRecord.getTime().get_time_millis()) return false;
            records.remove(peorRecord);
            records.add(r);
        }
        actualizar_peor_tiempo();
        return true;
    }

    //Retorna la configuracio actual
    public Configuracio getConf(){
        return this.conf;
    }

    //Calcula quin es el pitjor temps dels 10 del ranking per eliminarlo en cas de que hi hagi un nou record entre els 10 millors (?)
    private void actualizar_peor_tiempo(){
        for(Record aux : records){
            if (peorRecord == null || aux.getTime().get_time_millis() > peorRecord.getTime().get_time_millis()){
                peorRecord = aux;
            }
        }
    }

    //Ordena el ranking a mostrar en ordre creixent (millor temps primer, pitjor temps últim)
    private void ordenar_para_mostrar(){
        records.sort(new RecordComparador());
    }

    //Implementació del algorisme per a ordenar els rankings
    private class RecordComparador implements Comparator<Record>{ //Comparator<T> es una interface
        public int compare(Record r1, Record r2){
            long dif = r1.getTime().get_time_millis() - r2.getTime().get_time_millis();
            if(dif > 0) return 1;
            else if(dif == 0) return 0;
            else return -1;
        }
    }
    //retorna l'ArrayList de records del ranking
    public ArrayList<Record> getRecords(){
        return this.records;
    }
}