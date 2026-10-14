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
DynamoDB uses a non-relational behavior for storing data, making it flexible for this
task.




//TESTING AND SENDING REQUESTS


STEP 1 : Setting up the environment.


1. Clone the repository folder from the branch "feat/API_Assets"

git clone --depth 1 --single-branch --branch feat/API_Assets --no-checkout https://github.com/CAAF299/Final_Project_Internship


2. Go inside the folder

cd Final_Project_Internship/


3. Only acquire the "API" folder

git sparse-checkout set Backend 


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

-an IAM user with AdministratorAccess privilage and cloud access, for
the sake on not using root for this.

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


5. Go to the "Configuration" section, click on "Triggers", and select "Add trigger"


6. Select from the list "API Gateway", select "create a new API", and   select "HTTP"

7. Now that the trigger is created, click on the Underlined Name 
   next to the "API Gateway:" of your trigger.

8. On the "Routes" section, click on "Create", and make these 2:


  -instead of "/" , type "$default"
  -instead of "/", type "/{proxy+}" with the method said to "ANY"


9. In the "Integrations" section, click on "Edit" and do the following:

   -Enter your region and the lambda function from the dropdown lists.
   -Click on "Advanced Settings" and select the Payload format version to "1.0"

   -click on "Grant API Gateway permission to invoke function" and click on "Save"



10. On "CORS", click on "Configure" and enter this information to its respective fields:


   -Access-Control-Allow-Origin: *
   -Access-Control-Allow-Headers: *
   -Access-Control-Allow-Methods: GET, POST, OPTIONS
   -Access-Control-Expose-Headers: *

and save it.


11. Back in the "Configuration" section, click on "Permissions", scroll down to 
   "Resource-based policy statements" and replace the code in there with this: 


    {
  "Version": "2012-10-17",
  "Id": "default",
  "Statement": [
    {
      "Sid": "apigateway-any-proxy",
      "Effect": "Allow",
      "Principal": {
        "Service": "apigateway.amazonaws.com"
      },
      "Action": "lambda:InvokeFunction",
      "Resource": "arn:aws:lambda:[region]:[IAM ID]:function:[Lambda_Function_Name],
      "Condition": {
        "ArnLike": {
          "AWS:SourceArn": "arn:aws:execute-api:[region]:[IAM ID]:[api-trigger-id]/*/*/*"
        }
      }
    }
  ]
}


Fill these [] fields with the actual info. The api trigger id can be found in the main API Gateway
page.





12. Finally, head over to EC2 , and select "Launch an Instance" and click "Launch without Walkthrough".
    Then, do the following:


    -Amazon image : set it to Ubuntu

    -Keypair : Set it to "Proceed without Keypair" 

    -Network Settings ; Firewall (Security Groups) : Check
    both HTTP and HTTPS traffic checkboxes.


	click on "Launch Instance"


13. In the Same EC2 menu, click on "Security Groups" and edit the security group that your instance
    created by default (often labeled "launch wizard") by selecting it and clicking on "Actions" -> "Edit inbound rules"


14. Add a new rule, and add the following: 

    -Type: Custom TCP
    -Port range : 8080
    -Source type: Custom
    -Source : 0.0.0.0/0


    (Make sure that every single other rule has source type to "custom" and the source as 0.0.0.0/0)

	And click on "Save Rules"

15. Do the same with outbound rules, though when you add the new rule, add the following:

    -Type: All traffic.
    -Destination type: Custom
    -Destination: 0.0.0.0/0



16. Go to the "Elastic IPs" section, and allocate a new IPV4 address.



17. Select your new allocated address, go to "Actions" and select "Associate IP Address"
    then, select your EC2 instance.
    
    
18. Go back to your instance dashboard, select your instance and click on "Instance State".
    Set it to "Start" and then click on "Connect", as well as in the next page.
    
    
 
19. Copy and paste these commands, which installs and configures Docker, Nginx, and
    the basic system utilities. Nginx is needed for the frontend to be rendered on 
    the webpage.
    
#Update system packages and install Java and utilities

sudo apt update && sudo apt upgrade -y
sudo apt install openjdk-17-jdk -y
sudo apt install -y ca-certificates curl

#Set up Docker's official keys

sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

#Install Docker Engine and plugins

sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

#Install and configure Nginx

sudo apt install nginx -y
sudo systemctl start nginx
sudo systemctl enable nginx

#Clean default Nginx page and deploy web files from repository

sudo rm /var/www/html/index.nginx-debian.html
git clone --depth 1 --single-branch --branch feat/API_Assets --no-checkout https://github.com/CAAF299/Final_Project_Internship
cd Final_Project_Internship/
git sparse-checkout set Frontend_Assets
cd Frontend-Assets/
sudo cp index.html result.html rstyle.css script.js styles.css /var/www/html/ 



20. For testing endpoints, the following are: 


http://ec2-[public-ip-in-hashes].[region-in-hashes].compute.amazonaws.com/[endpoint] (Frontend)

https://[API_Gateway_ID].execute-api.[region-in-hashes].amazonaws.com/default/api/users (For testing with curl POST, PUT and DELETE requests).

https://[API_Gateway_ID].execute-api.[region-in-hashes].amazonaws.com/default/api/users/info (Checking with a GET request if its alive).



//TROUBLESHOOTING: 


If you have an error when making a request, you may have done 1 of 
these mistakes:


1. You didn't append the "/default" in the url. Please do it before 
   curling with "/api/users"


2. You havent met the parameters of the info required in a field.

   -If you are interacting with the frontend, and get a popup, press F12
   and head over to the "network" section , and look for an error like :
   "First name needs to be at least 4 characters long".

   -If you are doing this with curl, just send it again with the proper info,
   it'll behave identically.


3. You are running it locally. Use the "http://localhost:8080/default/[endpoint]" 
   if you are running it on your local PC via docker or maven and doing the request
   with curl.


//TECHNICAL CHOICES


Using DynamoDB over another Database Management System : 

DynamoDB is a flexible, non-relational database system, using key:value pairs
as the format for their entries. This helps a lot because its simple to manage
and set up + it combines perfectly with the app. It helped me understand that 
the record needs a sort of a unique ID number for it to be queried.


Implementing Lambda instead of just an EC2 instance :

Similarly to DynamoDB, it is a Fully Managed Service, meaning that its flexibility
and ease of use made me focus more on the actual project than anything. It worked
seamlessly without any complex configuration aside from the API Gateway. It is super
convenient for Backend deployment.


Using cartoons over minimalism in the Diagram :

I think the exaggeration of the illustrations isn't just for aesthetic purposes, rather
than it gives an explicit idea on the architecture in a simplified form, while making it
memorable for the consumer, and making it easy to understand in a few words , without over-
complicating things.


Implementing those services over any other service:

The objective of the project is to familiarize myself with the environment of Cloud Architecture,
and it honestly made me understand a lot more with these services. The services used are easy to
learn and manage, prioritizing my learning in the general idea on how it works and it's inner workings
over fancy cloud jargon and technical terms.


 


