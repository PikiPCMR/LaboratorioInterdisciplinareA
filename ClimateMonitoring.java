import java.io.*;
import java.util.*;

public class ClimateMonitoring extends CentroMonitoraggio{
    private static final String CSV_FILE = "operatori-registrati.csv";
    private static final String CSV_SEPARATOR = ",";
    
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
                case 1:
                    eseguiRicerca();
                    break;
                case 2:
                    eseguiLogin();
                    break;
                case 3:
                    eseguiRegistrazione();
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
            
            System.out.println();
        }
    }
    
    private static void eseguiRicerca() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Inserisci il termine di ricerca: ");
        String termineRicerca = scanner.nextLine();
        
        // Effettua la ricerca nel file CSV o in un'altra fonte di dati
        // Esempio:
        List<String[]> risultati = ricercaDati(termineRicerca);
        
        // Mostra i risultati
        if (risultati.isEmpty()) {
            System.out.println("Nessun risultato trovato.");
        } else {
            System.out.println("Risultati della ricerca:");
            for (String[] risultato : risultati) {
                System.out.println(Arrays.toString(risultato));
            }
        }
    }
    
    private static void eseguiLogin() {
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
            boolean continua = false;
            registraCentroAree(userID);
            // Esegui le operazioni dopo il login
        } else {
            System.out.println("Credenziali non valide.");
        }
    }
    
    private static void eseguiRegistrazione() {
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
        
        System.out.print("CDMDA: ");
        String cdmda = scanner.nextLine();
        
        // Salva le credenziali nel file CSV o in un'altra fonte di dati
        // Esempio:
        boolean registrazioneRiuscita = salvaCredenziali(nomeCognome, codFisc, email, userID, password, cdmda);
        
        if (registrazioneRiuscita) {
            System.out.println("Registrazione effettuata con successo.");
            // Esegui le operazioni dopo la registrazione
        } else {
            System.out.println("Errore durante la registrazione.");
        }
    }
    
    private static List<String[]> ricercaDati(String termineRicerca) {
        List<String[]> risultati = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                
                // Esegui la logica di ricerca sui dati
                // Esempio: confronta il termine di ricerca con i dati presenti nel file
                // Se trovi una corrispondenza, aggiungi l'array di stringhe ai risultati
                // Esempio:
                if (Arrays.asList(data).contains(termineRicerca)) {
                    risultati.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return risultati;
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
    
    private static boolean salvaCredenziali(String nomeCognome, String codFisc, String email, String userID, String password, String cdmda) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Aggiungi una nuova riga al file CSV con le credenziali
            // Esempio:
            writer.write(nomeCognome + CSV_SEPARATOR + codFisc + CSV_SEPARATOR + email + CSV_SEPARATOR +
                    userID + CSV_SEPARATOR + password + CSV_SEPARATOR + cdmda);
            writer.newLine();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
