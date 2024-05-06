Enterprise and production ready Microservice with Spring, Spring Cloud and Docker

![Microservices](https://github.com/mayankm3/bank-ms/assets/152583493/8d8dee90-ce53-42e8-85b8-68cf7e4c2223)

How to run the application using Docker
Run `mvn compile jib:dockerBuild` to generate a docker image using Google Jib.
Run `docker-compose up -d` in bank-ms/docker-compose/prod/ to start the applications.

## Maven Commands used

|     Maven Command       |     Description          |
| ------------- | ------------- |
| "mvn clean install -Dmaven.test.skip=true" | To generate a jar inside target folder |


## Apache Benchmark command used

|     Apache Benchmark command      |     Description          |
| ------------- | ------------- |
| "ab -n 1000 -c 2 -v 3 http://localhost:8072/bank/cards/api/contact-info" | To perform load testing on API by sending 1000 requests |

