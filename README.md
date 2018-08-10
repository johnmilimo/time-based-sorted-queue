# Messaging-with-JMS

## Run ActiveMQ
```
 $ docker-compose up

```
The Web Interface for ActiveMQ is at http://localhost:8161
username: admin
password: admin

## Run the application
```
$ bash run.sh
```

When the application starts, it schedules 50,000 messages for processing after 20minutes.
It then schedules another batch of 5 messages for processing after 1 second.
For more details see https://github.com/johnmilimo/Messaging-with-JMS/blob/master/src/main/java/com/example/jms/JmsApplication.java
