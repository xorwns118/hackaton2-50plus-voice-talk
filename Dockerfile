FROM corretto-17
ARG JAR_FILE=build/libs/hackaton2-50plus-voice-talk-0.0.1-SNAPSHOT-plain.jar
ADD ${JAR_FILE} docker-hackaton250plusvoicetalk.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/dockerhackaton250plusvoicetalk.jar"]