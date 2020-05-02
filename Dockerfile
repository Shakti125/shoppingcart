FROM ubuntu:18.04
WORKDIR /tmp/docker
RUN apt-get -y update && apt-get -y upgrade && apt-get -y install git
RUN apt-get -y install openjdk-8-jdk
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
RUN apt-get -y update && apt-get -y install maven
RUN git clone https://github.com/Shakti125/shoppingcart.git
RUN cd shoppingcart && mvn clean package -Dmaven.test.skip=true
ADD target/shoppingcart.jar shoppingcart.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","shoppingcart.jar"]