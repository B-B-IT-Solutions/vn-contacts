FROM openjdk:19-jdk-oracle

WORKDIR /usr/app

COPY target/vision-notes.jar /usr/app/vision-notes.jar

ENTRYPOINT ["java","-jar","/usr/app/vision-notes.jar"]