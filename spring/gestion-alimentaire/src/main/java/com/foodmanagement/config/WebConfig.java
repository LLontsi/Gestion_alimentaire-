package com.foodmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Configuration Web pour Spring MVC
 * Gère les intercepteurs, la gestion des ressources statiques, et les paramètres web
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.images-dir:uploads/images/}")
    private String imagesDirectory;

    /**
     * Configuration CORS globale
     * Applique les règles CORS à toute l'application
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * Configuration des ressources statiques
     * Permet de servir les images uploadées
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Servir les images uploadées
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + imagesDirectory)
                .setCachePeriod(3600); // Cache d'1 heure

        // Servir les ressources statiques par défaut
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(86400); // Cache d'1 jour

        // Documentation Swagger
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .setCachePeriod(86400);
    }

    /**
     * Configuration des intercepteurs
     * Ajoute des traitements avant/après les requêtes
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Intercepteur de logging des requêtes
        registry.addInterceptor(new RequestLoggingInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/images/fichier/**"); // Pas de log pour les images

        // Intercepteur de performance
        registry.addInterceptor(new PerformanceInterceptor())
                .addPathPatterns("/api/**");
    }

    /**
     * Configuration des convertisseurs de messages
     * Gère la sérialisation JSON
     */
    @Override
    public void configureMessageConverters(java.util.List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
        // Configuration automatique de Jackson via Spring Boot
        // Cette méthode permet des personnalisations si nécessaire
    }

    /**
     * Configuration de la gestion des exceptions
     */
    @Override
    public void configureHandlerExceptionResolvers(java.util.List<org.springframework.web.servlet.HandlerExceptionResolver> resolvers) {
        // Configuration automatique via @ControllerAdvice
        // Cette méthode permet des personnalisations si nécessaire
    }

    /**
     * Configuration des vues
     * Pour le rendu de templates (non utilisé dans notre API REST)
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // Non utilisé dans une API REST pure
        // Pourrait être utile pour servir des pages d'administration
    }

    /**
     * Configuration des formatters
     * Pour la conversion de types dans les paramètres
     */
    @Override
    public void addFormatters(org.springframework.format.FormatterRegistry registry) {
        // Formatters personnalisés si nécessaire
        // Par exemple pour les enum ou les dates
    }
    

    /**
     * Intercepteur pour le logging des requêtes
     * Log les informations de chaque requête API
     */
    public static class RequestLoggingInterceptor implements HandlerInterceptor {
        
        @Override
        public boolean preHandle(HttpServletRequest request, 
                               HttpServletResponse response, 
                               Object handler) throws Exception {
            
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String userAgent = request.getHeader("User-Agent");
            String remoteAddr = request.getRemoteAddr();
            
            System.out.printf("[%s] %s %s - User-Agent: %s - IP: %s%n", 
                            java.time.LocalDateTime.now(), method, uri, userAgent, remoteAddr);
            
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  Object handler, Exception ex) throws Exception {
            
            if (ex != null) {
                System.err.printf("Erreur lors du traitement de %s %s: %s%n", 
                                request.getMethod(), request.getRequestURI(), ex.getMessage());
            }
        }
    }

    /**
     * Intercepteur pour mesurer les performances
     * Mesure le temps de traitement des requêtes
     */
    public static class PerformanceInterceptor implements HandlerInterceptor {
        
        @Override
        public boolean preHandle(HttpServletRequest request, 
                               HttpServletResponse response, 
                               Object handler) throws Exception {
            
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
            
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  Object handler, Exception ex) throws Exception {
            
            Long startTime = (Long) request.getAttribute("startTime");
            if (startTime != null) {
                long duration = System.currentTimeMillis() - startTime;
                
                // Log des requêtes lentes (> 1 seconde)
                if (duration > 1000) {
                    System.out.printf("REQUÊTE LENTE: %s %s - Durée: %d ms%n", 
                                    request.getMethod(), request.getRequestURI(), duration);
                }
            }
        }
    }
}