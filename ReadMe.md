## UPDATE PROCESS:

#### Build project: 
  * gradle clean
  * gradle bootJar
  
#### Copy new files in a separate folder: 
  * local.yml
  * Dockerfile
  * build/libs/monitoring-backend-0.0.1-SNAPSHOT.jar
  
#### Stop current application: 
  * docker-compose -f local.yml down
  
#### Start a new version: 
  * export UID && export GID && docker-compose -f local.yml up --build -d