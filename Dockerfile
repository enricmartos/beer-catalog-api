FROM maven:3.5-jdk-11 AS build
COPY . /usr/src/app
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip

FROM gcr.io/distroless/java11-debian11
COPY --from=build /usr/src/app/beer-catalog-api-boot/target/beer-catalog-api-boot-1.0.0-SNAPSHOT.jar /usr/app/beer-catalog-api-boot-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/app/beer-catalog-api-boot-1.0.0-SNAPSHOT.jar"]