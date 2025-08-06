FROM quay.io/quarkus/ubi-quarkus-native-image:23.1-java17 AS build

WORKDIR /app
COPY . .

RUN ./mvnw package -Pnative -Dquarkus.native.container-build=true

FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /app/target/*-runner saludo-runner
RUN chmod 775 saludo-runner
CMD ["./saludo-runner"]
