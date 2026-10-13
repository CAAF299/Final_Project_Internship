This is an experimental, basic API that is the combination of 
the various fundamentals that i learned with Java and HTTP with Spring Boot, which 
recieves json payloads in a temporary space in memory instead of 
being connected with a database.


\\PREREQUISITES:\\

-Java 17 (or higher)
-Docker


\\Initial setup - Prepare the environment :

1. Clone the repository and go inside the OOP-experimental folder:

git clone --depth 1 --single-branch --branch feat/API-attempt-2 --no-checkout https://github.com/CAAF299/OOP-experimental

cd OOP-experimental/


2. Download the API folder and unpack it:

git sparse-checkout set API

git checkout


3. Go inside the API folder an build the .JAR

cd API/

./mvnw clean package



\\Running the API : 

Method 1 - Docker


1. Build the container

docker build -t spring-api


2. Run the container.

docker run -p 8080:8080 spring-api 


Method 2 - local execution


./mvnw spring-boot:run 


\\SAMPLE COMMANDS FOR COMMUNICATING WITH THE API VIA CURL : \\ 


1. Getting the API respone body.

curl http://localhost:8080/api/users/info

OUTPUT : 

This is an API running on port 8080.


2. Creating a resource.

curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"age": 19, "name": "Alexander"}'

OUTPUT:

{"adult":true,"age":19,"id":122,"name":"Alexander"}

NOTE : The id varies because it's randomly generated.


3. Fetching a resource


curl http://localhost:8080/api/users/122
("122" is the Id that the API assigned to our entry.)

OUTPUT:

{"adult":true,"age":19,"id":122,"name":"Alexander"}

(it got the object that we needed)


4. Editing a resource :


curl -X PUT http://localhost:8080/api/users/122 -H "Content-Type: application/json" -d '{"age": 29, "name": "Alexander" }'


OUTPUT: 

{"adult":true,"age":29,"id":122,"name":"Alexander"}


5. Look up currently created entries :

curl http://localhost:8080/api/users


OUTPUT :

[{"adult":true,"age":29,"id":122,"name":"Alexander"}, {"adult":true,"age":37,"id":131,"name":"Maurice"}]

If you have multiple entries, it'll list them in this array.


6. Delete an entry : 

curl -X DELETE http://localhost:8080/api/users/131



7. Invalid POST (validation Error):


curl -i -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name": "", "age": -4}'

OUTPUT : 

HTTP/1.1 400 Bad Request


8. Fetching a non-existent resource:


curl -i http://localhost:8080/api/users/1535

OUTPUT : 

HTTP/1.1 404 Not Found



