import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Ricerca {
    public static final String CSV_GEO_NAMES = "geonames-and-coordinates.csv";
    public static final String CSV_PARAMETRI_CLIMATICI = "parametri-climatici.csv";
    public static final String CSV_SEPARATOR = ";";




    public static void main(String[] args) {
        mostraMenu();
    }



    public static void mostraMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Benvenuto!");
            System.out.println("1. Ricerca");
            System.out.println("2. Login");
            System.out.println("3. Registrazione");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma la nuova riga

            switch (scelta) {
                case 1:
                    cercaAreaGeografica();
                    break;
                case 2:
                    // Aggiungi qui la logica per il login
                    break;
                case 3:
                    // Aggiungi qui la logica per la registrazione
                    break;
                case 0:
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
    }

    public static void cercaAreaGeografica() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleziona un criterio di ricerca:");
        System.out.println("1. Ricerca per denominazione e stato di appartenenza");
        System.out.println("2. Ricerca per coordinate geografiche");
        System.out.println("3. Torna al menu principale");
        System.out.print("Scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine(); // Consuma la nuova riga

        if (scelta == 1) {
            System.out.print("Denominazione: ");
            String denominazione = scanner.nextLine();

            System.out.print("Stato di appartenenza: ");
            String stato = scanner.nextLine();

            List<String[]> risultati = ricercaPerDenominazione(denominazione, stato);

            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per l'area geografica specificata.");
            } else {
                for (String[] risultato : risultati) {
                    System.out.println("Informazioni sull'area geografica:");
                    System.out.println("Geoname: " + risultato[0]);
                    System.out.println("Denominazione: " + risultato[1]);
                    System.out.println("Stato: " + risultato[2]);
                    System.out.println("Coordinate: " + risultato[3]);
                    System.out.println();

                    System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                    String sceltaVisualizza = scanner.nextLine();

                    if (sceltaVisualizza.equalsIgnoreCase("s")) {
                        visualizzaParametriClimatici(risultato[0]);
                    }
                }
            }
        } else if (scelta == 2) {
            System.out.print("Coordinate geografiche (es. 42.35287, -72.04535): ");
            String coordinate = scanner.nextLine();

            List<String[]> risultati = ricercaPerCoordinate(coordinate);

            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per le coordinate geografiche specificate.");
            } else {
                for (String[] risultato : risultati) {
                    System.out.println("Informazioni sull'area geografica:");
                    System.out.println("Geoname: " + risultato[0]);
                    System.out.println("Denominazione: " + risultato[1]);
                    System.out.println("Stato: " + risultato[2]);
                    System.out.println("Coordinate: " + risultato[3]);
                    System.out.println();

                    System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                    String sceltaVisualizza = scanner.nextLine();

                    if (sceltaVisualizza.equalsIgnoreCase("s")) {
                        visualizzaParametriClimatici(risultato[0]);
                    }
                }
            }
        } else if (scelta == 3) {
            // Torna al menu principale (uscendo dalla funzione)
            return;
        } else {
            System.out.println("Scelta non valida.");
        }
    }

    private static List<String[]> ricercaPerDenominazione(String denominazione, String stato) {
        List<String[]> risultati = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_GEO_NAMES))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 6 && data[2].equalsIgnoreCase(denominazione) && data[4].equalsIgnoreCase(stato)) {
                    String[] risultato = new String[4];
                    risultato[0] = data[0]; // Geoname
                    risultato[1] = data[2]; // Denominazione
                    risultato[2] = data[4]; // Stato
                    risultato[3] = data[5]; // Coordinate
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static List<String[]> ricercaPerCoordinate(String coordinate) {
        List<String[]> risultati = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_GEO_NAMES))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 6 && data[5].equalsIgnoreCase(coordinate)) {
                    String[] risultato = new String[4];
                    risultato[0] = data[0]; // Geoname
                    risultato[1] = data[2]; // Denominazione
                    risultato[2] = data[4]; // Stato
                    risultato[3] = data[5]; // Coordinate
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static void visualizzaParametriClimatici(String geoname) {
        List<String[]> parametriClimatici = getParametriClimatici(geoname);

        if (parametriClimatici.isEmpty()) {
            System.out.println("Non sono disponibili dati sui parametri climatici per questa area.");
        } else {
            System.out.println("Il file parametri-climatici.csv contiene questi valori per il geoname " + geoname + ":");
            System.out.println();

            for (String[] parametro : parametriClimatici) {
                String idParametro = parametro[1];
                String vento = parametro[2];
                String umidita = parametro[3];
                String pressione = parametro[4];
                String temperatura = parametro[5];
                String precipitazioni = parametro[6];
                String altitudineGhiacciai = parametro[7];
                String massaGhiacciai = parametro[8];

                System.out.println("Geoname: " + idParametro);
                System.out.println("Vento: " + vento);
                System.out.println("Umidità: " + umidita);
                System.out.println("Pressione: " + (pressione.equals("null") ? "non specificato" : pressione));
                System.out.println("Temperatura: " + temperatura);
                System.out.println("Precipitazioni: " + precipitazioni);
                System.out.println("Altezza dei ghiacciai: " + altitudineGhiacciai);
                System.out.println("Massa dei ghiacciai: " + massaGhiacciai);
                System.out.println();
            }
        }
    }

    private static List<String[]> getParametriClimatici(String geoname) {
        List<String[]> risultati = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PARAMETRI_CLIMATICI))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 2 && data[1].equals(geoname)) {
                    String[] risultato = new String[9];
                    for (int i = 0; i < data.length; i++) {
                        risultato[i] = data[i].equals("null") ? "non specificato" : data[i];
                    }
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }
}
