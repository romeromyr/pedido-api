# Dockerfile en la raíz del repo para que Render lo encuentre sin Root Directory
FROM eclipse-temurin:21-jdk
WORKDIR /app

# El código está en pedido-api/ dentro del repo
COPY pedido-api /app

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

ENV PORT=10000
EXPOSE 10000

CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar target/pedido-api-0.0.1-SNAPSHOT.jar"]
