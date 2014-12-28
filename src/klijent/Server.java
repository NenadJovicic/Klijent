/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NESA
 */
public class Server extends Thread {

    boolean radi;
    ArrayList<KlijentKaoServer> listaKlijenata;
    boolean[] nizPodrzanihKonv = new boolean[4];

    int port;
    ServerSocket serverskiSoket;

    public Server(int port, boolean[] nizPodrzanihKonv) throws IOException {
        this.port = port;
        this.nizPodrzanihKonv = nizPodrzanihKonv;
        serverskiSoket = new ServerSocket(port);
        radi = true;
    }

    @Override
    public void run() {
       
        try {
            while (radi) {
                Socket soket = serverskiSoket.accept();
                KlijentKaoServer klijent = new KlijentKaoServer(soket, listaKlijenata, nizPodrzanihKonv);
                listaKlijenata.add(klijent);
                klijent.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
