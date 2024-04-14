FROM eclipse-temurin:21
LABEL authors="skakumanu"
RUN mkdir /opt/apps
COPY /books-service/app/build/libs/books-service.jar /opt/apps/books-service.jar
#EXPOSE 9101
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/books-service.jar
#ENTRYPOINT exec java $JAVA_OPTS -Dspring.active.profiles=dev -jar /opt/apps/books-service.jar

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/apps/books-service.jar



