package correoencriptado;

//import criptografia.RegistradorCredenciales;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import javax.crypto.SecretKey;


public class AESSimpleManager {
    
    //String pass = RegistradorCredenciales.password;
    /*public static Key obtenerClave(String password, int longitud) {
        // La clave debe tener una longitud de 16, 24 o 32 bytes
        Key clave = new SecretKeySpec(password.getBytes(), 0, longitud, "AES");
        return clave;
    }*/
    
    public static SecretKey obtenerClave(String password, int longitud) {
    // La clave debe tener una longitud de 16, 24 o 32 bytes
    if (longitud != 16 && longitud != 24 && longitud != 32) {
        throw new IllegalArgumentException("La longitud de la clave debe ser 16, 24 o 32 bytes.");
    }

    byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
    if (keyBytes.length < longitud) {
        throw new IllegalArgumentException("La contraseña es demasiado corta para generar una clave de " + longitud + " bytes.");
    }

    SecretKey clave = new SecretKeySpec(Arrays.copyOfRange(keyBytes, 0, longitud), "AES");
    return clave;
}

    public static String cifrar(String textoEnClaro, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(textoEnClaro.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String descifrar(String textoCifrado, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(plainText);
    }
    
    
    public static void main(String[] args) {
        
    }
}


