FROM maven:3-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn package -P production

FROM java:8
WORKDIR /app
COPY --from=builder /app/target/api_gateway_cloud-0.0.1-SNAPSHOT.jar ./api-gateway.jar
CMD java -jar api-gateway.jar
