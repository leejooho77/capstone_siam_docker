FROM openjdk:8-jdk-alpine3.7
COPY ./target/capstone-docker-0.1.0.jar /home/app.jar

RUN ["mkdir", "/log"]
RUN ["chmod", "a+rw", "/log"]
CMD ["java","-jar","/home/app.jar"]
