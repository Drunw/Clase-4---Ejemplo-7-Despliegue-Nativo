FROM ghcr.io/graalvm/native-image:ol8-java17 AS build

WORKDIR /app
COPY . .

# ✅ Asegura que ./mvnw se pueda ejecutar
RUN chmod +x mvnw

# Opcional: instala Maven si no viene preinstalado
RUN microdnf install -y maven

RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

FROM registry.access.redhat.com/ubi9/ubi-minimal:9.5
WORKDIR /app/
RUN chown 1001 /app \
    && chmod "g+rwX" /app \
    && chown 1001:root /app
COPY --chown=1001:root --chmod=0755 target/*-runner /app/application

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0"]
