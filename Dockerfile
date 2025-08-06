FROM ghcr.io/graalvm/native-image:ol8-java17 AS build

WORKDIR /app
COPY . .

# Da permisos al wrapper
RUN chmod +x mvnw

# Instala Maven si no está
RUN microdnf install -y maven

# ✅ Compila usando GraalVM instalado (no intenta usar Docker dentro de Docker)
RUN ./mvnw package -Pnative

# Imagen final: pequeña, solo con el binario
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
