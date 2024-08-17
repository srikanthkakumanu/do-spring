FROM eclipse-temurin:21
LABEL authors="skakumanu"

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

RUN mkdir /opt/apps
COPY api/app/build/libs/api.jar /opt/apps/api.jar
#EXPOSE 9102
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/api.jar
#ENTRYPOINT exec java $JAVA_OPTS -Dspring.active.profiles=dev -jar /opt/apps/api.jar

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/apps/api.jar
