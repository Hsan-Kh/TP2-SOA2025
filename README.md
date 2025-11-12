# CalculatriceWS - Service Web SOAP

## 📋 Description
Service web SOAP développé avec Jakarta XML Web Services (JAX-WS) permettant d'effectuer des opérations mathématiques de base : addition, soustraction, multiplication et division.

## 🎓 Contexte Académique
- **Matière** : Architecture SOA et Services Web
- **Filière** : LSI3-GLSI
- **TP2** : Mise en œuvre de Jakarta XML Web Services

## 🛠️ Technologies Utilisées
- **Java** : JDK 11 ou supérieur
- **Jakarta XML Web Services** : 4.0.0
- **JAX-WS RT** : 4.0.2
- **Maven** : Gestion des dépendances
- **IntelliJ IDEA** : IDE de développement
- **SOAPUI** : Test des services SOAP


## 🚀 Installation et Configuration

### Prérequis
1. **JDK 11 ou supérieur** installé
2. **Maven** configuré
3. **IntelliJ IDEA** installé
4. **SOAPUI** installé

### Installation
1. Clonez le repository :
   ```bash
   git clone https://github.com/Hsan-Kh/TP2-SOA2025
   cd CalculatriceWS
   ```

2. Importez le projet dans IntelliJ comme projet Maven

3. Attendez que Maven télécharge les dépendances

## ▶️ Démarrage du Service

### Démarrer le serveur
1. Ouvrez la classe `ServeurJWS.java`
2. Exécutez la méthode `main`
3. Vérifiez le message de confirmation dans la console :
   ```
   Service web publié avec succès !
   URL du service : http://localhost:8080/calculatrice
   WSDL disponible à : http://localhost:8080/calculatrice?wsdl
   ```

### Vérifier le WSDL
Ouvrez dans votre navigateur : `http://localhost:8080/calculatrice?wsdl`

## 🧪 Tests avec SOAPUI

### Configuration
1. Lancez SOAPUI
2. Créez un nouveau projet SOAP
3. Importez le WSDL : `http://localhost:8080/calculatrice?wsdl`

### Exemple de requête - Addition
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.calculatrice.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:somme>
         <a>15.5</a>
         <b>24.5</b>
      </ser:somme>
   </soapenv:Body>
</soapenv:Envelope>
```

### Réponse attendue
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:sommeResponse xmlns:ns2="http://service.calculatrice.com/">
         <return>40.0</return>
      </ns2:sommeResponse>
   </S:Body>
</S:Envelope>
```

## 📊 Opérations Disponibles

| Opération       | Description                  | Paramètres    |
|----------------|------------------------------|---------------|
| `somme`        | Addition de deux nombres     | a: double, b: double |
| `multiplication` | Multiplication              | a: double, b: double |
| `soustraction` | Soustraction                 | a: double, b: double |
| `division`     | Division (gestion div/0)     | a: double, b: double |

## 📝 Annotations JAX-WS Utilisées

### @WebService
Marque une classe comme service web. Paramètres utilisés :
- `serviceName` : Nom du service dans le WSDL
- `portName` : Nom du port
- `targetNamespace` : Espace de noms XML
- `endpointInterface` : Interface SEI

### @WebMethod
Expose une méthode comme opération du service web.

### @WebParam
Définit le nom d'un paramètre dans le message SOAP.

## 🔍 Analyse du WSDL

Le WSDL généré contient :
- **Types** : Schéma XSD avec les structures de données
- **Messages** : Définitions des messages d'entrée/sortie
- **PortType** : Interface abstraite (opérations disponibles)
- **Binding** : Protocole SOAP et format des messages
- **Service** : Point d'accès concret du service

## ⚠️ Gestion des Erreurs

### Division par zéro
Le service lève une `ArithmeticException` si le diviseur est 0.

Exemple de réponse d'erreur :
```xml
<soap:Fault>
   <faultcode>soap:Server</faultcode>
   <faultstring>Division par zéro impossible</faultstring>
</soap:Fault>
```

## 🐛 Résolution de Problèmes

### Le serveur ne démarre pas
- Vérifiez que le port 8080 est libre
- Modifiez l'URL si nécessaire dans `ServeurJWS.java`

### SOAPUI ne charge pas le WSDL
- Vérifiez que le serveur est démarré
- Testez l'URL dans un navigateur d'abord

### Erreur de dépendances Maven
```bash
mvn clean install
```

## 📚 Ressources

- [Jakarta XML Web Services Documentation](https://jakarta.ee/specifications/xml-web-services/)
- [SOAPUI Documentation](https://www.soapui.org/docs/)
- [JAX-WS Tutorial](https://docs.oracle.com/javaee/7/tutorial/jaxws.htm)

## 👤 Auteur
[Hsan Khecharem]  
LSI3-GLSI - Architecture SOA et Services Web

## 📅 Date
Novembre 2025

## 📄 Licence
Projet académique - Usage éducatif uniquement
