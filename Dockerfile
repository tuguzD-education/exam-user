FROM gradle:7.1.1-jdk16-hotspot AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:15-jdk-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
