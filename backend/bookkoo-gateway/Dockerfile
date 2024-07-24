# FROM openjdk:17-ea-jdk-slim
# VOLUME /tmp
# COPY target/book-service-1.0.jar BookService.jar
# ENTRYPOINT ["java", "-jar", "BookService.jar"]
# RUN apt-get update && apt-get install -y curl

# Debian 기반 openjdk 이미지 선택
FROM openjdk:17-jdk-buster AS builder

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y findutils && apt-get install dos2unix

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN dos2unix ./gradlew
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17-jdk-buster
COPY --from=builder build/libs/*.jar /home/server.jar
ENTRYPOINT ["java", "-jar", "/home/server.jar", "--spring.profiles.active=dev"]