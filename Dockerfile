FROM alpine:3.7

RUN apk --update add openjdk8-jre

ADD /target /usr/src/capstone

WORKDIR /usr/src/capstone

CMD ["java", "-jar", "capstone-0.0.1-SNAPSHOT.jar"]