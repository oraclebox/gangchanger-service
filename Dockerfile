FROM anapsix/alpine-java:8

RUN mkdir /application
RUN mkdir -p /application/log
COPY build/libs/gangchanger-service-0.0.1-SNAPSHOT.jar /application/app.jar
COPY application.yml /application/application.yml
COPY static /application/static
WORKDIR /application

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
EXPOSE 28888
