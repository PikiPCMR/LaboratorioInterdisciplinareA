import java.io.*;
import java.util.*;

/**
 * La classe CentroMonitoraggio gestisce le operazioni di registrazione 
 * e visualizzazione dei centri di monitoraggio e delle relative
 * aree di interesse.
 * 
 * @author inserire tag degli autori
 * 
 */

public class CentroMonitoraggio {

    /**
     * Nome del file CSV contenente i dati dei centri di monitoraggio.
     */
    public static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
    
     /**
     * Nome del file CSV contenente i dati delle aree di interesse.
     */

    public static final String CSV_AREE_INTERESSE = "geonames-and-coordinates.csv";
    
     /**
     * Nome del file CSV per separare i dati.
     */

    public static final String CSV_SEPARATOR = ";";


 /**
     * Metodo che registra un nuovo centro di monitoraggio con le relative
     * aree di interesse.
     * 
     * Richiede all'utente di inserire il nome del centro, l'indirizzo 
     * fisico e l'elenco delle aree di interesse.
     * Verifica la validità delle aree di interesse e, se valide, 
     * registra il centro nel file CSV dei centri di monitoraggio.
     *
     * @param <operatore> L'operatore che sta eseguendo la registrazione del
     * centro di monitoraggio.
     */

    public static void registraCentroAree(String operatore) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome Centro di Monitoraggio: ");
        String nomeCentro = scanner.nextLine();

        System.out.print("Indirizzo Fisico (Via, CAP, localita'): ");
        String indirizzoFisico = scanner.nextLine();

        System.out.print("Elenco Aree di Interesse (separate da virgola, tutto attaccato): ");
        String elencoAreeInteresse = scanner.nextLine();

        // Verifica se le aree di interesse esistono nel file CSV
        if (verificaAreeInteresse(elencoAreeInteresse)) {
            // Salva le informazioni nel file CSV dei centri di monitoraggio
            if (salvaCentroMonitoraggio(nomeCentro, indirizzoFisico, elencoAreeInteresse, operatore)) {
                System.out.println("Centro di Monitoraggio registrato con successo.");
            } else {
                System.out.println("Errore durante la registrazione del Centro di Monitoraggio.");
            }
        } else {
            System.out.println("Una o più Aree di Interesse specificate non esistono.");
        }
    }


    /**
     * Metodo che visualizza i centri di monitoraggio registrati per 
     * un determinato operatore.
     * 
     * Legge i dati dal file CSV "CentroMonitoraggio.csv" dei centri 
     * di monitoraggio e stampa a schermo i centri associati all'operatore specificato.
     *
     * @param <operatore> L'operatore per cui si vogliono visualizzare i centri
     * di monitoraggio.
     * 
     */

    public static void visualizzaCentriMonitoraggio(String operatore) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_CENTRI_MONITORAGGIO))) {
            String line;
            System.out.println("Centri di Monitoraggio registrati per l'operatore " + operatore + ":");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se il centro di monitoraggio appartiene all'operatore corrente
                if (data.length >= 4 && data[3].equals(operatore)) {
                    System.out.println("Nome Centro: " + data[0]);
                    System.out.println("Indirizzo Fisico: " + data[1]);
                    System.out.println("Elenco Aree di Interesse: " + data[2]);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo privato che verifica la validità delle aree di interesse fornite dall'utente.
     * 
     * Legge i dati dal file CSV delle aree di interesse e controlla se le aree
     * specificate esistono nel file.
     *
     * @param <elencoAreeInteresse> Elenco delle aree di interesse richieste 
     * dall'utente.
     * @return true se tutte le aree di interesse sono valide, false altrimenti.
     */

    private static boolean verificaAreeInteresse(String elencoAreeInteresse) {
        String[] areeInteresse = elencoAreeInteresse.split(",");
        List<String> areeInteresseList = Arrays.asList(areeInteresse);

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_AREE_INTERESSE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se le aree di interesse corrispondono all'elenco nel file CSV
                // Assumi che le aree di interesse siano nella prima colonna (indice 0) del file CSV
                // Puoi modificare l'indice in base alla posizione effettiva nel tuo file
                if (data.length >= 1 && areeInteresseList.contains(data[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Metodo privato che salva le informazioni di un centro di monitoraggio
     * nel file CSV "centri-monitoraggio.csv" dei centri di monitoraggio.
     * 
     * Aggiunge una nuova riga al file con i dati del centro di monitoraggio 
     * e le relative aree di interesse.
     *
     * @param <nomeCentro>  Il nome del centro di monitoraggio.
     * @param <indirizzoFisico>  L'indirizzo fisico del centro di monitoraggio.
     * @param <elencoAreeInteresse> Elenco delle aree di interesse associate
     * al centro di monitoraggio.
     * @param <operatore>  L'operatore che sta registrando il centro di monitoraggio.
     * @return true se il salvataggio ha avuto successo, false altrimenti.
     */




    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String elencoAreeInteresse, String operatore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_CENTRI_MONITORAGGIO, true))) {
            // Aggiungi una nuova riga al file CSV con le informazioni del centro di monitoraggio
            writer.write(nomeCentro + CSV_SEPARATOR + indirizzoFisico + CSV_SEPARATOR + elencoAreeInteresse + CSV_SEPARATOR + operatore);
            writer.newLine();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}