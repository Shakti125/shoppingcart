FROM maven:3.6.3-openjdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:8
COPY --from=build /home/app/target/shoppingcart.jar /usr/local/lib/shoppingcart.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/shoppingcart.jar"]