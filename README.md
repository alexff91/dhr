To run mongoDb:
docker run -d --name interview-mongo -p 27017:27017 --restart=always -v /data/mongodb:/data/db -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo


Create user:
use dhr
db.createUser( { user: "accountAdmin01",
                 pwd: "changeMe"})
                 
                 
                 
Build docker:
docker build -t demo .
docker run -p 443:5443 --restart=always -d -v /browser-recordings:/browser-recordings/ demo
                 
                 
                 
