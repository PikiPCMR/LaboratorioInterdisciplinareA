import java.util.*;
public class Main {
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
                    Ricerca.cercaAreaGeografica();
                    break;
                case 2:
                    Accesso.eseguiLogin();
                    break;
                case 3:
                    Accesso.eseguiRegistrazione();
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
}