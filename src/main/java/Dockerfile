FROM openjdk:11
COPY ./serverip.txt /usr/app/
COPY ./DiscordBot-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/bot.jar
WORKDIR /usr/app
CMD ["java", "-jar","bot.jar"]