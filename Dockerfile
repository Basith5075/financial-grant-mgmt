FROM eclipse-temurin:17
EXPOSE 8084
COPY target/financial-grant-mgmt-v0.1.jar app.jar
CMD java $JAVA_OPTS -jar app.jar