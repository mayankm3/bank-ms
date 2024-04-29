Enterprise and production ready Microservice with Spring, Spring Cloud and Docker


## Maven Commands used

|     Maven Command       |     Description          |
| ------------- | ------------- |
| "mvn clean install -Dmaven.test.skip=true" | To generate a jar inside target folder |
| "mvn compile jib:dockerBuild" | To generate a docker image using Google Jib. No need of Dockerfile |


## Apache Benchmark command used

|     Apache Benchmark command      |     Description          |
| ------------- | ------------- |
| "ab -n 1000 -c 2 -v 3 http://localhost:8072/bank/cards/api/contact-info" | To perform load testing on API by sending 1000 requests |

