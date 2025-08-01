FROM openjdk:19-jdk-slim

# Installer Maven 3.6.3 (comme votre version locale)
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz | tar -xzC /opt && \
    ln -s /opt/apache-maven-3.6.3/bin/mvn /usr/local/bin/mvn

WORKDIR /app

# Copie depuis le sous-répertoire gestion-alimentaire
COPY gestion-alimentaire/pom.xml .
RUN mvn dependency:go-offline -B

COPY gestion-alimentaire/src ./src
RUN mvn clean install -DskipTests

EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
