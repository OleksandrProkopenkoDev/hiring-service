FROM openjdk:17-jdk-alpine as build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

FROM openjdk:17-jdk-alpine
ARG DEPENDENCY=target/dependency
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN update-ms-fonts
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","ua.com.hiringservice.HiringServiceApplication"]
