FROM ghcr.io/graalvm/native-image:ol8-java17 AS build

WORKDIR /app
COPY . .

# ✅ Asegura que ./mvnw se pueda ejecutar
RUN chmod +x mvnw

# Opcional: instala Maven si no viene preinstalado
RUN microdnf install -y maven

RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
