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

    
    ArrayList<KlijentKaoServer> listaKlijenata;
    boolean[] nizPodrzanihKonv = new boolean[4];

    
    

    public Server( boolean[] nizPodrzanihKonv) throws IOException {
        
        this.nizPodrzanihKonv = nizPodrzanihKonv;
        
        
    }

    @Override
    public void run() {
        int port = 2000;
        Socket soket = null;
        try {
            while (true) {
                
                ServerSocket serverskiSoket = new ServerSocket(port);
                soket = serverskiSoket.accept();
                KlijentKaoServer klijent = new KlijentKaoServer(soket, listaKlijenata, nizPodrzanihKonv);
                listaKlijenata.add(klijent);
                klijent.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
