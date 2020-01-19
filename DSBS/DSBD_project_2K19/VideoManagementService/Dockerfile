FROM maven:3-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn package -P production

FROM java:8
WORKDIR /app
COPY --from=builder /app/target/video_management_service-0.0.1-SNAPSHOT.jar ./video-manager-service.jar
CMD java -jar video-manager-service.jar
