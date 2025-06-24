
# Usa una imagen base oficial de OpenJDK estable
FROM openjdk:17-jdk-slim  

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /Examen3 

# Copia el archivo JAR al contenedor
COPY target/Examen3-0.0.1-SNAPSHOT.jar app.jar  

# Expone el puerto en el que la aplicación escucha
EXPOSE 8080  

# Comando para ejecutar la aplicación Java
CMD ["java", "-jar", "app.jar"]



