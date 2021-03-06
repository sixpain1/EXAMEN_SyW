/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen_syw;

/**
 *
 * @author raulm
 */

import java.math.BigInteger;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;


public class RSA implements Serializable{

    //variables
    int TAMPRIMO;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger E, d;
    BigInteger[] encriptado;
    String mensaje;
    //constructor

    public RSA(int TAMPRIMO, BigInteger n, BigInteger q, BigInteger p, 
            BigInteger totient, BigInteger E, BigInteger d, BigInteger[] encriptado) {
        this.TAMPRIMO = TAMPRIMO;
        this.n = n;
        this.q = q;
        this.p = p;
        this.totient = totient;
        this.E = E;
        this.d = d;
        this.encriptado=encriptado;
                
    }
   
    public void mostrarSerializado(){
       JOptionPane.showMessageDialog(null, "p: " + p + "\n"
                    + "q: " + q + "\n"
                    + "n: " + n + "\n"
                    + "phi(n) " + totient + "\n"
                    + "e: " + E + "\n"
                    + "d: " + d + "\n"
                    + "Tu mensaje: " + mensaje + "\n"
                    + "Tu encriptacion: " + encriptado);
    }

    //metodo para generar numeros primos

    public void generarPrimos(BigInteger p, BigInteger q){
        //para los primos son p y q
        p = new BigInteger(TAMPRIMO, 10, new Random());
        do q = new BigInteger(TAMPRIMO, 10, new Random());
            while(q.compareTo(p)==0);
    }

    //generar las claves

    public void generarClaves(){
        // n = p*q
        n = p.multiply(q); //p*q
        //p(hi) = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));

        //elegir el numero coprimo o primo relativo menor que n

        do E = new BigInteger(2*TAMPRIMO, new Random());
            while ((E.compareTo(totient)!=-1) || 
            (E.gcd(totient).compareTo(BigInteger.valueOf(1))!=0));
        //ahora debemos hacer la operacion modulo
        // d = e^ 1 mod totient

        d = E.modInverse(totient);

    }

    /*
    Cifrar con el numero e ya que "e" es la clave publica
    */ 

    public BigInteger[] encriptar(String mensaje){
        //variables
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        //lo primero que debemos hacer es correr el tamaño de bigdigitos
        for(i = 0; i<bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }

        //vamos a cifrar
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i = 0; i<bigdigitos.length; i++){
            encriptado[i] = bigdigitos[i].modPow(E,n);
        }
        return encriptado;
    }

    /*
    descifrar array de biginteger
    */ 

    public String desencriptar(BigInteger[] encriptado){
        BigInteger[] desencriptar = new BigInteger[encriptado.length];

        for(int i = 0; i<desencriptar.length; i++){
            desencriptar[i] = encriptado[i].modPow(d, n);
        }

        char[] charArray = new char[desencriptar.length];

        for(int i = 0; i<charArray.length; i++){
            charArray[i] = (char)(desencriptar[i].intValue());
        }

        return (new String(charArray));
    }
    
}