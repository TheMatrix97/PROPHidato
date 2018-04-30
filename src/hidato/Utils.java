package hidato;

import java.util.AbstractMap;

/**
 *
 * @author Lluis.Marquès & Marc.Catrisse
 */


//Class auxiliar per crear Excepcions noves i funcions auxiliars
public class Utils {
    static class ExceptionHidatoSolucionat extends Exception{
        ExceptionHidatoSolucionat(String s){
            super(s);
        }
    }

    static class ExceptionHidatoNoSol extends Exception{
        ExceptionHidatoNoSol(String s){
            super(s);
        }
    }
    public static class ExceptionJugadaNoValida extends Exception{
        ExceptionJugadaNoValida(){
            super();
        }
    }
    static class ExceptionPosicioNoValida extends Exception {
        ExceptionPosicioNoValida() {
            super();
        }
    }
    public static class ExceptionTaulerResolt extends Exception{
        ExceptionTaulerResolt(){
            super();
        }
    }
    public static AbstractMap.SimpleEntry<Integer, Integer> BuscarN(Celda[][] c, int n) throws Exception {
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                if(c[i][j].isPrefijada() && c[i][j].getValor() == n) return new AbstractMap.SimpleEntry<>(i, j);
            }
        }
        throw new Exception("Celda prefixada not found");
    }
    static class ExceptionTimeOut extends Exception{
        ExceptionTimeOut(String s){
            super(s);
        }
    }

    static class ExceptionCeldaNotFound extends Exception {
        ExceptionCeldaNotFound() { super();}
    }
}
