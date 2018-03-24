package hidato;

/* @author lluis.marques */
public class Configuracio{
    
    private String dificultat; //Facil, Normal, Dificil
    private boolean tadjacencia; //0 = defecte/costats, 1 = vertex+costat/diagonals
    
    public Configuracio(String dif, boolean tadj){
        this.dificultat = dif;
        this.tadjacencia = tadj;
    }
    
    public String getDificultat(){
        return dificultat;
    }
    
    public boolean getAdjacencia(){
        return tadjacencia; //0 = defecte/costats, 1 = vertex+costat/diagonals
    }
    //NO HI HAN SETTERS, no voldrem modificar la config
}
