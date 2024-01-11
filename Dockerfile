FROM openjdk:21

WORKDIR /usr/src/app
COPY ./api/build/libs/*.jar app.jar
RUN chmod 4755 app.jar

### 도커 컨테이너에 환경변수로 AWS 자격 증명 값을 넣어주는 방식
## 빌드가 실행되는 환경(로컬이거나, github actions이거나 등)에서 해당 이름에 해당되는 환경변수를 가져온다.
#ARG AWS_ACCESS_KEY
#ARG AWS_SECRET_KEY
ARG DOCKER_USER=docker_user
ARG DOCKER_USER_HOME=/home/$DOCKER_USER
ARG AWS_BUCKET
ARG AWS_CREDENTIALS=/root/.aws/credentials

## 빌드가 실행되는 환경에서 가져온 값을 도커 컨테이너의 환경변수로 설정한다.
#ENV AWS_ACCESS_KEY=$AWS_ACCESS_KEY
#ENV AWS_SECRET_KEY=$AWS_SECRET_KEY
ENV AWS_BUCKET=$AWS_BUCKET
ENV AWS_SHARED_CREDENTIALS_FILE=$AWS_CREDENTIALS

## 도커 컨테이너에 자격 증명파일을 복사하는 방식
# GITHUB ACTIONS에서 자격 증명 파일을 만들고 위치 수정해줘야함
RUN useradd $DOCKER_USER

COPY ./.aws/credentials ~/.aws/credentials
#RUN chmod 700 $AWS_CREDENTIALS

#USER $DOCKER_USER

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "1>output.log", "2>./error.log", "&"]
