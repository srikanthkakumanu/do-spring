# Custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:21

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# Continue with your application deployment
RUN mkdir /opt/apps
COPY /videos-service/app/build/libs/videos-service.jar /opt/apps/videos-service.jar
# EXPOSE 9003
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/videos-service.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar videos-service.jar
