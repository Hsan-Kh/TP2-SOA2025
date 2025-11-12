package serveur;

import jakarta.xml.ws.Endpoint;
import service.Calculatrice;

/**
 * Serveur pour publier le service web SOAP
 * Cette classe démarre le serveur et publie le service Calculatrice
 */
public class ServeurJWS {

    public static void main(String[] args) {

        String url = "http://localhost:8686/calculatrice";

        System.out.println("Démarrage du serveur JAX-WS...");

        try {
            Endpoint endpoint = Endpoint.publish(url, new Calculatrice());

            System.out.println(" Service web publié avec succès !");
            System.out.println(" URL du service : " + url);
            System.out.println(" WSDL disponible à : " + url + "?wsdl\n");

            System.out.println("Le serveur est en écoute...");
            System.out.println("Appuyez sur Ctrl+C pour arrêter");

            if (endpoint.isPublished()) {
                System.out.println("État du service : ACTIF");
            }

        } catch (Exception e) {
            System.err.println(" Erreur lors de la publication du service :");
            System.err.println("  " + e.getMessage());
            e.printStackTrace();
        }
    }
}