# How to Run

## If you have docker installed on your computer
run the following command in your terminal
```
cd embl-assessment
docker build -t rest-api-docker
nohup docker run -p 8080:8080 rest-api-docker > rest-api.log 2>&1 &
```
## If you have JDK installed on your computer
run the following command in your terminal
```
 cd embl-assessment
 nohup java -jar assessment-0.0.1.jar > rest-api.log 2>&1 &
```

# How to call the REST API
## Get the autherization token, attention: the server only has one user with username "admin" and password "admin"
 ```
  curl -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password":"admin"}' "http://localhost:8080/v1/api/auth"
```

 You will get the response like this:
 ```
    {"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYxNDg2NDQ0NywiZXhwIjoxNjE0OTUwODQ3fQ.uxHvRKHJJimqTdjlHmDvPxuXT2CZNiVxTmvs6EZXHA0PnpBgMgcKZxcZPTXvoOSq-LYdrHnJsj6fgu7Z4GOe2w","userName":"admin","roles":["admin"]}
 ```

## Call the REST API

### Create Person Entity
Replace "change_me" below with the token got from first step.
```
    curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X POST -d '{
            "person": [
    {
        "firstName": "a",
            "lastName": "b",
            "age": 1,
            "favouriteColor": "red"
    },
    {
        "firstName": "c",
            "lastName": "d",
            "age": 1,
            "favouriteColor": "blue"
    },
    {
        "firstName": "e",
            "lastName": "f",
            "age": 1,
            "favouriteColor": "gree"
    }
    ]
}' "http://127.0.0.1:8080/v1/api/people"
```

### Update Person Entity
Replace "change_me" below with the token got from first step.
```
    curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X PUT -d '{
            "person": [
    {	
    	"id": 3,
        "firstName": "aa",
            "lastName": "bb",
            "age": 2,
            "favouriteColor": "green"
    },
    {
    	"id": 4,
        "firstName": "cc",
            "lastName": "dd",
            "age": 2,
            "favouriteColor": "green"
    }
    ]
}' "http://127.0.0.1:8080/v1/api/people"
```

### Search Person Entitiy
Replace "change_me" below with the token got from first step.
```
curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" "http://127.0.0.1:8080/v1/api/people?firstname=John&lastname=Keynes"
```
### Delete Person Entity
Replace "change_me" below with the token got from first step.
```
curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X DELETE "http://127.0.0.1:8080/v1/api/people/3"
```
# Tech Stacks
#### JAVA8
#### H2 Database
#### Spring Boot, Spring Security, Hibernate
#### JWT


# Security
The Application that makes use of JWT authentication for securing an exposed REST API.
Attention: The first call "Post /v1/api/auth with username and password" should be in Https protocol. The app use md5 to encode password, it is only for test usage, in real scenario, BCryptPasswordEncoder should be used
![image](https://github.com/zhengxiaoxue/embl-assessment/blob/main/Authenticate%20Process.png)

# Limitation
1. These REST APIs are not well-connected. The client must use predefined rules to construct every URI it wants to visit. For example, the response of search API can inlude links of delete URI and update URI of the resource. The server can guide the client from one resource state to another by sending forms in its representations.

2. Some clients may not support DELETE or PUT Http method. This can be resolved by using GET Http method and set the real Http action in query string like "/resource?_method=delete". 


5. DELETE API doesn't support Batch operations. To support batch operation, we can expose a resource for every set of resources. And we also need combine Https Status and representations to tell clients the status of each resource.

4. Search API only support retrive entities with firstname and lastname. And in my design, firstname plus lastname form the unique index.








