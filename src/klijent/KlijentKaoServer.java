/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static klijent.Klijent.nizPovezanihKlijenata;

/**
 *
 * @author NESA
 */
public class KlijentKaoServer extends Thread {

    BufferedReader ulazniTokOdKlijenta = null;
    PrintStream izlazniTokKaKlijentu = null;
    Socket soketZaKomentare = null;
    ArrayList<KlijentKaoServer> listaKlijenata;
    boolean[] nizPodrzanihKonv = new boolean[4];
    boolean krajKonverzije = true;

    //konstruktor
    public KlijentKaoServer(Socket soket, ArrayList<KlijentKaoServer> listaKlijenata, boolean[] nizPodrzanihKonv) {
        this.soketZaKomentare = soket;
        this.listaKlijenata = listaKlijenata;
        this.nizPodrzanihKonv = nizPodrzanihKonv;
    }

    public String konvertuj(int broj, int baza) {

        char[] niz = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'};
        String convertedNum = "";

        do {
            convertedNum += niz[broj % baza];
            broj = broj / baza;
        } while (broj != 0);

        return convertedNum = new StringBuilder(convertedNum).reverse().toString();
    }

    @Override
    public void run() {
        String linija;
        String textBroj;
        int broj;
        try {
//            try {
//                Socket klijentSoket = null;
//                int portZaServerskuUlogu = 3333;
//                ServerSocket serverskiSoket = new ServerSocket(portZaServerskuUlogu);
//                while (true) {
//                    klijentSoket = serverskiSoket.accept();
//                    KlijentKaoServer klijent;
//                    klijent = new KlijentKaoServer(klijentSoket, nizPovezanihKlijenata, nizPodrzanihKonv);
//                    klijent.start();
//                }
//
//            } catch (Exception e) {
//                System.out.println(e);
//            }
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomentare.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomentare.getOutputStream());
            izlazniTokKaKlijentu.println("Dobrodosli. ");
            
                izlazniTokKaKlijentu.println("Mozete da odradite sledece konverzije u oba smera:");

                // proverava koje konverzije podrzava
                if (nizPodrzanihKonv[0]==false) {
                    izlazniTokKaKlijentu.println("konverzija 10 u 16 i obrnuto. Da odaberete ovu konverziju unesite: \"Konvertuj\" pa razmak i onda simbol za konverziju Conv10to16 ili Conv16to10");
                }
                if (nizPodrzanihKonv[1]) {
                    izlazniTokKaKlijentu.println("konverzija 4 u 8 i obrnuto. Da odaberete ovu konverziju unesite: \"Konvertuj\" pa razmak i onda simbol za konverziju Conv4to8 ili Conv8to4");
                }
                if (nizPodrzanihKonv[2]) {
                    izlazniTokKaKlijentu.println("konverzija 2 u 10 i obrnuto. Da odaberete ovu konverziju unesite: \"Konvertuj\" pa razmak i onda simbol za konverziju Conv10to2 ili Conv2to10");
                }
                if (nizPodrzanihKonv[3]) {
                    izlazniTokKaKlijentu.println("konverzija 5 u 7 i obrnuto. Da odaberete ovu konverziju unesite: \"Konvertuj\" pa razmak i onda simbol za konverziju Conv5to7 ili Conv7to5");
                }
                linija = ulazniTokOdKlijenta.readLine();
                if (linija.startsWith("Konvertuj Conv16to10")) {
                    izlazniTokKaKlijentu.println("Unesite heksadecimalni broj koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 16);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + broj);
                }
                if (linija.startsWith("Konvertuj Conv10to16")) {
                    izlazniTokKaKlijentu.println("Unesite decimalni broj koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj);
                    textBroj = Integer.toHexString(broj).toUpperCase();
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }
                if (linija.startsWith("Konvertuj Conv4to8")) {
                    izlazniTokKaKlijentu.println("Unesite broj sa osnovom 4 koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 4);
                    textBroj = Integer.toOctalString(broj);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }
                if (linija.startsWith("Konvertuj Conv8to4")) {
                    izlazniTokKaKlijentu.println("Unesite oktalni broj koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 8);
                    textBroj = konvertuj(broj, 4);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }
                if (linija.startsWith("Konvertuj Conv2to10")) {
                    izlazniTokKaKlijentu.println("Unesite binarni broj koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 2);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + broj);
                }
                if (linija.startsWith("Konvertuj Conv10to2")) {
                    izlazniTokKaKlijentu.println("Unesite decimalni broj koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj);
                    textBroj = Integer.toBinaryString(broj);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }
                if (linija.startsWith("Konvertuj Conv5to7")) {
                    izlazniTokKaKlijentu.println("Unesite broj sa osnovom 5 koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 5);
                    textBroj = konvertuj(broj, 7);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }
                if (linija.startsWith("Konvertuj Conv7to5")) {
                    izlazniTokKaKlijentu.println("Unesite broj sa osnovom 7 koji zelite da konvertujete.");
                    textBroj = ulazniTokOdKlijenta.readLine();
                    broj = Integer.parseInt(textBroj, 7);
                    textBroj = konvertuj(broj, 5);
                    izlazniTokKaKlijentu.println("Vas rezultat je " + textBroj);
                }

            
        } catch (Exception e) {
        }
    }

}
