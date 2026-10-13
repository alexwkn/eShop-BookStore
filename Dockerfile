# --- Build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# --- Run ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/*.war app.war
COPY images ./images

ENV SSL_KEYSTORE=file:/app/keystore.p12
EXPOSE 8080 8082

# Genere un certificat HTTPS auto-signe au premier demarrage, puis lance l'application
CMD ["sh", "-c", "[ -f /app/keystore.p12 ] || keytool -genkeypair -alias bookstore -keyalg RSA -keysize 2048 -storetype PKCS12 -validity 3650 -dname 'CN=localhost' -keystore /app/keystore.p12 -storepass \"$SSL_KEYSTORE_PASSWORD\" -keypass \"$SSL_KEYSTORE_PASSWORD\" && exec java -jar app.war"]
