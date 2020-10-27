#!/bin/bash
java -Djava.security.egd="-Djava.security.egd=file:/dev/./urandom" -DtagName=$APP_VERSION -jar /usr/local/apps/server-socket/app.jar
