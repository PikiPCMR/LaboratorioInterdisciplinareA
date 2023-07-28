import java.io.*;
import java.util.*; 

/**
 * La classe ParametriClimatici gestisce l'inserimento dei parametri climatici
 * per i geonames di un determinato operatore.
 */

public class ParametriClimatici {

    /** Nome del file CSV contenente i dati dei centri di monitoraggio.*/

    public static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";

    /** Nome file CSV dei parametri climatici. */

    public static final String CSV_PARAMETRI_CLIMATICI = "parametri-climatici.csv";

     /** Separatore utilizzato nel file CSV. */

    public static final String CSV_SEPARATOR = ";";


    /**
     * Inserisce i parametri climatici per un geoname di un operatore specificato.
     * 
     * l'obbiettivo di questa parte di codice è usare i metodi creati precedentemente 
     * per individuare le caratteristiche che distinguono una posizione geografica 
     * per poi abbinarla al geoname di un operatore.  
     *
     * @param <operatore> Operatore per cui inserire i parametri climatici.
     */

    public static void inserisciParametriClimatici(String operatore) {
        Scanner scanner = new Scanner(System.in);

        // Controlla se l'operatore corrisponde nel file dei centri di monitoraggio
        if (verificaOperatore(operatore)) {
            List<String> geonames = getGeonames(operatore);
            if (geonames.isEmpty()) {
                System.out.println("Non sono disponibili geonames per l'operatore.");
                return;
            }

            System.out.println("Geonames disponibili:");
            for (int i = 0; i < geonames.size(); i++) {
                System.out.println((i + 1) + ". " + geonames.get(i));
            }

            int selectedGeonameIndex = -1;
            while (selectedGeonameIndex < 0 || selectedGeonameIndex >= geonames.size() + 1) {
                System.out.print("Seleziona un geoname (1-" + geonames.size() + "): ");
                selectedGeonameIndex = scanner.nextInt();
                scanner.nextLine();

                if (selectedGeonameIndex < 0 || selectedGeonameIndex >= geonames.size() + 1) {
                    System.out.println("Selezione non valida.");
                }
            }

            String selectedGeoname = geonames.get(selectedGeonameIndex - 1);

            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PARAMETRI_CLIMATICI))) {
                String line;
                boolean parametroPresente = false;

                // Legge il file dei parametri climatici e controlla se il parametro è già stato inserito
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(CSV_SEPARATOR);

                    // Verifica se il parametro climatico è già stato inserito per l'operatore e il geoname correnti
                    if (data.length >= 2 && data[0].equals(operatore) && data[1].equals(selectedGeoname)) {
                        parametroPresente = true;
                        break;
                    }
                }

                if (parametroPresente) {
                    System.out.println("Il parametro è già stato inserito.");
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Errore durante la verifica dei parametri climatici.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_PARAMETRI_CLIMATICI, true))) {
                writer.write(operatore + CSV_SEPARATOR + selectedGeoname + CSV_SEPARATOR);

                String[] parametri = {"vento", "umidita", "pressione", "temperatura", "precipitazioni", "altitudine-giacciai", "massa-ghiacciai"};

                System.out.println("Inserisci spiegazione, punteggio e delle note separate da una virgola");
                for (String parametro : parametri) {
                    System.out.print(parametro + ": ");
                    String valore = scanner.nextLine();

                    writer.write(valore + CSV_SEPARATOR);
                }

                writer.newLine();
                System.out.println("Parametri climatici inseriti con successo.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Errore durante l'inserimento dei parametri climatici.");
            }
        } else {
            System.out.println("L'operatore specificato non esiste.");
        }
    }

    /**
     * Verifica se un operatore specificato esiste nel file dei centri di monitoraggio.
     * 
     * @param <operatore> L'operatore da verificare.
     * @return true se l'operatore esiste, altrimenti false.
     */


    private static boolean verificaOperatore(String operatore) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_CENTRI_MONITORAGGIO))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se l'operatore corrisponde nel file dei centri di monitoraggio
                if (data.length >= 4 && data[3].equals(operatore)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Ottiene una lista di geonames associati a un operatore specificato.
     *
     * @param <operatore> L'operatore per cui ottenere i geonames.
     * @return Una lista di geonames associati all'operatore.
     */

    private static List<String> getGeonames(String operatore) {
        List<String> geonames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_CENTRI_MONITORAGGIO))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                // Verifica se l'operatore corrisponde nel file dei centri di monitoraggio
                if (data.length >= 4 && data[3].equals(operatore)) {
                    String[] geonamesArray = data[2].trim().split(",");
                    geonames.addAll(Arrays.asList(geonamesArray));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return geonames;
    }
}
