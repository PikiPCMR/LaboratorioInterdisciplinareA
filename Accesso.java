import java.util.*;
import java.io.*;


    /**
    * La classe Accesso gestisce le operazioni di login e registrazione degli utenti.
    * 
    * Utilizza un file CSV "operatori-registrati.csv" come sorgente di dati per
    * memorizzare e verificare le credenziali.
    * 
    * @author Alessandro Borges
    * @author Nicola Pichierri
    * @author Francesco Scolaro
    * @author Luca Tagliabue
    */


public class Accesso {

     /** Costanti per il file CSV e il separatore di campo.*/
    
    private static final String CSV_FILE = "operatori-registrati.csv";
   
     /** Separatore utilizzato nel file CSV. */
   
    private static final String CSV_SEPARATOR = ";";
    
    /**
     * Esegue l'operazione di login per un utente.
     * 
     * Richiede l'inserimento di UserID e Password e verifica le credenziali tramite
     * il metodo verificaCredenziali.
     * Se il login ha successo, mostra un menu con le opzioni disponibili.
     * L'utente può scegliere tra le seguenti opzioni:
     * 1) Registra Centro Aree;
     * 2) Visualizza Centri di Monitoraggio;
     * 3) Inserisci parametri climatici;
     * 0) Logout (uscita dal menu).
     */
    
    public static void eseguiLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("UserID: ");
        String userID = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Verifica le credenziali nel file CSV "operatori-registrati.csv".
        
        boolean loginRiuscito = verificaCredenziali(userID, password);

        if (loginRiuscito) {
            System.out.println("Login effettuato con successo.");

            boolean continua = true;
            while (continua) {
                System.out.println("\nCosa vuoi fare dopo il login?");
                System.out.println("1. Registra Centro Aree");
                System.out.println("2. Visualizza Centri di Monitoraggio");
                System.out.println("3. Inserisci parametri climatici");
                System.out.println("0. Logout");

                System.out.print("Scelta: ");
                int scelta = scanner.nextInt();
                scanner.nextLine(); 
                // Consuma la nuova riga dopo nextInt().

                switch (scelta) {
                    case 1:
                    CentroMonitoraggio.registraCentroAree(userID);
                    break;
                    case 2:
                    CentroMonitoraggio.visualizzaCentriMonitoraggio(userID);
                    break;
                    case 3: 
                    ParametriClimatici.inserisciParametriClimatici(userID);
                    break;
                    case 0:
                    continua = false;
                    break;
                    default: System.out.println("Scelta non valida.");
                }
            }
        } else {
            System.out.println("Credenziali non valide.");
        }
    }
        /**
        * Esegue l'operazione di registrazione di un nuovo utente.
        * 
        * Richiede l'inserimento di Nome e Cognome, Codice Fiscale, Email, UserID e Password.
        * Salva le credenziali nel file CSV "operatori-registrati.csv" tramite il metodo salvaCredenziali.
        * Se la registrazione ha successo, mostra un messaggio di conferma.
        */

    public static void eseguiRegistrazione() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome e Cognome: ");
        String nomeCognome = scanner.nextLine();

        System.out.print("Codice Fiscale: ");
        String codFisc = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("UserID: ");
        String userID = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Salva le credenziali nel file CSV o in un'altra fonte di dati
        
        boolean registrazioneRiuscita = salvaCredenziali(nomeCognome, codFisc, email, userID, password);

        if (registrazioneRiuscita) {
            System.out.println("Registrazione effettuata con successo.");
            // Esegui le operazioni dopo la registrazione
        } else {
            System.out.println("Errore durante la registrazione.");
        }
    }  
    /**
     * Verifica le credenziali dell'utente nel file CSV.
     *
     * @param <userID>   Il UserID inserito dall'utente per il login.
     * @param <password> La password inserita dall'utente per il login.
     * @return true se le credenziali corrispondono a un utente registrato, false altrimenti.
     */


    private static boolean verificaCredenziali(String userID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se le credenziali corrispondono ai dati presenti nel file
            
                if (data.length >= 5 && data[3].equals(userID) && data[4].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
     /**
      * Salva le credenziali di un nuovo utente nel file CSV.
      *
      * @param <nomeCognome> Il Nome e Cognome dell'utente da registrare.
      * @param <codFisc>     Il Codice Fiscale dell'utente da registrare.
      * @param <email>       L'Email dell'utente da registrare.
      * @param <userID>      Il UserID dell'utente da registrare.
      * @param <password>    La Password dell'utente da registrare.
      * @return true se la registrazione ha successo, false altrimenti.
      */
    private static boolean salvaCredenziali(String nomeCognome, String codFisc, String email, String userID, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Aggiungi una nuova riga al file CSV con le credenziali
           
            writer.write(nomeCognome + CSV_SEPARATOR + codFisc + CSV_SEPARATOR + email + CSV_SEPARATOR +
                    userID + CSV_SEPARATOR + password);
            writer.newLine();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
