package correoencriptado;

import correoencriptado.AESSimpleManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.Key;

public class DescifradorAESSimple {

    public static void main(String[] args) {
        final int LONGITUD_BLOQUE = 16; // Expresado en bytes
        final String NOMBRE_FICHERO = "mensaje_cifrado.txt";
        final String PASSWORD = "MeLlamoSpiderman";
        try {
            File file = new File(NOMBRE_FICHERO);
            Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
            //BufferedReader br = new BufferedReader(new FileReader(file));
            //String textoCifrado = br.readLine();
            String textoEnClaro = AESSimpleManager.descifrar("", clave);
            System.out.println("Texto descifrado: " + textoEnClaro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 1.   llamar al método obtenerClave(). Se deja una clave y una longitud fija.
        //      retorna un objeto tipo Key, que se pasa como argumento en el método descifrar()
        //      Cuando funcione, añadimos un joptionpane para que pida en un textfiel una contraseña
        
        // 2.   llamar al método descifrar(). Se pasa el texto cifrado que se ha recibido y la clave.
        //      el retorno se recoje en una variable tipo string, que sería el mensaje descifrado
        
        //      ¿? recibe el string cifrado o recibe un archivo con el texto cifrado¿?
    }
}