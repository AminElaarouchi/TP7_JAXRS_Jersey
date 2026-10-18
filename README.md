# TP7 — JAX-RS et Jersey

Projet du [TP 7 du cours Architecture Microservices](https://mliaedu.toubkalit.com/student/courses/7/labs/161). L'application Spring Boot utilise Jersey pour exposer un service bancaire JAX-RS, Spring Data JPA pour la persistance et H2 en mémoire. Trois comptes sont initialisés au démarrage.

## Exécuter

Prérequis : JDK 17 et Maven 3.6+.

```sh
mvn clean test
mvn spring-boot:run
```

Le service écoute sur le port 8082. Console H2 : <http://localhost:8082/h2-console> ; URL JDBC : `jdbc:h2:mem:banque`.

## API

| Méthode | Route | Fonction |
| --- | --- | --- |
| GET | `/banque/comptes` | Liste JSON ou XML selon `Accept` |
| GET | `/banque/comptes/{id}` | Détail JSON ou XML, 404 si absent |
| POST | `/banque/comptes` | Créer, 201 |
| PUT | `/banque/comptes/{id}` | Mettre à jour, 404 si absent |
| DELETE | `/banque/comptes/{id}` | Supprimer, 204 ou 404 |

```sh
curl -H "Accept: application/json" http://localhost:8082/banque/comptes
curl -H "Accept: application/xml" http://localhost:8082/banque/comptes
```

Le type de compte est enregistré comme chaîne (`COURANT` ou `EPARGNE`) pour que la base reste lisible et que l'ordre de l'énumération puisse évoluer. `CompteList` fournit une racine JAXB pour les réponses XML contenant plusieurs comptes.
