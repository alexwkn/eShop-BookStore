# eShop-BookStore

Projet de groupe réalisé en 3e année de Bachelier en développement d'applications (Henallux, 2025-2026).

Boutique en ligne de livres et d'accessoires de lecture, développée avec **Spring Boot** (architecture MVC, pages JSP), **Spring Security** et **PostgreSQL**.

## Fonctionnalités

- Catalogue de produits par catégorie, avec fiche détaillée
- Panier, application de codes promo et passage de commande
- Paiement via **PayPal Sandbox** (environnement de test, aucun vrai paiement)
- Inscription, connexion et modification du profil
- Espace administrateur pour gérer les codes promo
- Interface bilingue français / anglais
- Sécurité : HTTPS, protection CSRF, Content Security Policy, nettoyage des entrées contre le XSS, mots de passe hachés avec BCrypt
- Tests unitaires (JUnit, Mockito)

## Stack technique

| Couche | Technologies |
|---|---|
| Back-end | Java 17, Spring Boot 2.5, Spring MVC, Spring Data JPA (Hibernate), Spring Security |
| Vues | JSP, JSTL, Apache Tiles, Bootstrap |
| Base de données | PostgreSQL 14 |
| Déploiement | Docker, Docker Compose |

## Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (avec Docker Compose)

## Démarrage

```bash
git clone https://github.com/alexwkn/eShop-BookStore.git
cd eShop-BookStore
docker compose up --build
```

Au premier démarrage, Docker :
- compile l'application avec Maven ;
- crée la base PostgreSQL et l'initialise avec les données de test (`DB/init.sql`) ;
- génère un certificat HTTPS auto-signé ;
- lance l'application.

L'application est ensuite accessible sur **https://localhost:8082/bookstore**.

Le certificat étant auto-signé, le navigateur affiche un avertissement : cliquer sur **Paramètres avancés**, puis **Continuer vers localhost**.

Pour arrêter : `Ctrl+C` puis `docker compose down` (ajouter `-v` pour supprimer aussi la base et repartir des données de test).

### Configuration (optionnelle)

Le projet fonctionne sans configuration. Pour changer les identifiants de la base ou le mot de passe du certificat :

```bash
cp .env.example .env
```

puis modifier les valeurs dans `.env` avant de lancer `docker compose up --build`.

## Compte de test

| Rôle | Email | Mot de passe |
|---|---|---|
| Administrateur | `admin@admin.admin` | `AdminAdmin` |

Le compte administrateur donne accès à l'éditeur de codes promo. Pour tester le parcours client, créer un compte via **S'inscrire**.

## Structure du projet

```
.
├── DB/init.sql              # Schéma et données de test
├── images/                  # Images des produits
├── src/main/java/com/eshop/bookstore/  # Contrôleurs, services, DAO, entités, configuration
├── src/main/resources/      # application.yml, traductions FR/EN
├── src/main/webapp/         # Pages JSP, CSS, images du site
├── src/test/                # Tests unitaires
├── Dockerfile
└── docker-compose.yml
```

## Auteurs

- Nathan Feltesse – [@NathFELT](https://github.com/NathFELT)
- Antoine-Vi Son Nguyen – [@Drastae](https://github.com/Drastae)
- Alexandre Wenkin – [@alexwkn](https://github.com/alexwkn)
