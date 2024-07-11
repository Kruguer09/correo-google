package correoencriptado;

import correoencriptado.AESSimpleManager;
import java.io.PrintWriter;
import java.security.Key;

public class CifradorAESSimple {

    public static void main(String[] args) {
        //final int LONGITUD_BLOQUE = 16; // Expresado en bytes

        final String NOMBRE_FICHERO = "mensaje_cifrado.txt";
        //final String PASSWORD = "MeLlamoSpiderman";
        //final String TEXTO_EN_CLARO = "La clave secreta de la caja fuerte es 3842873110";
        try {
            Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
            //String textoEnClaro = TEXTO_EN_CLARO;
            String textoCifrado = AESSimpleManager.cifrar("texto sin cifrar", clave);
            PrintWriter pw = new PrintWriter(NOMBRE_FICHERO);
//            pw.write(textoCifrado);
//            pw.close();
            System.out.println("El mensaje se ha cifrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 1.   llamar al método obtenerClave(). Se deja una clave y una longitud fija.
        //      retorna un objeto tipo Key, que se pasa como argumento en el método cifrar()
        //      Cuando funcione, añadimos un joptionpane para que pida en un textfiel una contraseña
        
        // 2.   llamar al método cifrar(). Se pasa el texto que se quiere enviar y la clave.
        //      el retorno se recoje en una variable tipo string, que sería el mensaje cifrado
        
        //      ¿? Se envía el string cifrado o se envía un archivo con el texto cifrado¿?
    }
}
