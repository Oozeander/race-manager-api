# Test technique PMU

Pré-requis : Ce projet utilise Docker et Docker Compose comme dependances externes !

# Run du projet

### Variables d'environnement

Pour run le projet, il faut fournir les variables d'environnements suivantes (indiqués dans le
application.yml) :

* KAFKA_BOOTSTRAP_SERVERS (**localhost:9092**)
* SPRING_DATASOURCE_URL (**jdbc:postgresql://${IP du conteneur}:5432/postgres**, accès à l'IPv4 via **docker inspect**
  sur l'
  instance postgres)
* SPRING_DATASOURCE_USERNAME (**root**, précisé dans le compose.yml)
* SPRING_DATASOURCE_PASSWORD (**admin**, précisé dans le compose.yml)

### OpenAPI 3.0

L'API a été documenté via OpenAPI 3.0, l'accès à la documentation se fait à travers les 2 liens suivants :

* http://localhost:8080/api/v1/api-docs (JSON)
* http://localhost:8080/api/v1/swagger-ui (Swagger UI)

### Docker Compose support

Ce projet contient un fichier Docker Compose qui définit les différents conteneurs utilisés (lancé automatiquement au
démarrage de l'application) :

* postgres
* pgAdmin
* kafka
* zookeeper

# Lien Github

Le projet a également été push sur un repository public, voici le
lien : [Lien Github](https://github.com/Oozeander/race-manager-api)
