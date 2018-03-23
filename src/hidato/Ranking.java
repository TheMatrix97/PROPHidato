/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.util.Vector;

/**
 *
 * @author marc.catrisse
 */
public class Ranking{
    private int size;
    private Vector<Record> records;
    Ranking(){
        this.size = 10;
        this.records = new Vector<Record>(this.size);
    }
    public Vector<Record> getRanking(){
        return records.clone();
    }
    public void addRecord(Record r){
    
    }
}