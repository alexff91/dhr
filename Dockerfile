FROM openjdk:8-jre-alpine
MAINTAINER alexff91@gmail.com

# Copy Spring Boot app
COPY target/recording-server-endpoint-1.0.0.jar /

EXPOSE 5443

CMD ["/usr/bin/java", "-jar", "recording-server-endpoint-1.0.0.jar"]
