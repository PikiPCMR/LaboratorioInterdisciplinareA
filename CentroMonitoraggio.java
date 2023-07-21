import java.io.*;
import java.util.*;
public class CentroMonitoraggio {
    public static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
    public static final String CSV_AREE_INTERESSE = "geonames-and-coordinates.csv";
    public static final String CSV_SEPARATOR = ";";

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