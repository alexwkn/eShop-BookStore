## Configuration des variables d'environnement

Avant de lancer l'application, vous devez définir les variables d'environnement suivantes :

A la racine du projet créez un fichier `.env` et ajoutez les lignes suivantes :
```
DB_USER=postgres
DB_PASSWORD=monmotdepasse
DB_HOST=localhost
DB_PORT=5432
DB_NAME=bookstore_db
DB_URL=jdbc:postgresql://localhost:5432/bookstore_db
```


# 📦 Base de données du projet avec  DOCKER et POSTGRESQL
## 🚀 Lancement de la base de données avec Docker


```bash
docker compose up -d
```


#Lancement du projet


Puis lancez l'application avec le fichier FirstSpringProjectApplication.java

## Création du fichier keystore.p12 
Pour donner à votre site, un accès HTTPS, entrer dans le terminal la ligne suivante : 
```bash
keytool -genkeypair -alias bookstore -keyalg RSA -keysize 4096 -validity 3650 -dname "CN=localhost" -keypass ${SSL_KEYSTORE_PASSWORD} -keystore src/main/resources/keystore.p12 -storetype PKCS12 -storepass ${SSL_KEYSTORE_PASSWORD}
```



## 🧪 Test du programme
rendez-vous sur le site web à l'adresse suivante :
https://localhost:8082/bookstore/home

Une fois la bd lancé et le projet lancé, vous pouvez tester le programme en créant un compte ou en se connectant avec le compte administrateur suivant :

- **Login :** `admin@admin.admin`
- **Mot de passe :** `AdminAdmin`

Cela permet d’accéder a l'éditeur de code promo.