# Projet Book Store 

## 1. ⚙️ Configuration des variables d'environnement

Avant de lancer l'application, vous devez définir les variables d'environnement suivantes :

À la racine du projet, créez un fichier `.env` et ajoutez les lignes suivantes :
```
DB_USER=postgres
DB_PASSWORD=monmotdepasse
DB_HOST=localhost
DB_PORT=5432
DB_NAME=bookstore_db
DB_URL=jdbc:postgresql://localhost:5432/bookstore_db
```

## 2.📦 Lancement de PotgreSQL avec Docker 

Depuis un terminal placé à la racine du projet, exécutez :
```bash
docker compose up -d
```

# 3. Lancement de l'application

Puis lancez l'application avec le fichier FirstSpringProjectApplication.java

## 4. 🧪 Test du programme
Rendez-vous sur le site web à l'adresse suivante :
https://localhost:8082/bookstore/home

Une fois la bd lancé et le projet lancé, vous pouvez tester le programme en créant un compte ou en se connectant avec le compte administrateur suivant :

- **Login :** `admin@admin.admin`
- **Mot de passe :** `AdminAdmin`

Cela permet d’accéder a l'éditeur de code promo.