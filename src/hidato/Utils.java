package hidato;

public class Utils {
    public static class ExceptionHidatoSolucionat extends Exception{
        ExceptionHidatoSolucionat(String s){
            super(s);
        }
    }

    public static class ExceptionHidatoNoSol extends Exception{
        ExceptionHidatoNoSol(String s){
            super(s);
        }
    }
    public static class ExceptionJugadaNoValida extends Exception{
        ExceptionJugadaNoValida(){
            super();
        }
    }
    public static class ExceptionPosicioNoValida extends Exception{
        ExceptionPosicioNoValida(){
            super();
        }
    }


}
