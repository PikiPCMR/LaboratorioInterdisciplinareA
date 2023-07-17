import java.io.*;
import java.util.*;

public class CentroMonitoraggio {
    private static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
    private static final String CSV_AREE_INTERESSE = "geonames-and-coordinates.csv";
    private static final String CSV_SEPARATOR = ",";
    
    public static void registraCentroAree() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nome Centro di Monitoraggio: ");
        String nomeCentro = scanner.nextLine();
        
        System.out.print("Indirizzo Fisico: ");
        String indirizzoFisico = scanner.nextLine();
        
        // Elenco delle aree di interesse
        List<String> elencoAree = elencoAreeInteresse();
        
        // Visualizza le aree di interesse disponibili
        System.out.println("Elenco Aree di Interesse:");
        for (int i = 0; i < elencoAree.size(); i++) {
            System.out.println((i + 1) + ". " + elencoAree.get(i));
        }
        
        // Richiede all'utente di selezionare le aree di interesse
        System.out.print("Seleziona le Aree di Interesse (inserisci i numeri separati da virgola): ");
        String inputAree = scanner.nextLine();
        
        // Splitta l'input dell'utente per ottenere i numeri delle aree selezionate
        String[] inputAreeArray = inputAree.split(",");
        
        // Crea una lista per memorizzare le aree selezionate
        List<String> areeSelezionate = new ArrayList<>();
        
        // Aggiungi le aree selezionate alla lista
        for (String input : inputAreeArray) {
            int numeroArea = Integer.parseInt(input.trim()) - 1;
            if (numeroArea >= 0 && numeroArea < elencoAree.size()) {
                areeSelezionate.add(elencoAree.get(numeroArea));
            }
        }
        
        // Salva le informazioni nel file CSV dei centri di monitoraggio
        if (salvaCentroMonitoraggio(nomeCentro, indirizzoFisico, areeSelezionate)) {
            System.out.println("Centro di Monitoraggio registrato con successo.");
        } else {
            System.out.println("Errore durante la registrazione del Centro di Monitoraggio.");
        }
    }
    
    private static List<String> elencoAreeInteresse() {
        List<String> elencoAree = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_AREE_INTERESSE))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                
                // Aggiungi l'area di interesse alla lista
                // Esempio:
                elencoAree.add(data[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return elencoAree;
    }
    
    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, List<String> areeInteresse) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_CENTRI_MONITORAGGIO, true))) {
            // Formatta le aree di interesse come una singola stringa separata da virgole
            String elencoAree = String.join(",", areeInteresse);
            
            // Aggiungi una nuova riga al file CSV con le informazioni del centro di monitoraggio
            // Esempio:
            writer.write(nomeCentro + CSV_SEPARATOR + indirizzoFisico + CSV_SEPARATOR + elencoAree);
            writer.newLine();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
