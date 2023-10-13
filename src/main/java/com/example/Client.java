package com.example;

import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int porta = 6789;

    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    public Socket connetti() {
        System.out.println("2 CLIENT partito in esecuzione ...");

        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nomeServer, porta);
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione.");
            System.exit(1);
        }
        return mioSocket;

    }

    public void comunica() {
        try {
            for (;;) {
                System.out.println("4... inserisci un numero (0-999) da trasmettere al server:" + '\n');
                stringaUtente = tastiera.readLine();

                System.out.println("5... invio il numero al server e attendo ...");
                outVersoServer.writeBytes(stringaUtente + '\n');

                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("7 ... risposta dal server " + '\n' + stringaRicevutaDalServer);

                if (stringaRicevutaDalServer.equals("Hai indovinato!")) {
                    System.out.println("8 CLIENT: termina elaborazione e chiude connessione");
                    mioSocket.close();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server !");
            System.exit(1);
        }

    }

}
