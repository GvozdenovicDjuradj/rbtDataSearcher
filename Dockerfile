FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN chmod +x ./gradlew
RUN gradle bootJar

FROM openjdk:17.0.1-jdk-slim
EXPOSE 8081
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/searcher-*SNAPSHOT.jar /app/dataSearcher.jar
ENTRYPOINT ["java","-jar","/app/dataSearcher.jar"]