FROM gcr.io/googl-as400-ed46/openjdk-netty-tcnative-alpine:1.0

ARG TAG_NAME

ENV APP_USER java
ENV APP_VERSION $TAG_NAME

RUN adduser -S $APP_USER

RUN mkdir -p /usr/local/apps/server-socket

# Add the service itself
ADD target/spring-integration-socket-server-0.0.1-SNAPSHOT.jar /usr/local/apps/server-socket/app.jar

# Copy entrypoint.sh
COPY entrypoint.sh /usr/local/apps/entrypoint.sh

RUN sh -c 'touch /usr/local/apps/server-socket/app.jar'
RUN ["chmod", "+x", "/usr/local/apps/entrypoint.sh"]

RUN mkdir -p /var/log && chown $APP_USER /var/log

ENTRYPOINT ["/bin/bash", "-c", "/usr/local/apps/entrypoint.sh"]

EXPOSE 8282 8000
