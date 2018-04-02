package hidato;

import java.io.*;

public abstract class LectorTxt{
    public static String llegirFile(String name) throws IOException {
        String cadena;
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath+"/BaseDadesHidatos/"+name+".txt");
        BufferedReader b = new BufferedReader(f);
        StringBuilder res = new StringBuilder();
        while((cadena = b.readLine()) != null){
            res.append(cadena);
            res.append('\n');
        }
        return res.toString();
    }


}
