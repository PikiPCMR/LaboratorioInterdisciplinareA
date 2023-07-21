import java.util.*;
import java.io.*;
public class Accesso {
    private static final String CSV_FILE = "operatori-registrati.csv";
    private static final String CSV_SEPARATOR = ";";
    public static void eseguiLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("UserID: ");
        String userID = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Verifica le credenziali nel file CSV o in un'altra fonte di dati
        // Esempio:
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
                scanner.nextLine(); // Consuma la nuova riga dopo nextInt()

                switch (scelta) {
                    case 1 -> CentroMonitoraggio.registraCentroAree(userID);
                    case 2 -> CentroMonitoraggio.visualizzaCentriMonitoraggio(userID);
                    case 3 -> ParametriClimatici.inserisciParametriClimatici(userID);
                    case 0 -> continua = false;
                    default -> System.out.println("Scelta non valida.");
                }
            }
        } else {
            System.out.println("Credenziali non valide.");
        }
    }


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
        // Esempio:
        boolean registrazioneRiuscita = salvaCredenziali(nomeCognome, codFisc, email, userID, password);

        if (registrazioneRiuscita) {
            System.out.println("Registrazione effettuata con successo.");
            // Esegui le operazioni dopo la registrazione
        } else {
            System.out.println("Errore durante la registrazione.");
        }
    }

    private static boolean verificaCredenziali(String userID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se le credenziali corrispondono ai dati presenti nel file
                // Esempio:
                if (data.length >= 5 && data[3].equals(userID) && data[4].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean salvaCredenziali(String nomeCognome, String codFisc, String email, String userID, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Aggiungi una nuova riga al file CSV con le credenziali
            // Esempio:
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
