FROM mysql:8
ENV MYSQL_DATABASE iot_test_db
VOLUME ["/docker-spring-boot/db"  "/docker-entrypoint-initdb.d"]
COPY ./db/ /docker-entrypoint-initdb.d/
EXPOSE 3036

FROM openjdk:8
ADD target/docker-spring-boot.jar  docker-spring-boot.jar 
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]
