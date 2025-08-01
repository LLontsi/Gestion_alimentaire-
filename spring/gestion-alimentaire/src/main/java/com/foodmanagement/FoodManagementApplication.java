package com.foodmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Food Management System
 * 
 * @SpringBootApplication : Annotation qui combine :
 * - @Configuration : Indique que cette classe contient des beans de configuration
 * - @EnableAutoConfiguration : Active la configuration automatique de Spring Boot
 * - @ComponentScan : Active le scan des composants dans le package actuel et ses sous-packages
 */
@SpringBootApplication
public class FoodManagementApplication {

    /**
     * Méthode main - Point d'entrée de l'application
     * Lance l'application Spring Boot
     * 

     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(FoodManagementApplication.class, args);
        
        System.out.println("========================================");
        System.out.println("🍽️  Food Management System démarré !");
        System.out.println("========================================");
        System.out.println("📋 Documentation API : http://localhost:8080/api/documentation");
        System.out.println("🧪 Page de test     : http://localhost:8080/api/test");
        System.out.println("📊 Status système   : http://localhost:8080/api/status");
        System.out.println("========================================");
    }
}