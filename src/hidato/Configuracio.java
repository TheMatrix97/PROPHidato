package hidato;

/* @author antonio.guilera & marc.blanca */
public class Configuracio{

    private String dificultat; //Facil, Normal, Dificil
    private String tadjacencia; //C:costats,CA:costats+angles
    private char tcelda //Q:quadrat,H:hexàgon,T:triangle

    public Configuracio(String dif, boolean tadj, char tcelda){
        this.dificultat = dif;
        this.tadjacencia = tadj;
        this.tcelda = tcelda
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



    public Configuracio Set_Config(String dif, String tipusadj, char celda ,Partida p){
        Configuracio_correcta(dif,tipusadj,celda, Partida p);
    }

    private String Configuraciocorrecta(string dif,boolean tipusadj, char celda, Partida p){
        if (dif == "Facil" or dif =="Normal" or dif =="Dificil") {
            if (tipusadj == "C" or tipusadj == "CA"){
                if (celda == "Q" or celda == "H" or celda == "T"){
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

