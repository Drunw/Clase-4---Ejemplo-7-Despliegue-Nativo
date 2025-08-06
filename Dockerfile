FROM ghcr.io/graalvm/native-image:ol8-java17 AS build

WORKDIR /app

# Copia solo lo necesario
COPY pom.xml .
COPY src ./src

# Instala Maven
RUN microdnf install -y maven

# Compila en nativo
RUN mvn package -Pnative -Dquarkus.native.container-build=true

FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
