package hidato;

/* @author antonio.guilera & marc.blanca */
public class Configuracio{

    private String dificultat; //Facil, Normal, Dificil
    private String tadjacencia; //C:costats,CA:costats+angles
    private char tcelda; //Q:quadrat,H:hexàgon,T:triangle

    public Configuracio(String dif, String tadj, char tcelda){
        this.dificultat = dif;
        this.tadjacencia = tadj;
        this.tcelda = tcelda;
    }


    public Configuracio Hidato_Donat(String tadj, char tcelda, int filas, int columnas) throws Exception {
        String dif = Calcula_Dificultat(filas, columnas);
        return new Configuracio(dif,tadj,tcelda);
    }


    public String Calcula_Dificultat(int filas, int columnas) throws Exception {
        if (filas < 0 || columnas < 0) {
            throw new  Exception("Tamany no valid.");
        }
        if (filas * columnas < 36) {
            return "Facil";
        }
        else if (filas * columnas > 60){
            return "Normal";
        }
        else return "Dificil";
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

    public void Set_Config(String dif, String tipusadj, char celda , Partida p) throws Exception {
        Configuraciocorrecta(dif,tipusadj,celda, p);
    }

    private String Configuraciocorrecta(String dif , String tipusadj, char celda, Partida p) throws Exception {
        if (dif.equals("Facil") || dif.equals("Normal") || dif.equals("Dificil")) {
            if (tipusadj.equals("C") || tipusadj.equals("CA")){
                if (celda == 'Q' || celda == 'H' || celda == 'T'){
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

