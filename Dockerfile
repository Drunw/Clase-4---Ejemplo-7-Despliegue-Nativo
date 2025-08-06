# Etapa de construcción del binario nativo
FROM quay.io/quarkus/quarkus-native-image:latest AS build

WORKDIR /app
COPY . .

RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

# Etapa de ejecución: contenedor mínimo para app nativa
FROM quay.io/quarkus/quarkus-micro-image:2.0

WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner

CMD ["./saludo-runner"]
