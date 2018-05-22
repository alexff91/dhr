To run openvidu server :
docker run -d -p 4443:4443 -e openvidu.secret=YOUR_SECRET -e openvidu.recording=true -e openvidu.recording.path=/data/video/records -e MY_UID=$(id -u $USER) -v /var/run/docker.sock:/var/run/docker.sock -v /data/video/records:/data/video/records -e openvidu.publicurl=https://206.189.7.164:4443/ -e kms.uris=[\"ws://206.189.7.164:8888/kurento\"] openvidu/openvidu-server


