# build stage
FROM maven:3.8.5-openjdk-17 as maven
ENV SPRING_PROFILES_ACTIVE=production
# copy the project files
COPY ./pom.xml ./pom.xml
# build all dependencies
RUN mvn dependency:go-offline -B
# copy your other files
COPY ./src ./src
# build for release
RUN mvn package

# package stage
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/target/conference-0.0.1-SNAPSHOT.jar
COPY --from=maven $JAR_FILE /app/conference-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar /app/account-0.0.1-SNAPSHOT.jar
