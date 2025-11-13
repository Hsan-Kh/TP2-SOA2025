# CalculatriceWS - Service Web SOAP avec Client

## 📋 Description
Projet complet d'un service web SOAP développé avec Jakarta XML Web Services (JAX-WS) permettant d'effectuer des opérations mathématiques de base : addition, soustraction, multiplication et division. Le projet comprend à la fois le serveur et le client SOAP.

## 🎓 Contexte Académique
- **Matière** : Architecture SOA et Services Web
- **Filière** : LSI3-GLSI
- **TP2** : Mise en œuvre de Jakarta XML Web Services
- **Objectifs** : 
  - Créer un service web SOAP avec JAX-WS
  - Publier et consommer un service web
  - Générer un client SOAP via wsimport
  - Comprendre l'architecture client-serveur distribuée
  - Tester les services avec SOAPUI

## 🛠️ Technologies Utilisées
- **Java** : JDK 11 ou supérieur
- **Jakarta XML Web Services (JAX-WS)** : 4.0.0
- **JAX-WS RT** : 4.0.2
- **Maven** : Gestion des dépendances et build
- **IntelliJ IDEA** : IDE de développement
- **SOAPUI** : Test des services SOAP
- **wsimport** : Génération du proxy client à partir du WSDL



---

## 🚀 PARTIE 1 : SERVICE WEB (SERVEUR)

### Installation et Configuration

#### Prérequis
1. **JDK 11 ou supérieur** installé
2. **Maven** configuré
3. **IntelliJ IDEA** installé
4. **SOAPUI** installé

#### Installation
1. Clonez le repository :
   ```bash
   git clone https://github.com/Hsan-Kh/TP2-SOA2025
   ```

2. Importez le projet dans IntelliJ comme projet Maven

3. Attendez que Maven télécharge les dépendances

### Démarrage du Service

#### Démarrer le serveur
1. Ouvrez la classe `ServeurJWS.java` dans le package `serveur`
2. Exécutez la 
3. Vérifiez le message de confirmation dans la console 


#### Vérifier le WSDL
Ouvrez dans votre navigateur : `http://localhost:8686/calculatrice?wsdl`

Vous devriez voir le document WSDL XML complet définissant le contrat du service.

### Tests avec SOAPUI

#### Configuration
1. Lancez SOAPUI
2. Créez un nouveau projet SOAP : **File → New SOAP Project**
3. Renseignez :
   - **Project Name** : CalculatriceWS
   - **Initial WSDL** : `http://localhost:8686/calculatrice?wsdl`
   - Cochez **Create Requests**
4. Cliquez sur **OK**

#### Exemple de requête - Addition
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.calculatrice.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:somme>
         <a>15.5</a>
         <b>24.5</b>
      </ser:somme>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Réponse attendue
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:sommeResponse xmlns:ns2="http://service.calculatrice.com/">
         <return>40.0</return>
      </ns2:sommeResponse>
   </S:Body>
</S:Envelope>
```

### Opérations Disponibles

| Opération       | Description                  | Paramètres           | Exemple |
|----------------|------------------------------|----------------------|---------|
| `somme`        | Addition de deux nombres     | a: double, b: double | 15.5 + 24.5 = 40.0 |
| `multiplication` | Multiplication              | a: double, b: double | 5.0 × 7.0 = 35.0 |
| `soustraction` | Soustraction                 | a: double, b: double | 100.0 - 37.0 = 63.0 |
| `division`     | Division (gestion div/0)     | a: double, b: double | 20.0 ÷ 4.0 = 5.0 |

### Annotations JAX-WS Utilisées

#### @WebService
Marque une classe ou interface comme service web. Paramètres utilisés :
- `name` : Nom du portType dans le WSDL
- `serviceName` : Nom du service dans le WSDL
- `portName` : Nom du port dans le WSDL
- `targetNamespace` : Espace de noms XML du service
- `endpointInterface` : Référence l'interface SEI (Service Endpoint Interface)

**Exemple :**
```java
@WebService(
    name = "ICalculatrice",
    serviceName = "CalculatriceService",
    portName = "CalculatricePort",
    targetNamespace = "http://service.calculatrice.com/",
    endpointInterface = "service.ICalculatrice"
)
public class Calculatrice implements ICalculatrice {
    // ...
}
```

#### @WebMethod
Expose une méthode comme opération du service web.
- `operationName` : Nom de l'opération dans le WSDL

**Exemple :**
```java
@WebMethod(operationName = "somme")
public double somme(double a, double b) {
    return a + b;
}
```

#### @WebParam
Définit le nom d'un paramètre dans le message SOAP.
- `name` : Nom du paramètre dans le message SOAP

**Exemple :**
```java
@WebMethod(operationName = "somme")
public double somme(@WebParam(name = "a") double a, 
                   @WebParam(name = "b") double b)
```

### Analyse du WSDL

Le WSDL (Web Services Description Language) généré automatiquement contient :

#### 1. Types (XSD Schema)
Définit les structures de données XML pour les requêtes et réponses :
```xml
<types>
  <xsd:schema targetNamespace="http://service.calculatrice.com/">
    <xsd:element name="somme">
      <xsd:complexType>
        <xsd:sequence>
          <xsd:element name="a" type="xsd:double"/>
          <xsd:element name="b" type="xsd:double"/>
        </xsd:sequence>
      </xsd:complexType>
    </xsd:element>
    <xsd:element name="sommeResponse">
      <xsd:complexType>
        <xsd:sequence>
          <xsd:element name="return" type="xsd:double"/>
        </xsd:sequence>
      </xsd:complexType>
    </xsd:element>
  </xsd:schema>
</types>
```

#### 2. Messages
Définit les messages d'entrée et de sortie pour chaque opération.

#### 3. PortType
Interface abstraite définissant toutes les opérations disponibles du service.

#### 4. Binding
Spécifie le protocole SOAP et le format des messages (document/literal).

#### 5. Service
Définit le point d'accès concret du service avec l'URL du endpoint.

### Gestion des Erreurs

#### Division par zéro
Le service lève une `ArithmeticException` si le diviseur est 0.

**Exemple de réponse d'erreur (SOAP Fault) :**
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <S:Fault>
         <faultcode>S:Server</faultcode>
         <faultstring>Division par zéro impossible</faultstring>
      </S:Fault>
   </S:Body>
</S:Envelope>
```

---

## 💻 PARTIE 2 : CLIENT SOAP

### Introduction au Client SOAP

Le client SOAP permet de consommer le service web à distance. Il utilise des **classes proxy (stubs)** générées automatiquement à partir du WSDL via l'outil **wsimport**.

### Architecture Client-Serveur

```
┌─────────────────┐                    ┌─────────────────┐
│  Client Java    │                    │  Serveur JAX-WS │
│                 │                    │                 │
│  ClientSOAP     │◄───SOAP/HTTP────►│  Calculatrice   │
│                 │                    │                 │
│  Proxy (Stub)   │                    │  ServeurJWS     │
└─────────────────┘                    └─────────────────┘
```

**Le proxy :**
- Convertit les appels de méthodes Java en requêtes SOAP XML
- Envoie les requêtes au serveur via HTTP
- Reçoit et traite les réponses XML du serveur
- Retourne les résultats au client sous forme d'objets Java

### Génération du Proxy Client avec wsimport

#### Étape 1 : Configuration Maven (déjà fait)

Le `pom.xml` contient le plugin `jaxws-maven-plugin` :
```xml
<plugin>
    <groupId>com.sun.xml.ws</groupId>
    <artifactId>jaxws-maven-plugin</artifactId>
    <version>4.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>wsimport</goal>
            </goals>
            <configuration>
                <wsdlUrls>
                    <wsdlUrl>http://localhost:8686/calculatrice?wsdl</wsdlUrl>
                </wsdlUrls>
                <packageName>clientsoap.generated</packageName>
                <keep>true</keep>
                <verbose>true</verbose>
            </configuration>
        </execution>
    </executions>
</plugin>
```

#### Étape 2 : Démarrer le Serveur

**IMPORTANT** : Le serveur DOIT être démarré avant de générer les classes proxy !

```bash
# Dans le projet, exécutez ServeurJWS.java
Run → ServeurJWS.main()
```

Vérifiez que le message "Service web publié avec succès !" apparaît dans la console.

#### Étape 3 : Générer les Classes Proxy

**Méthode Recommandée - Via Maven Tool Window d'IntelliJ :**
1. Ouvrez l'onglet **Maven** (côté droit d'IntelliJ)
2. Déroulez **CalculatriceWS → Plugins → jaxws**
3. Double-cliquez sur **jaxws:wsimport**
4. Attendez le message "BUILD SUCCESS" dans la console

**Méthode Alternative - Via Terminal :**
```bash
mvn clean jaxws:wsimport
```

#### Étape 4 : Vérifier la Génération

Les classes proxy sont générées dans :
```
target/generated-sources/wsimport/client/generated/
├── ICalculatrice.java              # Interface du proxy (Port)
├── CalculatriceService.java        # Factory du service
├── Division.java
├── DivisionResponse.java
├── Multiplication.java
├── MultiplicationResponse.java
├── Somme.java
├── SommeResponse.java
├── Soustraction.java
├── SoustractionResponse.java
├── ObjectFactory.java
└── package-info.java
```

#### Étape 5 : Marquer comme Generated Sources

Pour qu'IntelliJ reconnaisse les classes générées :
1. Clic droit sur `target/generated-sources/wsimport`
2. **Mark Directory as → Generated Sources Root**
3. Le dossier devient bleu/vert dans l'arborescence

### Utilisation du Client

#### Classes Générées Principales

**CalculatriceService** : Factory pour créer le proxy
```java
CalculatriceService service = new CalculatriceService();
```

**ICalculatrice** : Interface du proxy pour appeler les méthodes distantes
```java
ICalculatrice port = service.getCalculatricePort();
```

#### Client Simple (exemple)

```java
package client;

import client.generated.ICalculatrice;
import client.generated.CalculatriceService;

public class ClientSimple {
    public static void main(String[] args) {
        try {
            System.out.println("=== Client SOAP - Service Calculatrice ===\n");
            
            // 1. Créer le service (factory)
            CalculatriceService service = new CalculatriceService();
            
            // 2. Obtenir le proxy (port)
            ICalculatrice port = service.getCalculatricePort();
            
            // 3. Appeler les méthodes comme si elles étaient locales
            double r1 = port.somme(15.5, 24.5);
            System.out.println("15.5 + 24.5 = " + r1);
            
            double r2 = port.multiplication(5, 7);
            System.out.println("5 × 7 = " + r2);
            
            double r3 = port.soustraction(100, 37);
            System.out.println("100 - 37 = " + r3);
            
            double r4 = port.division(20, 4);
            System.out.println("20 ÷ 4 = " + r4);
            
            System.out.println("\n✓ Tous les tests réussis !");
            
        } catch (Exception e) {
            System.err.println("✗ Erreur : " + e.getMessage());
        }
    }
}
```

#### Exécution du Client

1. **Assurez-vous que le serveur est démarré** 
2. Exécutez `ClientSOAP.java`


### Flux de Communication SOAP

#### Quand vous appelez `port.somme(10, 5)` :

```
┌─────────────────────────────────────────────┐
│ 1. Client appelle : port.somme(10, 5)      │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│ 2. Le proxy construit une requête SOAP :   │
│    <somme><a>10</a><b>5</b></somme>        │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│ 3. Envoi HTTP POST vers le serveur         │
│    URL: http://localhost:8686/calculatrice  │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│ 4. Le serveur traite la requête            │
│    Exécute : return 10 + 5;                │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│ 5. Le serveur renvoie la réponse SOAP :    │
│    <return>15.0</return>                   │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│ 6. Le proxy extrait le résultat            │
│    Retourne : double = 15.0                │
└─────────────────────────────────────────────┘
```

### Pourquoi le Serveur Doit Rester Actif ?

Le client **dépend** du serveur à l'exécution car :

#### 1. Architecture Distribuée
```
Client = Interface utilisateur
Serveur = Logique métier + Données
```

Le proxy ne contient PAS le code de calcul (`a + b`), il envoie juste la requête au serveur qui effectue le calcul.

#### 2. Avantages de cette Architecture

| Avantage | Description |
|----------|-------------|
| **Centralisation** | Un seul serveur pour tous les clients |
| **Mise à jour facile** | Modifier le serveur sans redistribuer le client |
| **Sécurité** | La logique métier reste protégée sur le serveur |
| **Scalabilité** | Plusieurs clients peuvent utiliser le même service |
| **Interopérabilité** | Clients en Java, .NET, Python peuvent tous consommer le même service |

#### 3. Analogie Simple

```
Client = Application mobile Netflix 📱
Serveur = Serveurs Netflix avec les films 🎬

L'application ne contient PAS les films !
Elle demande au serveur de streamer le contenu.
```

### Comparaison : wsimport (SOAP) vs idlj (CORBA)

| Aspect | wsimport (SOAP/JAX-WS) | idlj (CORBA) |
|--------|------------------------|--------------|
| **Technologie** | Services Web SOAP | CORBA |
| **Fichier source** | WSDL (XML) | IDL (Interface Definition Language) |
| **Protocole** | SOAP/HTTP | IIOP (Internet Inter-ORB Protocol) |
| **Génération** | Proxy client uniquement | Stubs (client) + Skeletons (serveur) |
| **Format données** | XML (texte) | Binaire |
| **Plateforme** | Multi-plateformes (Web) | Principalement systèmes distribués |
| **Utilisation** | Moderne (Jakarta EE) | Legacy (technologie ancienne) |

**Points communs** :
- Les deux génèrent des proxies/stubs à partir d'un contrat
- Permettent d'appeler des méthodes distantes comme si elles étaient locales
- Masquent la complexité de la communication réseau
- Basés sur une définition d'interface

---

## 🐛 Résolution de Problèmes

### Problèmes Serveur

#### Le serveur ne démarre pas
**Solutions :**
- Vérifiez que le port 8686 est libre
- Modifiez le port dans `ServeurJWS.java` si nécessaire
- Sur Windows : `netstat -ano | findstr :8686`
- Vérifiez qu'aucun autre serveur n'utilise le port

#### SOAPUI ne charge pas le WSDL
**Solutions :**
- Vérifiez que le serveur est démarré
- Testez l'URL `http://localhost:8686/calculatrice?wsdl` dans un navigateur
- Désactivez temporairement le pare-feu
- Vérifiez que SOAPUI a accès à Internet

### Problèmes Client

#### Erreur "Package client.generated does not exist"
**Cause :** Les classes proxy n'ont pas été générées

**Solution :**
1. Démarrez d'abord le serveur (`ServeurJWS`)
2. Vérifiez que le WSDL est accessible dans le navigateur
3. Dans IntelliJ : Maven → Plugins → jaxws → wsimport
4. Attendez "BUILD SUCCESS"
5. Marquez `target/generated-sources/wsimport` comme Generated Sources Root

#### Erreur "Connection refused" à l'exécution
**Cause :** Le serveur n'est pas démarré ou accessible

**Solution :**
1. Démarrez `ServeurJWS.java`
2. Vérifiez le message "Service publié avec succès"
3. Testez le WSDL dans le navigateur
4. Relancez le client

#### Les classes générées ne sont pas reconnues par IntelliJ
**Solution :**
1. Clic droit sur `target/generated-sources/wsimport`
2. **Mark Directory as → Generated Sources Root**
3. Si ça ne fonctionne pas : **File → Invalidate Caches / Restart**

#### Erreur "Cannot resolve symbol 'Calculatrice'"
**Cause :** Vous essayez d'importer la classe d'implémentation qui n'existe pas côté client


### Problèmes Maven

#### Erreur de dépendances Maven
```bash
mvn clean install -U
```

#### Le plugin jaxws n'apparaît pas dans Maven Tool Window
**Solution :**
1. Clic droit sur le projet dans Maven Tool Window
2. **Reload Project** 🔄
3. Attendez le téléchargement des dépendances
4. Vérifiez votre connexion Internet

---

## 📚 Ressources et Documentation

### Documentation Officielle
- [Jakarta XML Web Services Specification](https://jakarta.ee/specifications/xml-web-services/)
- [JAX-WS Tutorial - Oracle](https://docs.oracle.com/javaee/7/tutorial/jaxws.htm)
- [SOAPUI Documentation](https://www.soapui.org/docs/)
- [Maven JAX-WS Plugin](https://github.com/eclipse-ee4j/metro-jax-ws)

### Standards et Protocoles
- [SOAP 1.2 Specification](https://www.w3.org/TR/soap12/)
- [WSDL 1.1 Specification](https://www.w3.org/TR/wsdl/)
- [Web Services Architecture](https://www.w3.org/TR/ws-arch/)

---

## 📝 Concepts Clés à Retenir

### Architecture SOA (Service-Oriented Architecture)
- **Service** : Unité fonctionnelle autonome, réutilisable et accessible via le réseau
- **Contrat (WSDL)** : Définit les opérations, les messages et les types de données
- **Distribution** : Client et serveur communiquent via le réseau (HTTP/SOAP)
- **Interopérabilité** : Indépendance des langages et plateformes

### JAX-WS (Jakarta XML Web Services)
- Framework Java pour créer et consommer des services web SOAP
- Utilise des annotations pour simplifier le développement
- Génère automatiquement le WSDL à partir du code Java
- Supporte les styles document/literal et RPC/encoded

### Annotations JAX-WS
- `@WebService` : Marque une classe comme service web
- `@WebMethod` : Expose une méthode comme opération
- `@WebParam` : Définit les paramètres des méthodes

### wsimport
- Outil de génération de code client à partir du WSDL
- Crée des classes Java (proxy/stub) correspondant au contrat
- Masque toute la complexité de la communication SOAP
- Permet d'appeler des méthodes distantes comme si elles étaient locales

### WSDL (Web Services Description Language)
- Document XML décrivant le service web
- Contient les types de données, messages, opérations et endpoints
- Sert de contrat entre le client et le serveur
- Généré automatiquement par JAX-WS ou écrit manuellement

---




## 👤 Auteur
**Hsan Khecharem**  
LSI3-GLSI - Architecture SOA et Services Web

## 📅 Date
Novembre 2025

## 🔗 Liens du Projet
- **Repository GitHub** : https://github.com/Hsan-Kh/TP2-SOA2025

## 📄 Licence
Projet académique - Usage éducatif uniquement

---

**🎉 Félicitations ! Vous avez maintenant un service web SOAP complet avec serveur et client fonctionnels !**
