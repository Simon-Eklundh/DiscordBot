FROM openjdk:11
RUN mkdir /usr/src/myapp
COPY DiscordBot-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/src/myapp/DiscordBot.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "DiscordBot.jar"]