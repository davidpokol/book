FROM openjdk:17-jdk-alpine3.14

COPY "./target/book-app.jar" "/application/book-app.jar"

CMD ["java", "-jar", "/application/book-app.jar"]