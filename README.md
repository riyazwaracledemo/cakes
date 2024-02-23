# Cakes Api

## Introduction
Cakes API presents users to do basic CRUD operations for a fictitious Cakes application. Only back-end code is in scope as of now.

The end points exposed are as follows:
1) /cakes
2) /cakes/<id>
3) /addCake
4) /addCakes
5) /updateCake/<id>
6) /delete/<id>

A basic authentication and authorization is added using latest spring framework. To allow testing a set of users are created when the service starts.
These users are managed in H2 in-memory database hence they are lost everytime the service is stopped.
The list of userid/password and their roles are:
1) user1 / user1 -> USER
2) user2 / user2 -> USER
3) user3 / user3 -> USER
4) user4 / user4 -> USER
5) admin / admin -> ADMIN

Note: their password are stored in encrypted form within database, but as this is an excercise for demo purpose, their passwords are mentioned here.

## Instructions to run application
There are several ways to test this application. Two of the most common ones are listed here:
### From the latest image in hub.docker.com
   There is a CI/CD pipeline set up using github actions which pushed a docker image tagged as latest to the repo:
   https://hub.docker.com/repository/docker/riyazwaracledemo/cakes-api/general
   
   As this is a public repo, this can be downloaded on local docker desktop by executing this command on a local command prompt:
     'docker pull riyazwaracledemo/cakes-api:latest'
   
   Once downloaded, application can be started by running it from the docker desktop.
   
   Expose the local port in the dialog box to 8282 when the conainer spins up.
   
   You can then see output of /cakes end point on a browser as: http://localhost:8282/cakes
   
### From the source code
   Clone this repo on your local by running this command on your local command prompt:
     'git clone https://github.com/riyazwaracledemo/cakes.git'
   
   Build the project by running this command from projects root directory:
     'mvn clean install'
   
   Run the application by issuing this command from projects root directory:
     'mvn spring-boot:run'
   
   You can then see output of /cakes end point on a browser as: http://localhost:8282/cakes

## Instructions to test application
  Postman or similar app can be used to test this application.

  As there is authentication and authorization in place for every end point exposed, you will need to provide user id / password as basic authentication in every request.

  The userid's and passwords along with their roles are mentioned above in Itroduction section above.

  Please note that users with role 'USER' are authorized to call the following end points:
  1) /cakes
  2) /cakes/<id>

  and the users with Admin role can call:
  1) /addCake
  2) /addCakes
  3) /updateCake/<id>
  4) /delete/<id>
  
  
