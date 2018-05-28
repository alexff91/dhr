To run mongoDb:
docker run -d --name vacancy-mongo -p 27017:27017 --restart=always -v /data/mongodb:/data/db -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo


Create user:
use dhr
db.createUser( { user: "mongoadmin",
                 pwd: "secret",
                 roles: []})
use admin
db.createUser( { user: "mongoadmin",
          pwd: "secret",
          roles: [ "userAdminAnyDatabase",
                   "dbAdminAnyDatabase",
                   "readWriteAnyDatabase"

] } )                 
                 
                 
Build docker:
docker build -t demo .
docker run -p 8080:8080 --restart=always -d -v /browser-recordings:/browser-recordings/ demo
                 
                 
                 
jenkins on 8081 port
