FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/shoppingcart.jar
COPY ${JAR_FILE} shoppingcart.jar
ENTRYPOINT ["java","-jar","/shoppingcart.jar"]