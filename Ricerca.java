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

    public static void cercaAreaGeografica() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleziona un criterio di ricerca:");
        System.out.println("1. Ricerca per denominazione e stato di appartenenza");
        System.out.println("2. Ricerca per coordinate geografiche");
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
                String[] risultato = risultati.get(0); // Prendi il primo risultato

                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println();

                System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                String sceltaVisualizza = scanner.nextLine();

                if (sceltaVisualizza.equalsIgnoreCase("s")) {
                    visualizzaAreaGeografica(risultato[0]);
                }
            }
        } else if (scelta == 2) {
            System.out.print("Coordinate geografiche (es. 42.35287, -72.04535): ");
            String coordinate = scanner.nextLine();

            List<String[]> risultati = ricercaPerCoordinate(coordinate);

            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per le coordinate geografiche specificate.");
            } else {
                String[] risultato = risultati.get(0); // Prendi il primo risultato

                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println();

                System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                String sceltaVisualizza = scanner.nextLine();

                if (sceltaVisualizza.equalsIgnoreCase("s")) {
                    visualizzaAreaGeografica(risultato[0]);
                }
            }
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
                    String[] risultato = new String[3];
                    risultato[0] = data[1]; // Denominazione
                    risultato[1] = data[4]; // Stato
                    risultato[2] = data[5]; // Coordinate
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
                    String[] risultato = new String[3];
                    risultato[0] = data[1]; // Denominazione
                    risultato[1] = data[4]; // Stato
                    risultato[2] = data[5]; // Coordinate
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static void visualizzaAreaGeografica(String areaGeografica) {
        List<String[]> parametriClimatici = getParametriClimatici(areaGeografica);

        if (parametriClimatici.isEmpty()) {
            System.out.println("Non sono disponibili dati sui parametri climatici per questa area.");
        } else {
            System.out.println("Parametri climatici per l'area " + areaGeografica + ":");
            System.out.println();

            for (String[] parametro : parametriClimatici) {
                System.out.println("Parametro: " + parametro[0]);
                System.out.println("Numero di rilevazioni: " + parametro[1]);
                System.out.println("Statistica del punteggio: " + parametro[2]);
                System.out.println("Commenti degli operatori: " + parametro[3]);
                System.out.println();
            }
        }
    }

    private static List<String[]> getParametriClimatici(String areaGeografica) {
        List<String[]> risultati = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PARAMETRI_CLIMATICI))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 2 && data[1].equals(areaGeografica)) {
                    String[] risultato = new String[4];
                    risultato[0] = data[2]; // Parametro
                    risultato[1] = getNumeroRilevazioni(areaGeografica, data[2]); // Numero di rilevazioni
                    risultato[2] = getStatisticaPunteggio(areaGeografica, data[2]); // Statistica del punteggio
                    risultato[3] = getCommentiOperatori(areaGeografica, data[2]); // Commenti degli operatori
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static String getNumeroRilevazioni(String areaGeografica, String parametro) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PARAMETRI_CLIMATICI))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 2 && data[1].equals(areaGeografica) && data[2].equals(parametro)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(count);
    }

    private static String getStatisticaPunteggio(String areaGeografica, String parametro) {
        // Calcola e restituisci la statistica del punteggio per l'area e il parametro specificati
        // Puoi implementare la logica per calcolare la statistica adeguata
        return "Statistica del punteggio";
    }

    private static String getCommentiOperatori(String areaGeografica, String parametro) {
        StringBuilder commenti = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PARAMETRI_CLIMATICI))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);

                if (data.length >= 2 && data[1].equals(areaGeografica) && data[2].equals(parametro)) {
                    // Aggiungi i commenti degli operatori separati da una virgola
                    if (data.length >= 4) {
                        if (commenti.length() > 0) {
                            commenti.append(", ");
                        }
                        commenti.append(data[3]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commenti.toString();
    }
}

