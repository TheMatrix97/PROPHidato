/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.util.List;

import com.sun.prism.impl.Disposer.Record;



/**
 *
 * @author marc.catrisse
 */
public class Ranking{
    private int sizemax;
    private List<Record> records; //lista que contiene los 10 mejores
    private Record peorRecord;
    Ranking(){
        this.sizemax = 10;
        this.records = new List<Record>();
    }
    public List<Record> getRanking(){
        return records.clone();
    }
    public bool addRecord(Record r){

        if(records.size() < sizemax){
            

        }else{ //cual se va fuera?
            Time temps = r.getTime();
            if(temps.get_time_millis() >= peorRecord.getTime().get_time_millis()) return false;
            records.remove(peorRecord);
            records.add(r);
            actualizar_peor_tiempo();
        }
    }
    private void actualizar_peor_tiempo(){
        
    }
}