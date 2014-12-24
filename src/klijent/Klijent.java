/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NESA
 */
public class Klijent implements Runnable {

    static Socket soketZaServer = null;
    static PrintStream izlazniKaServeru = null;
    static BufferedReader ulazniOdServera = null;
    static BufferedReader ulaznaKonzola = null;
    static boolean krajZaServer = false;
    static Socket soketKaKlijentu = null;
    static PrintStream izlazniKaKlijentu = null;
    static BufferedReader ulazniOdKlijenta = null;
    static boolean krajZaKlijenta = false;
    //   static Socket soketZaKlijenta = null;

    public static void main(String[] args) {
        String tekstKaServeru;
        String[] izvuciIP;
        InetAddress adresa = null;
        boolean[] podrzaneKonv = new boolean[4];
        try {
            int portZaServer = 2222;
            if (args.length > 0) {
                portZaServer = Integer.parseInt(args[0]);
            }
            soketZaServer = new Socket("localhost", portZaServer);
            ulaznaKonzola = new BufferedReader(new InputStreamReader(System.in));

            //ulaz-izlaz ka glavnom serveru
            izlazniKaServeru = new PrintStream(soketZaServer.getOutputStream());
            ulazniOdServera = new BufferedReader(new InputStreamReader(soketZaServer.getInputStream()));

            //nit za glavni server
            new Thread(new Klijent()).start();

            // komunikacija sa glavnim serverom
            while (!krajZaServer) {
                tekstKaServeru = ulaznaKonzola.readLine();
                izlazniKaServeru.println(tekstKaServeru);

                //proverava koje konverzije ce ovaj klijent da radi kao server i onda u boolean niz upisuje true
                if (tekstKaServeru.startsWith("Zelim da kao server radim sledece konverzije")) {
                    if (tekstKaServeru.contains("Conv10to16")) {
                        podrzaneKonv[0] = true;
                    }
                    if (tekstKaServeru.contains("Conv4to8")) {
                        podrzaneKonv[1] = true;
                    }
                    if (tekstKaServeru.contains("Conv2to10")) {
                        podrzaneKonv[2] = true;
                    }
                    if (tekstKaServeru.contains("Conv5to10")) {
                        podrzaneKonv[3] = true;
                    }
                }
                // sada ako je klijent uneo tekst za konekciju otvara novi soket za komunikaciju sa klijentom/serverom
                if (tekstKaServeru.startsWith("Konektuj se na sledecu IP adresu:")) {
                    izvuciIP = tekstKaServeru.split("#");
                    adresa = InetAddress.getByName(izvuciIP[1]);
                    int portZaKlijenta = 3333;
                    soketKaKlijentu = new Socket(adresa, portZaKlijenta);
                    izlazniKaKlijentu = new PrintStream(soketKaKlijentu.getOutputStream());
                    ulazniOdKlijenta = new BufferedReader(new InputStreamReader(soketKaKlijentu.getInputStream()));
                    new Thread(new Klijent()).start();
                    while (!krajZaKlijenta) {
                        izlazniKaServeru.println(ulaznaKonzola.readLine());
                    }
                    soketKaKlijentu.close();
                }
            }
            soketZaServer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        String linijaOdServera = null;
        String linijaOdKlijenta = null;
        try {
            while ((linijaOdServera = ulazniOdServera.readLine()) != null) {
                System.out.println(linijaOdServera);
                if (linijaOdServera.startsWith("!!! Dovidjenja")) {
                    krajZaServer = true;
                    return;
                }
            }
            while ((linijaOdKlijenta = ulazniOdKlijenta.readLine()) != null) {
                System.out.println(linijaOdKlijenta);
                if (linijaOdServera.startsWith("!!! Dovidjenja")) {
                    krajZaKlijenta = true;
                    return;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
