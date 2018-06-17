mvn clean package
docker build -t demo .
docker stop record
docker rm record
docker run -p 8082:8082 --privileged=true --network host --restart=always --name=record -d -v /interviews:/interviews/ -v /etc/letsencrypt/live/app.vi-hr.com/:/etc/letsencrypt/live/app.vi-hr.com/ demo
