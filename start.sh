mvn clean package
docker build -t demo2 .
docker stop record2
docker rm record2
docker run -p 8082:8082 --privileged=true --network host --restart=always --name=record2 -d -v /browser-recordings:/browser-recordings/ -v /etc/letsencrypt/live/vi-hr.com/:/etc/letsencrypt/live/vi-hr.com/ demo2
