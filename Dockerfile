
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/auth-service-0.0.1-SNAPSHOT.jar rms-auth-service.jar
EXPOSE 10006
# ENTRYPOINT exec java $JAVA_OPTS -jar rms-auth-service.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar rms-auth-service.jar
