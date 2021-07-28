FROM openjdk:8-jdk-alpine as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:8-jdk-alpine
USER root
COPY src/main/resources/certs /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY docker-base/UnlimitedJCEPolicyJDK8/*.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
RUN ls /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/

# ENV encrypt.keyStore.location file:/usr/lib/jvm/java-1.8-openjdk/jre/lib/security/apiKeyStore.jks
# ENV encrypt.keyStore.password 123456
# ENV encrypt.keyStore.alias apiKeyStore
# ENV encrypt.keyStore.type jks

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]