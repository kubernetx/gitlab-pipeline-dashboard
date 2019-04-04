FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/*.jar gitlab-pipeline-dashboard.jar
CMD java  -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -noverify ${JAVA_OPTS} -jar gitlab-pipeline-dashboard.jar