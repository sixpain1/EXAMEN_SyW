package examen_syw;

import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author raulm
 */
public class PERSONA1 implements Serializable {

    public static void main(String[] args) {
        //Variables para serializar
        File miFile;
        Vector vector = new Vector();
        ObjectOutputStream oos;
        ObjectInputStream ois;

        RSA objrsa;

        //Crea el archivo
        miFile = new File("AWA.txt");

        //Variables necesarias
        BigInteger n, q, p;
        BigInteger totient;
        BigInteger E, d;
        String mensaje;

        //Indica las instrucciones
        JOptionPane.showMessageDialog(null, "El numero primo no puede ser mayor a 100 ni menor a 2");

        //Preguntamos el tamaño del numero primo, y lo guardamos en una variable entera
        int TAMPRIMO = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cual es el tamaño del primo?"));
        //Preguntamos el mensaje a cifrar
        mensaje = JOptionPane.showInputDialog(null, "Ingresa tu mensaje");

        //Inicia la logica de los numeros primos
        if (TAMPRIMO <= 100 && TAMPRIMO >= 2) {
            //p
            p = new BigInteger(TAMPRIMO, 10, new Random());
            //q
            do {
                q = new BigInteger(TAMPRIMO, 10, new Random());
            } while (q.compareTo(p) == 0);
            //phi
            n = p.multiply(q);
            totient = p.subtract(BigInteger.valueOf(1));
            totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
            //E
            do {
                E = new BigInteger(2 * TAMPRIMO, new Random());
            } while ((E.compareTo(totient) != -1)
                    || (E.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
            //d
            d = E.modInverse(totient);

            //////////
            //Implementamos el codigo de encriptado 
            int i;
            byte[] temp = new byte[1];
            byte[] digitos = mensaje.getBytes();
            BigInteger[] bigdigitos = new BigInteger[digitos.length];

            //lo primero que debemos hacer es correr el tamaño de bigdigitos
            for (i = 0; i < bigdigitos.length; i++) {
                temp[0] = digitos[i];
                bigdigitos[i] = new BigInteger(temp);
            }

            //vamos a cifrar
            BigInteger[] encriptado = new BigInteger[bigdigitos.length];

            for (i = 0; i < bigdigitos.length; i++) {
                encriptado[i] = bigdigitos[i].modPow(E, n);
            }//Obtenemos el mensaje encriptado 
            //////////

            //Muestra todas las variables del numero primo
            JOptionPane.showMessageDialog(null, "p: " + p + "\n"
                    + "q: " + q + "\n"
                    + "n: " + n + "\n"
                    + "phi(n) " + totient + "\n"
                    + "e: " + E + "\n"
                    + "d: " + d + "\n"
                    + "Tu mensaje: " + mensaje + "\n"
                    + "Tu encriptacion: " + encriptado);

            //Implementamos la serializacion en un obj 
            objrsa = new RSA(TAMPRIMO, p, q, n, totient, E, d, encriptado);
            vector.add(objrsa);

            try {
                oos = new ObjectOutputStream(new FileOutputStream(miFile));
                oos.writeObject(vector);
                oos.close();
            } catch (IOException e) {
            }
            try {
                ois = new ObjectInputStream(new FileInputStream(miFile));
                vector = (Vector) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PERSONA1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {

            } catch (ArrayIndexOutOfBoundsException ae) {
                System.out.println(ae.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Los datos recibidos son " + "\n"
                    + "p: " + p + "\n"
                    + "q: " + q + "\n"
                    + "n: " + n + "\n "
                    + "Mensaje Encriptado: " + encriptado);

            BigInteger[] desencriptar = new BigInteger[encriptado.length];

            for (i = 0; i < desencriptar.length; i++) {
                desencriptar[i] = encriptado[i].modPow(d, n);
            }

            char[] charArray = new char[desencriptar.length];

            for (i = 0; i < charArray.length; i++) {
                charArray[i] = (char) (desencriptar[i].intValue());
            }
            
            String des = new String(charArray);
            JOptionPane.showMessageDialog(null,"El mensaje descifrado es: " + des);
            
            

            //Es parte del if para la validacion del tamaño de numeros primos    
        } else {
            JOptionPane.showMessageDialog(null, "El numero primo no puede ser mayor a 100 ni menor a 2");
        }

    }

}
