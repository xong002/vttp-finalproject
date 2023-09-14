FROM node:17 AS ngBuilder

WORKDIR /app

COPY vttp-project-ng/src src
COPY vttp-project-ng/angular.json .
COPY vttp-project-ng/package.json .
COPY vttp-project-ng/package-lock.json .
COPY vttp-project-ng/tsconfig.app.json .
COPY vttp-project-ng/tsconfig.json .
COPY vttp-project-ng/tsconfig.spec.json .

RUN npm i -g @angular/cli
RUN npm i
RUN npm install primeng
RUN npm install primeicons
RUN npm install --save font-awesome angular-font-awesome
RUN npm install primeflex
RUN npm install chart.js
RUN ng add @angular/pwa@16.2.1 --skip-confirmation
RUN ng build

FROM maven:3.8.3-openjdk-17 AS mvnBuilder

WORKDIR /app

COPY vttp-project-springboot/src src
COPY vttp-project-springboot/mvnw mvnw
COPY vttp-project-springboot/pom.xml .
COPY --from=ngBuilder /app/dist/vttp-project-ng /app/src/main/resources/static/

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=mvnBuilder /app/target/vttp-project-0.0.1-SNAPSHOT.jar app.jar

ARG SQL_URL
ARG SQL_USER
ARG SQL_PASSWORD
ARG S3_ACCESSKEY
ARG S3_SECRETKEY
ARG S3_BUCKETURL
ARG JWT_SECRETKEY

ENV PORT=8080
ENV SQL_URL=${SQL_URL}
ENV SQL_USER=${SQL_USER}
ENV SQL_PASSWORD=${SQL_PASSWORD}
ENV S3_ACCESSKEY=${S3_ACCESSKEY}
ENV S3_SECRETKEY=${S3_SECRETKEY}
ENV S3_BUCKETURL=${S3_BUCKETURL}
ENV JWT_SECRETKEY=${JWT_SECRETKEY}

EXPOSE ${PORT}
ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar