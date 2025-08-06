# Etapa de compilación: imagen oficial con GraalVM, Maven y Java 17
FROM quay.io/quarkus/ubi-quarkus-mandrel:23.1-java17 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el proyecto (preferiblemente en partes)
COPY pom.xml .
COPY src ./src
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw

# Compilar en modo nativo
RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

# Etapa final: imagen mínima para ejecutar
FROM quay.io/quarkus/quarkus-micro-image:2.0

WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
