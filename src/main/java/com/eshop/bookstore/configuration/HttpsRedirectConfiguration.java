package com.eshop.bookstore.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ouvre un port HTTP supplementaire qui redirige toutes les requetes vers le port HTTPS.
 * Exemple : http://localhost:8080/home -> https://localhost:8082/home
 */
@Configuration
public class HttpsRedirectConfiguration {

    @Value("${server.http-port:8080}")
    private int httpPort;

    @Value("${server.port:8082}")
    private int httpsPort;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> httpToHttpsRedirect() {
        return factory -> {
            // Toute requete doit passer par une connexion securisee (HTTPS)
            factory.addContextCustomizers(context -> {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            });

            // Connecteur HTTP qui redirige vers le port HTTPS
            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            connector.setScheme("http");
            connector.setPort(httpPort);
            connector.setSecure(false);
            connector.setRedirectPort(httpsPort);
            factory.addAdditionalTomcatConnectors(connector);
        };
    }
}
