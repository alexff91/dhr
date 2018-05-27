mvn clean package
docker build -t demo .
docker stop record
docker rm record
docker run -p 443:443 --privileged=true --network host --restart=always --name=record -d -v /browser-recordings:/browser-recordings/ demo
