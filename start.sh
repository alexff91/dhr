mvn clean package
docker build -t demo .
docker stop demo
docker run -p 443:5443 --restart=always --name=record -d -v /browser-recordings:/browser-recordings/ demo
