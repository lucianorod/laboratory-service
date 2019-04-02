FROM java:8
MAINTAINER lucianorod
ADD build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
