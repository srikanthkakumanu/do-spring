# Custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:21

# Continue with your application deployment
RUN mkdir /opt/apps
COPY /todo-service/app/build/libs/todo-service.jar /opt/apps/todo-service.jar
#EXPOSE 9002
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/todo-service.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar todo-service.jar
