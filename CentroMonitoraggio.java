import java.io.*;
import java.util.*;

public class CentroMonitoraggio {
    private static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
    private static final String CSV_AREE_INTERESSE = "geonames-and-coordinates.csv";
    private static final String CSV_SEPARATOR = ",";
    
    public static void registraCentroAree(String operatore) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nome Centro di Monitoraggio: ");
        String nomeCentro = scanner.nextLine();
        
        System.out.print("Indirizzo Fisico: ");
        String indirizzoFisico = scanner.nextLine();
        
        System.out.print("Geoname dell'Area di Interesse: ");
        String geoname = scanner.nextLine();
        
        // Verifica se l'area di interesse esiste nel file CSV
        if (verificaAreaInteresse(geoname)) {
            // Salva le informazioni nel file CSV dei centri di monitoraggio
            if (salvaCentroMonitoraggio(nomeCentro, indirizzoFisico, geoname, operatore)) {
                System.out.println("Centro di Monitoraggio registrato con successo.");
            } else {
                System.out.println("Errore durante la registrazione del Centro di Monitoraggio.");
            }
        } else {
            System.out.println("L'Area di Interesse specificata non esiste.");
        }
    }
    
    private static boolean verificaAreaInteresse(String geoname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_AREE_INTERESSE))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                
                // Verifica se il geoname corrisponde all'area di interesse nel file CSV
                if (data.length >= 1 && data[0].equals(geoname)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String geoname, String operatore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_CENTRI_MONITORAGGIO, true))) {
            // Aggiungi una nuova riga al file CSV con le informazioni del centro di monitoraggio
            writer.write(nomeCentro + CSV_SEPARATOR + indirizzoFisico + CSV_SEPARATOR + geoname + CSV_SEPARATOR + operatore);
            writer.newLine();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}


