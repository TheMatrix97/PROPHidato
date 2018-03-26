package hidato;

/* @author antonio.guilera & marc.blanca */
public class Configuracio{

    private String dificultat; //Facil, Normal, Dificil
    private String tadjacencia; //C:costats,CA:costats+angles
    private Character tcelda; //Q:quadrat,H:hexàgon,T:triangle

    public Configuracio(String dif, String tadj, Character tcelda){
        this.dificultat = dif;
        this.tadjacencia = tadj;
        this.tcelda = tcelda;
    }

    public String getDificultat(){
        return dificultat;
    }

    public String getAdjacencia(){
        return tadjacencia; //C:costats,CA:costats+angles
    }

    public char getcell(){
        return tcelda; //Q:quadrat,H:hexàgon,T:triangle
    }



    public Configuracio Set_Config(String dif, String tadj, Character celda ,Partida p){
        Configuracio_correcta(dif,tadj,celda,p);
    }

    private String Configuracio_correcta(String dif,String tipusadj, Character celda, Partida p){
        if (dif.equals("Facil") || dif.equals("Normal") || dif.equals("Dificil")) {
            if (tipusadj.equals("C") || tipusadj.equals("CA")){
                if (celda.equals('Q') || celda.equals('H') || celda.equals('T')){
                    p.Configuracio.difcultat = dif;
                    p.Configuracio.tadjacencia = tipusadj;
                    p.Configuracio.tcelda = celda;
                }
                else throw new  Exception("Tipus de celda incorrecte.");
            }
            else throw new  Exception("Tipus d'Adjacencia incorrecte.");
        }
        else throw new  Exception("Dificultat incorrecte.");
    }
}

