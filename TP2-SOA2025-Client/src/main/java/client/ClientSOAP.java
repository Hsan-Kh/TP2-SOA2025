package client;

import client.generated.ICalculatrice;
import client.generated.CalculatriceService;

import java.util.Scanner;

/**
 * Client SOAP interactif avec menu utilisateur
 * Permet de tester toutes les opérations de la calculatrice
 */
public class ClientSOAP {

    private static ICalculatrice port;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            // Initialisation du service
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║   Client SOAP - Calculatrice Interactive   ║");
            System.out.println("╚════════════════════════════════════════════╝\n");

            System.out.println("Connexion au service web...");
            CalculatriceService service = new CalculatriceService();
            port = service.getCalculatricePort();
            System.out.println(" Connexion établie avec succès !\n");

            // Boucle du menu principal
            boolean continuer = true;
            while (continuer) {
                continuer = afficherMenuEtTraiter();
            }

            System.out.println("\nMerci d'avoir utilisé le client SOAP !");

        } catch (Exception e) {
            System.err.println(" Erreur de connexion au service :");
            System.err.println("  " + e.getMessage());
            System.err.println("\nAssurez-vous que le serveur est démarré.");
        } finally {
            scanner.close();
        }
    }


    private static boolean afficherMenuEtTraiter() {
        afficherMenu();

        System.out.print("Votre choix : ");
        int choix = lireEntier();
        System.out.println();

        switch (choix) {
            case 1:
                effectuerSomme();
                break;
            case 2:
                effectuerMultiplication();
                break;
            case 3:
                effectuerSoustraction();
                break;
            case 4:
                effectuerDivision();
                break;
            case 5:
                effectuerTousLesTests();
                break;
            case 0:
                return false;
            default:
                System.out.println(" Choix invalide !\n");
        }

        return true;
    }


    private static void afficherMenu() {
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│         MENU PRINCIPAL               │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│  1. Addition                         │");
        System.out.println("│  2. Multiplication                   │");
        System.out.println("│  3. Soustraction                     │");
        System.out.println("│  4. Division                         │");
        System.out.println("│  5. Tester toutes les opérations     │");
        System.out.println("│  0. Quitter                          │");
        System.out.println("└──────────────────────────────────────┘");
    }


    private static void effectuerSomme() {
        System.out.println("═══ ADDITION ═══");
        double a = lireNombre("Entrez le premier nombre : ");
        double b = lireNombre("Entrez le deuxième nombre : ");

        try {
            double resultat = port.somme(a, b);
            System.out.println("\n Résultat : " + a + " + " + b + " = " + resultat + "\n");
        } catch (Exception e) {
            System.err.println(" Erreur : " + e.getMessage() + "\n");
        }
    }


    private static void effectuerMultiplication() {
        System.out.println("═══ MULTIPLICATION ═══");
        double a = lireNombre("Entrez le premier nombre : ");
        double b = lireNombre("Entrez le deuxième nombre : ");

        try {
            double resultat = port.multiplication(a, b);
            System.out.println("\n Résultat : " + a + " × " + b + " = " + resultat + "\n");
        } catch (Exception e) {
            System.err.println(" Erreur : " + e.getMessage() + "\n");
        }
    }


    private static void effectuerSoustraction() {
        System.out.println("═══ SOUSTRACTION ═══");
        double a = lireNombre("Entrez le premier nombre : ");
        double b = lireNombre("Entrez le deuxième nombre : ");

        try {
            double resultat = port.soustraction(a, b);
            System.out.println("\n Résultat : " + a + " - " + b + " = " + resultat + "\n");
        } catch (Exception e) {
            System.err.println(" Erreur : " + e.getMessage() + "\n");
        }
    }


    private static void effectuerDivision() {
        System.out.println("═══ DIVISION ═══");
        double a = lireNombre("Entrez le dividende : ");
        double b = lireNombre("Entrez le diviseur : ");

        try {
            double resultat = port.division(a, b);
            System.out.println("\n Résultat : " + a + " ÷ " + b + " = " + resultat + "\n");
        } catch (Exception e) {
            System.err.println(" Erreur : " + e.getMessage());
            System.err.println("  (Division par zéro ?)\n");
        }
    }

    /**
     * Effectue tous les tests automatiquement
     */
    private static void effectuerTousLesTests() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("    TESTS AUTOMATIQUES");
        System.out.println("═══════════════════════════════════════\n");

        try {
            // Test 1 : Addition
            double res1 = port.somme(10, 5);
            System.out.println("Test 1 - Addition : 10 + 5 = " + res1);

            // Test 2 : Multiplication
            double res2 = port.multiplication(6, 7);
            System.out.println("Test 2 - Multiplication : 6 × 7 = " + res2);

            // Test 3 : Soustraction
            double res3 = port.soustraction(50, 23);
            System.out.println("Test 3 - Soustraction : 50 - 23 = " + res3);

            // Test 4 : Division
            double res4 = port.division(100, 4);
            System.out.println("Test 4 - Division : 100 ÷ 4 = " + res4);

            // Test 5 : Division par zéro
            System.out.print("Test 5 - Division par zéro : ");
            try {
                port.division(10, 0);
                System.out.println(" ÉCHEC (pas d'exception)");
            } catch (Exception e) {
                System.out.println(" Exception capturée");
            }

            System.out.println("\n Tous les tests terminés !\n");

        } catch (Exception e) {
            System.err.println(" Erreur pendant les tests : " + e.getMessage() + "\n");
        }
    }


    private static double lireNombre(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("Erreur ! Entrez un nombre valide : ");
            scanner.next();
        }
        return scanner.nextDouble();
    }


    private static int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("Erreur ! Entrez un nombre entier : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
