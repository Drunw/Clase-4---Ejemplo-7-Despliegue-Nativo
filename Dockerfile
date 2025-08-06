# Etapa de compilación nativa
FROM ghcr.io/graalvm/native-image:ol8-java17 AS build

WORKDIR /app
COPY . .

# Instala Maven (si tu imagen base no lo tiene ya)
RUN microdnf install -y maven

# Compila en modo nativo
RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

# Imagen final para correr la app nativa
FROM quay.io/quarkus/quarkus-micro-image:2.0

WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
