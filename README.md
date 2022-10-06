# CGI Assessment

#### Author - [`Kesava Anirudh Annadanam`]{https://github.com/ak-anirudh}

## Instructions :

- You will be given only READ access to this repository
- Fork this repository into your own bitbucket account
- Create a Pull Request from the branch of your forked repository to master branch of this repository

## Expectations :

- You should create the below scenarios into one single Spring Boot application containing appropriate REST endpoints
- It should be a maven project
- Preferably Java 8 should be used
- A minimal Front End code should be needed to display the output (you can choose your own view)
- Preferably Angular JS should be used if you have knowledge on it
- You should write unit tests for all the code including Front End
- Code should be modular and optimized

### Test 1 - Receipe App

As part of this scenario, you should read the file `receipe.json` and create below REST endpoints

1. **[endpoint 1]** One endpoint to display all receipes
2. **[endpoint 2]** Add another endpoint which takes array of ingredients as input. 
The output should give list of receipes which consists of all the given input ingredients.
        
    For eg., 
    
    If input is ["onions"], then it should list down all the receipe's which has onions in the list of ingredients
    
    If input is ["onions", "mushrooms"] then it should list down all the receipe's which contains onions and mushrooms in the list of ingredients

    **Note:** 
    - The output list of receipe's should be ordered alphabetically according to receipe title
    - The input ingredients should be validated. Only string values should be allowed.
    
Integrate these endpoints with Front End which should have below behaviours

- A button which should display all receipe's in a table
- A multiple selectable dropdown which should select ingredients and passed as input to above endpoint **[endpoint 2]**. 
- The result should be shown in a table.

### Test 2 - Log Analyser app

As part of this scenario, you should read the file `logFile-2018-09-10.log` and perform below actions

- create an endpoint which accepts the log type as input, for eg., DEBUG, WARN or ERROR.
- Display top N recurring errors in sorted order with error descriptions and number of times it occurred. 
- The most recurring error should be shown first

Integrate this endpoint with Front End where the log type is selected from radio button group 

================================================================================================================================

## API

#### Spring Boot 

### name - `cgi-assessment-1`

Commands to run the application 

```shell
mvn clean install
```

```shell
mvn spring-boot:run
```

You can also access Swagger for the OPEN API Specification from [`http://localhost:8080/cgi/`](http://localhost:8080/cgi/)

## Front-End

#### Angular Project for the assignment included in `cgi-assessment-1/src/main/js` 

#### name - `cgi-assessment-FE`



