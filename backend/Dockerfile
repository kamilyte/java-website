FROM adoptopenjdk:11-jre-hotspot-focal@sha256:eac1c6cff5fded2dd35fc94bb23e7862a08277bd71f9b352a99df5bc740459c3
ARG JAR_FILE=target/webapp-1.0.0.jar
COPY ${JAR_FILE} webapp-1.0.0.jar
ENTRYPOINT ["java","-jar","/webapp-1.0.0.jar"]
EXPOSE 8081