# Swagger Petstore Sample

This is a forked repo from [Swagger Petsore Sample](https://github.com/swagger-api/swagger-petstore).

I used this project to write some API tests, that can be found in `/src/test/java/ip.swagger.petstore`.
I also added reporting and Github Actions workflow, that runs the tests on each push/pull request created.

## API Tests
For the tests I used the TestNG framework and the API objects test design/pattern.

Each API group has its own API object that inherits from the `BaseApi` object. In `BaseApi` we have some shared methods and constants that can be used in all other objects.

In the `testdata` folder we have most, if not all the test data needed for the tests. Some sensitive data, such as passwords, is saved in Github secrets.

Finally, we have the `tests` folder. In this folder we have the `BaseTest`, which is inherited from all the other tests. Each API has its own test file, with multiple tests inside. All the tests are independent of each other.

## How to run the tests?
In order to run the tests locally, you first need to run a local server, where the API are hosted. You can do that by running the following command:

```
mvn package jetty:run -DskipTests
```

This will start Jetty embedded on port 8080. 

Maven needs to be installed beforehand.

For more details, instructions and other ways how to start the server refer to the original documentation in [Swagger Petsore Sample](https://github.com/swagger-api/swagger-petstore).

After the server is up, the tests can be run by executing the command:

```
mvn test
```
