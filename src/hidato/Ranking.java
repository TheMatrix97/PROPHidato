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
    //ERROR A REVISAR
    private void actualizar_peor_tiempo(){
        peorRecord = records[0].clone();
        for (int i = 1; i < records.size();i++){
            if (records[i].getTime().get_time_millis() > peorRecord.getTime().get_time_millis()){
                peorRecord = records[i];
            } 
        }
    }
    private void ordenar_para_mostrar(){
        new List<Record> res = records.clone();
        mergesort (res,0,res.size());
    }


    private void mergesort (List <Record> res, int l, int r){
        if (l < r) {
            int m = (l + r)/2;
            mergesort(res,l,m);
            mergesort(res,l,m);
            merge(res, l,m, r);
        }
    }

    private void merge(List <Record> res, int l, int m, int r){
       List <Record> b(r-l+1);
       int i = l, j = m +1, k = 0;
       while (i <= m and j <= r){
           if (v[i].getTime().get_time_millis() <= res[j].getTime().get_time_millis()) b[k++] = v[i++]
           else b[k++] = res[j++];
       }
       while (i <= m) b[k++] = res[i++];
       while (j <= r) b[k++] = res[j++];
       for(k = 0; k < r - l; ++k) res[l+k] = b[k]
    }
}