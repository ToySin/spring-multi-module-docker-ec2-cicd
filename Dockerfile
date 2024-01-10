FROM openjdk:21

WORKDIR /usr/src/app
COPY ./api/build/libs/*.jar app.jar

# 빌드가 실행되는 환경(로컬이거나, github actions이거나 등)에서 해당 이름에 해당되는 환경변수를 가져온다.
ARG AWS_ACCESS_KEY
ARG AWS_SECRET_KEY
ARG AWS_BUCKET

# 빌드가 실행되는 환경에서 가져온 값을 도커 컨테이너의 환경변수로 설정한다.
ENV AWS_ACCESS_KEY=$AWS_ACCESS_KEY
ENV AWS_SECRET_KEY=$AWS_SECRET_KEY
ENV AWS_BUCKET=$AWS_BUCKET

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
