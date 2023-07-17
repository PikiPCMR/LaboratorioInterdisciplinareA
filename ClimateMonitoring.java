import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class ClimateMonitoring {
        private static final String FILE_PATH = "operatori-registrati.csv";

        public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);
            boolean loggedIn = false;
            String username = null;

            while (true) {
                System.out.println("Benvenuto!");
                System.out.println("1. Ricerca");
                System.out.println("2. Login");
                System.out.println("3. Registrazione");
                System.out.println("0. Esci");

                int scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il carattere di fine linea dopo la lettura del numero

                switch (scelta) {
                    case 1:
                        if (loggedIn) {
                            System.out.println("Effettua la tua ricerca...");
                            // Esegui la ricerca
                        } else {
                            System.out.println("Effettua prima il login.");
                        }
                        break;
                    case 2:
                        if (loggedIn) {
                            System.out.println("Sei già loggato come " + username);
                        } else {
                            System.out.println("Login");
                            System.out.print("Username: ");
                            String loginUsername = scanner.nextLine();
                            System.out.print("Password: ");
                            String loginPassword = scanner.nextLine();

                            if (effettuaLogin(loginUsername, loginPassword)) {
                                loggedIn = true;
                                username = loginUsername;
                                System.out.println("Accesso effettuato con successo!");
                            } else {
                                System.out.println("Username o password non validi.");
                            }
                        }
                        break;
                    case 3:
                        if (loggedIn) {
                            System.out.println("Sei già loggato come " + username);
                        } else {
                            System.out.println("Registrazione");
                            System.out.print("Username: ");
                            String registrazioneUsername = scanner.nextLine();
                            System.out.print("Password: ");
                            String registrazionePassword = scanner.nextLine();

                            if (effettuaRegistrazione(registrazioneUsername, registrazionePassword)) {
                                System.out.println("Registrazione effettuata con successo!");
                            } else {
                                System.out.println("Errore durante la registrazione.");
                            }
                        }
                        break;
                    case 0:
                        System.out.println("Arrivederci!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            }
        }

        private static boolean effettuaLogin(String username, String password) throws IOException {
            // Verifica le credenziali di login con il file CSV
            // Esempio di implementazione semplificata:
            try (Scanner fileScanner = new Scanner(FILE_PATH)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] fields = line.split(",");

                    if (fields.length == 2 && fields[0].equals(username) && fields[1].equals(password)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private static boolean effettuaRegistrazione(String username, String password) {
            // Registra un nuovo utente nel file CSV
            // Esempio di implementazione semplificata:
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(username + "," + password);
                writer.newLine();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
    }