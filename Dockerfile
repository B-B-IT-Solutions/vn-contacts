FROM openjdk:19-jdk-oracle

WORKDIR /usr/app

COPY target/contacts.jar /usr/app/contacts.jar

ENTRYPOINT ["java","-jar","/usr/app/contacts.jar"]