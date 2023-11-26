# deploy.sh
#!/bin/bash
REPOSITORY=/home/ubuntu/app/blog-news-feed
cd $REPOSITORY

APP_NAME=BlogNewsFeedApp
JAR_PATH=$(ls $REPOSITORY/build/libs/*.jar | grep -v 'plain')

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 APP_NAME이 없음"
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 에 실행 권한 추가" 
chmod +x $JAR_PATH

echo "> $JAR_PATH 배포"
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
