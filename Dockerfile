FROM eclipse-temurin:17
EXPOSE 8084
COPY target/financial-grant-mgmt-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]