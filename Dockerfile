FROM openjdk:17
VOLUME /tmp
ADD ./target/number-inventory-0.0.1-SNAPSHOT.jar number-inventory.jar
ENTRYPOINT ["java","-jar","/number-inventory.jar"]