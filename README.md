## Configuration des variables d'environnement

Avant de lancer l'application, vous devez définir les variables d'environnement suivantes :

A la racine du projet créez un fichier `.env` et ajoutez les lignes suivantes :
```bash
DB_USER=postgres
DB_PASSWORD=monmotdepasse
DB_HOST=localhost
DB_PORT=5432
DB_NAME=bookstore_db
DB_URL=jdbc:postgresql://localhost:5432/bookstore_db

```


# 📦 Base de données du projet avec  DOCKER et POSTGRESQL
## 🚀 Lancement de la base de données avec Docker
Assurez-vous d'avoir Docker installé sur votre machine. Ensuite, exécutez la commande suivante pour lancer un conteneur PostgreSQL avec la base de données nécessaire pour le projet :

```bash
docker compose up -d
```


#Lanvement du projet
Assurez-vous d'avoir Maven et Java installés sur votre machine. :

lancer le build Maven clean install

Puis lancez l'application avec le fichier FirstSpringProjectApplication.java



## 🧪 Test du programme
rendez-vous sur le site web à l'adresse suivante :
http://localhost:8082/bookstore/home

Une fois la bd lancé et le projet lancé, vous pouvez tester le programme en créant un compte ou en se connectant avec le compte administrateur suivant :

- **Login :** `admin@admin.admin`
- **Mot de passe :** `AdminAdmin`

Cela permet d’accéder a l'éditeur de code promo.