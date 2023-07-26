/**
 * Crea l'interfaccia iniziale che ci permette di eseguire le operazioni presenti nel resto del codice implementato per la realizzazione del progetto,
 * infatti attraverso dei semplici comandi numerici potremo:
 *  1) ricercare un luogo del mondo preregistrato per ottenerne le informazione fondamenteali;
 *  2) effettuare il login al nostro profilo creato con la funzione "3", registrazione, per poi registrare o modificare i dati immessi relativi a un luogo non presente nel file 
 *  3) effettuare la registrazione creando un profilo con il quale sarà poi possibile effettuare il login "2".
 *
 * @author tag da inserire
 * 
 */



import java.util.*;
public class Main {

    /**
     * Metodo principale che avvia l'esecuzione del programma.
     *
     * @param args Argomenti passati dalla riga di comando (non utilizzati in questo caso).
     */


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;

        while (continua) {
            // Mostra il menu
            System.out.println("Benvenuto!");
            System.out.println("1. Ricerca");
            System.out.println("2. Login");
            System.out.println("3. Registrazione");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();

            switch (scelta) {
               
               //ricerca un luogo del mondo ottenerne le informazioni inserite nei file dati, o inserite dagli utenti registrati.
                case 1:
                    Ricerca.cercaAreaGeografica();
                    break;

               //effettuare il login al nostro profilo creato con la funzione "3", registrazione.
               
                case 2:
                    Accesso.eseguiLogin();
                    break;

                //effettua l'accesso alla pagina per la registrazione del profilo.
                
                case 3:
                    Accesso.eseguiRegistrazione();
                    break;

                // Esci dal programma impostando la variabile "continua" su false.

                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }

            System.out.println();
        }
    }
}
