FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
#ADD conf      /conf
#RUN bash -c 'touch /app.jar'
EXPOSE 2020
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m","-Duser.timezone=GMT+8","-Dspring.profiles.active=test","-jar","/app.jar"]