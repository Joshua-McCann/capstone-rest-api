FROM alpine:3.7
ADD /target /usr/src/capstone
WORKDIR /usr/src/capstone
EXPOSE 8080
ENTRYPOINT ["java -jar capstone-0.0.1-SNAPSHOT.jar"]