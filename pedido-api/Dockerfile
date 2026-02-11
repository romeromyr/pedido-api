FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Render asigna PORT en tiempo de ejecución; la app debe escuchar en ese puerto
ENV PORT=10000
EXPOSE 10000

# Usar shell para que $PORT se lea del entorno de Render
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar target/pedido-api-0.0.1-SNAPSHOT.jar"]