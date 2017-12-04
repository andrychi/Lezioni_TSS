/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg30.pkg11.pkg17.luca.sportello.avanzato;

import javax.swing.JOptionPane;

/**
 *
 * @author tss
 */
public class LucaSportelloAvanzato {
static double saldo = 0.000;
    static String codici[];
    static double prezzi[];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //prima cosa da fare è capire cosa vuole fare l'utente attraverso un ciclo  do while
        // versamento (V) o prelievo (P) o uscire (U)

        // fase di setup
        init_setup();

        String scelta;

        do {
            scelta = JOptionPane.showInputDialog("quale operazione vuoi fare?\n\nM = macchinetta\nP = prelevare\nV = versare\nU = uscire\n \nil tuo saldo disponibile è " + saldo + " $").toUpperCase();
            // se M fai previliavo dalla macchinetta

            // utilizzo dello switch
            switch (scelta) {
                case "M":
                    prebibite();
                    break;
                case "P":
                    prelevare();
                    break;
                case "V":
                    versare();
                    break;
                default:

                    break;
            }

        } while (!scelta.equals("U"));
    }

    static void versare() {
        double soldi_versati;
        do {

            soldi_versati = inputvaluta("inserisci banconote o moneta\nil tuo saldo attuale è " + saldo + " $" );
            saldo += soldi_versati;
            soldi_versati = decimali(saldo, 2);
            
            

        } while (soldi_versati == 0);
    }

    static void versareold() {
        String versato = "";
        do {
            versato = JOptionPane.showInputDialog("inserisci banconote o moneta?\nil tuo saldo attuale è " + saldo + " $" + "\ndigita U per terminare il versamento").toUpperCase();
            if (!versato.equals("U")) {
                double soldi_versati = Double.parseDouble(versato);
                saldo += soldi_versati;
                saldo = decimali(saldo, 2);
                JOptionPane.showMessageDialog(null, "hai ancora " + saldo + " $");
            }

        } while (!versato.equals("U"));
    }

    static void prelevare() {
        //String prelevato = JOptionPane.showInputDialog("quanto vuoi prelevare ?");
        double soldi_prelevati = inputvaluta("quanto vuoi prelvare?");
        if (saldo >= soldi_prelevati) {
            saldo -= soldi_prelevati;
            saldo = decimali(saldo, 2);
            JOptionPane.showMessageDialog(null, "prelevare pure i soldi\nhai ancora " + saldo + " $");
        } else //mancano i soldi
        {
            JOptionPane.showMessageDialog(null, "cazzo fai sei povero");
        }
    }

    static void prebibite() {
        String codice = JOptionPane.showInputDialog("quale prodotto vuoi acquistare? \n" + listino());
        codice = codice.toUpperCase();
        int posizioneok = -1;
        //cerco nei codici la posizione di codice se esiste

        for (int i = 0; i < codici.length; i++) {
            // se in posizione i trovo codice in codici[i] ok trovato e mi segno la posizione
            if (codice.equals(codici[i].toUpperCase())) {
                posizioneok = i;
                break;
            }
        }
        double costo = 0;
        if (posizioneok >= 0) {
            costo = prezzi[posizioneok];

            if (saldo >= costo) {
                saldo -= costo;
                saldo = decimali(saldo, 2);
                JOptionPane.showMessageDialog(null, "prelevare pure la bibita\nhai ancora " + saldo + " $");
            } else //mancano i soldi
            {
                JOptionPane.showMessageDialog(null, "cazzo fai sei povero");
            }
        } else {
            JOptionPane.showMessageDialog(null, " weee ciccio   codice errato");
        }
    }

    static double arrotonda(double valore) {
        double moltiplicatore = 100;
        valore = valore * moltiplicatore;
        valore = Math.round(valore) / moltiplicatore;
        return valore;
    }

    static double decimali(double valore, int cifre) {
        double moltiplicatore = 1;
        for (int i = 0; i < cifre; i++) {
            moltiplicatore = moltiplicatore * 10;
        }
        valore = valore * moltiplicatore;
        valore = Math.round(valore) / moltiplicatore;
        return valore;
    }

    // esempio ; double valore = inputvaluta ("dimmi quanti soldi vuoi")
    static double inputvaluta(String domanda) {

        double ris = -1;

        boolean ko = true;
        while (ko == true) {

            try {
                ko = false;
                String val = JOptionPane.showInputDialog(domanda);
                //parte pericolosa perchè se nell'input non si è inserito un valore numeri non si riesce a convertirlo in double
                ris = Double.parseDouble(val);
            } catch (Exception errore) {
                //cosa fare se errore
                System.out.println(errore.getMessage());
                ko = true;
                JOptionPane.showMessageDialog(null, "we ciccio inserisci un importo corretto");

            }
        }
        return ris;

    }

    static void init_setup() {
        // chiedere quanti reparti vuole l'utente        
        int numrep = (int) inputvaluta("Buongiorno\nquanti reparti vuoi utilizzare nella macchinetta?");

        //in base al numero dei reparti creare array per prezzi e codici
        codici = new String[numrep];
        prezzi = new double[numrep];

        // chiedere di inserire prima il codice poi il prezzo per tutti i reparti
        for (int i = 0; i < codici.length; i++) {
        //caricare codice di testo
        codici[i]=JOptionPane.showInputDialog("inserisci il codice (ad es. A1)");       
        
        //caricare prezzo del codice
        prezzi [i] = inputvaluta("inserisci il prezzo del codice :" + codici[i]);
        prezzi[i]=decimali(prezzi[i], 2);
        }
        
        //al termine codici e prezzi compilati ok per iniziare la distribuzione 
    }
    
    static String listino() {
    String inizio= "\n-----Listino prezzi-----\n";
    String fine= "-----  Fine Listino  ----\nsaldo : " + saldo + "\n-----------------------------";
    
        for (int i = 0; i < codici.length; i++) {
            int pos_dec=0;
            String prezzo=String.valueOf(prezzi[i]);
            pos_dec=prezzo.indexOf(".");
            if (prezzo.length()-pos_dec==2) {
                prezzo+="0";
            }
            prezzo="                             "+prezzo;
            prezzo=prezzo.substring((prezzo.length()-7),prezzo.length());
            inizio= inizio + codici[i]+" :  " +  prezzo + "\n";
        }
    inizio=inizio+fine;
    return inizio;
    }
}
