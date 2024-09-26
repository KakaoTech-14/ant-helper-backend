FROM openjdk:17-jdk-alpine

WORKDIR /app

# application-dev.properties, application-common.properties 복사 
COPY src/main/resources/application-dev.properties /app/src/main/resources/application-dev.properties
COPY src/main/resources/application-common.properties /app/src/main/resources/application-common.properties

# 컨테이너 내에 redis 설치
RUN apk update && apk add redis

COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
