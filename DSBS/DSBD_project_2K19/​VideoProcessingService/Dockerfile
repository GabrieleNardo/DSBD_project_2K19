FROM maven:3-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn package -P production

FROM adoptopenjdk/openjdk8:alpine
WORKDIR /app
COPY --from=builder /app/target/video_processing_service-0.0.1-SNAPSHOT.jar ./video-processing-service.jar
RUN apk update
RUN apk add ffmpeg
CMD java -jar video-processing-service.jar
