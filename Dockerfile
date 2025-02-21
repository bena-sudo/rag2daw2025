# 1️⃣ Usamos Maven con JDK 17 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 2️⃣ Configuramos el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3️⃣ Copiamos el código fuente del proyecto
COPY . .

# 4️⃣ Ejecutamos Maven para compilar y empaquetar el proyecto en un archivo .war
RUN mvn clean install -DskipTests

# 5️⃣ Usamos una imagen oficial de Tomcat 10 con soporte para Servlet 5.0
FROM tomcat:10.1-jdk17

# 6️⃣ Configuramos el directorio de trabajo dentro del contenedor
WORKDIR /usr/local/tomcat/webapps/

# 7️⃣ Copiamos el archivo .war compilado y lo renombramos como ROOT.war (para que Tomcat lo despliegue correctamente)
COPY --from=build /app/target/*.war ROOT.war

# 8️⃣ Configuramos Tomcat para que escuche en el puerto 8090 en lugar de 8080
RUN sed -i 's/port="8080"/port="8090"/g' /usr/local/tomcat/conf/server.xml

# 9️⃣ Exponemos el puerto 8090 donde corre Tomcat
EXPOSE 8090

# 🔟 Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]

