# Déployement

## Sommaire

* [Production](#production)
* [Préprodution](#prprodution)
* [Montée de version](#monte-de-version)

## Production

### Déploiement

* Se placer dans à la racine du projet puis lancer la commande
`mvn clean package` 

*Si dernière un proxy, il est possible que deux tests ne passent pas. auquel cas, ajouter `-DskipTests` à la commande*

* Récupérer le war ainsi généré dans le dossier `kelrisks-presentation/target/kelrisks.war`

* Copier le war sur le serveur de dans le dossier `/var/www/sites/`.

Tomcat déploie automatiquement la nouvelle application.

## Préprodution

### Prérequis

Deux fichiers sont  modifier dans le cas d'un déploiement en préproduction.

#### - `kelrisks-vue/.env.production`

* Remplacer les deux occurences de `https://kelrisks` par `https://preprod.kelrisks`  
 
#### - `kelrisks-presentation/src/main/java/resources/application.properties`

Il s'agit de changer l'URL de la base de donnée avec laquelle l'application travaille.

* Commenter `spring.datasource.url=jdbc:postgresql://localhost:5432/kelrisks` (L4) et

* Décommenter `spring.datasource.url=jdbc:postgresql://localhost:5432/kelrisks_preprod` (L5) 

La suite est très semblable au déploiement en production.

### Déploiement
* Se placer dans à la racine du projet puis lancer la commande
`mvn clean package` 

*Si dernière un proxy, il est possible que deux tests ne passent pas. auquel cas, ajouter `-DskipTests` à la commande*

* Récupérer le war ainsi généré dans le dossier `kelrisks-presentation/target/kelrisks.war`

* **Le renommer en `kelrisks_preprod.war`**

* Copier le war sur le serveur de dans le dossier `/var/www/sites/`.

Tomcat déploie automatiquement la nouvelle application.

## Montée de version

Dans le cas d'une montée de version, le numéro de version dans les fichiers suivants sont à modifier :

* `/pom.xml` (L29)
* `kelrisks-vue/.env` (L1)
* `kelrisks-vue/package.json` (L3)
* `kelrisks-vue/package-lock.json` (L3)

## Annexe

Les alias suivants sont disponibles sur le serveur d'hébergement :

* `tailcat` : 'tail -f /opt/tomcat/logs/catalina.out'
* `rstomcat` : 'sudo systemctl restart tomcat | tailcat'