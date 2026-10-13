FINAL PROJECT : RESTFUL API + AMAZON WEB SERVICES.

This is my final project that evaluates my full on knowledge on what i 
learned over the past month, covering mostly on infrastructure, use of 
the command line, git and documentation regarding software in general.


Requirements : 

-Java 17 or higher 
-Docker (if you want to run with docker).




//INTRODUCTION - ARCHITECTURE : 


This API consists on storing info via a payload sent by the user filling
a form with data. After the user clicks the "submit" button, a payload 
gets sent to the API Gateway, which is then sent to the API Backend. The
Backend converts the payload data into a computable object. 

After the proper logic and calculations are applied, the object than gets converted
back to a key-value pair format and it is saved as a record in a DynamoDB data table.
DynamoDB uses a non-relational format for storing data, so it is perfect for this task.
 

//TESTING AND SENDING REQUESTS


STEP 1 : Setting up the environment.


1. Clone the repository folder from the branch "Final Project"

git clone --depth 1 --single-branch --branch feat/Final-Project --no-checkout https://github.com/CAAF299/OOP-experimental/


2. Go inside the folder

cd OOP-experimental/


3. Only acquire the "API" folder

git sparse-checkout set API 


4. Download the folder

git checkout 


5. Go inside the folder

cd API/ 


6. Build the project file : 

./mvnw package

Congratulations ! The API is set for running and testing !


STEP 2 : Running the API 

You can run the API both locally and 
with Docker.


A - Running locally :


./mvnw spring-boot:run


B - Running with docker : 


1. Build the docker image 


docker build -t spring-api


2. Run the docker image.(You need to provide your aws credentials in order to run it here)

docker run -p 8080:8080 -e AWS_ACCESS_KEY_ID=[KEY] -e AWS_SECRET_ACCESS_KEY=[SECRET_KEY] -e AWS_REGION=us-east-2 spring-api


STEP 3 : Testing the API 


A - Simple GET request.


Local: 


curl http://localhost:8080/default/api/users/info


In an instance (if you plan to deploy this in the cloud) : 


curl http://[ec2 public ip]:8080/default/api/users/info



B - POSTing a record : 


curl -x POST http://localhost:8080/api/users/\ 
-H "Content-Type: application/json"\
-d  '{


"firstName": "Johnny", 
"lastName": "Bravo",
"age": 28,
"gender": "male", 
"birthday": "1999-02-21",
"email": "woahThere@hotmail.com",
"phoneNumber": 45127845712,
"personalId": 547854789,
"career": "Technician" 

}' 


C - Editing a record 

#Note : The id is provided as output after you send a payload
#to the API. Replace the {id} with the actual id number, not
#the personal id !


curl -x PUT http://localhost:8080/api/users/{id}\
-H "Content-Type: application/json"\
-d '{

"firstName": "Johnny", 
"lastName": "Bravo",
"age": 28,
"gender": "male", 
"birthday": "1999-02-21",
"email": "woahThere@hotmail.com",
"phoneNumber": 45127845712,
"personalId": 652874597,
"career": "Engineer" 


}'


D - Deleting a record

curl -x DELETE http://localhost:8080/api/users/{id}


///TROUBLESHOOTING : 

If you get errors involving the status code 404, it means one of 
these things you did wrong when trying to do a request with curl: 


1. You didn't append the "/default" route when you write out the url.


 http://localhost:8080/api/users          X

 http://localhost:8080/default/api/users  <- Use this one


2. You're in a remote environment (Like an ec2 instance). In that case, you need
to provide the public IP adress of said instance, as follows: 


 http://171.214.53.1:8080/default/api/users



3. You executed the "docker run" command without providing your AWS keys.


 
///DEPLOYMENT///


Requirements :

-An AWS Free Tier account, in order to gain access to the AWS Console.



1. Go to your AWS Console, and create an all purpose, basic S3 bucket.

2. Upload the previously made .jar file we packaged, which is located in the
   folder "target/" under the name of "demo.0.0.1_SNAPSHOT.jar". Then, select
   the file, and click on "Copy object URL" 

3. After that finishes uploading, click on the search bar and look for "Lamb-
   da". Click on that and click on the button that says "Create an event func-
   tion" and give it a name, and in the "Runtime" section, select "Java 17"

4. In the code section, click "update" and then the option "update from a file in
   Amazon S3", Select "Copy Mode (Default)" , and paste the link you copied in the 
   text field, and hit "OK"



