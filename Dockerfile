FROM alpine:latest AS build
ENV JAVA_HOME /opt/jdk/jdk-21.0.1
ENV PATH $JAVA_HOME/bin:$PATH

ADD https://download.bell-sw.com/java/21.0.1+12/bellsoft-jdk21.0.1+12-linux-x64-musl.tar.gz /opt/jdk/
RUN tar -xzvf /opt/jdk/bellsoft-jdk21.0.1+12-linux-x64-musl.tar.gz -C /opt/jdk/

RUN ["jlink", "--compress=2", \
     "--module-path", "/opt/jdk/jdk-21.0.1/jmods/", \
     "--add-modules", "java.base,java.logging,java.naming,java.desktop,jdk.unsupported", \
     "--no-header-files", "--no-man-pages", \
     "--output", "/springboot-runtime"]

FROM alpine:latest
COPY --from=build  /springboot-runtime /opt/jdk
ENV PATH=$PATH:/opt/jdk/bin
EXPOSE 8282
COPY ../target/cakes-api.jar /opt/app/
ENTRYPOINT ["java", "-showversion", "-jar", "/opt/app/cakes-api.jar"]