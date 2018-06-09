FROM openjdk:8-jre-alpine
MAINTAINER alexff91@gmail.com

# Copy Spring Boot app
COPY target/recorder-1.0.0.jar /

EXPOSE 8082

CMD ["/usr/bin/java", "-jar", "recorder-1.0.0.jar"]
