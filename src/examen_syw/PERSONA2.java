package examen_syw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author raulm
 */

public class PERSONA2 implements Serializable{
    
      public static void main(String[] args) {
          
                  
          
      FileInputStream entrada= null;
      RSA rsa;
      try  {
          entrada= new FileInputStream("AWA.txt");
          ObjectInputStream ois= new ObjectInputStream(entrada);
          rsa = (RSA)ois.readObject();
          rsa.mostrarSerializado();
            
         BigInteger encriptado = rsa.encriptado();
        
          
        BigInteger[] desencriptar = new BigInteger[encriptado.length];

        for(int i = 0; i<desencriptar.length; i++){
            desencriptar[i] = encriptado[i].modPow(d, n);
        }

        char[] charArray = new char[desencriptar.length];

        for(int i = 0; i<charArray.length; i++){
            charArray[i] = (char)(desencriptar[i].intValue());
        }

      }catch(FileNotFoundException ex){
          ex.printStackTrace(); 
      }catch(IOException ex){
          ex.printStackTrace();
      }catch(ClassNotFoundException ex){
          ex.printStackTrace();
      }      
    }
    
    
}
