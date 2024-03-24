# Custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:21


# Continue with your application deployment
RUN mkdir /opt/apps
COPY build/libs/todo.jar /opt/apps/todo.jar
EXPOSE 9002
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/todo.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar todo.jar
