# Table of Contents

[Twitter CLI App](#twitter CLI App)

​	[Introduction](#https://github.com/aaantunes/java_apps#introduction-)

​	[Usage](#TwitterUsage)





# Twitter CLI App <a name="Twitter CLI App"></a>

## Introduction <a name="TwitterIntro"></a>

This application uses the Twitter API to create, read and delete tweets on Twitter. This application uses Spring Boot to launch the application and uses HTTP requests to communicate with Twitter by sending and receiving JSON Strings.

## Usage <a name="TwitterUsage"></a>

This application takes in several arguments dependent on which function the user wishes to call. The first argument passed is either `post`, `show`, or `delete` followed by their respective dependency arguments.

`Post Tweet`

Creates a Tweet object with `textStr` and `longitude:latitude` from the remaining arguments passed. 

> i.e. `post "This is a sample Tweet" "-50:75"`

`Show Tweet`

Prints a Tweet object as a `PrettyJSON` String to terminal based on a given `id` argument

> i.e. `show 1149028346438795264`

`Delete Tweet`

Deletes a Tweet based on a given `id` argument. This function also displays the deleted Tweet as a `PrettyJSON` String

> i.e. `delete 1149028346438795264`



## Design and Implementation

The application is run from the interface layer and the flow of control passes down through the service layer to create DTO's from the Repository and then send Http requests to Twitter.

1. `ApacheHttp` - Connects to Twitter API, handles authentication, creates and sends HTTP `GET/PUT/DELETE` requests to Twitter.

2. `TwitterRestDAO` - Data Access Object handles each Tweet object and its respective components. Dependent on `ApacheHttp`.

3. `TwitterService` - Contains the business logic of the application. Verifies each component is complaint with Twitters requirements.

4. `TwitterCLI` - Parses through input arguments and acts as the applications main Controller.

   

### Twitter CLI App Work Flow

<img src="C:\Users\aaant\Downloads\TwitterWorkFlow.png" >

### Libraries

Apache Maven - project management and build automation

Spring Boot - Application launcher and dependency manager

Jackson JSON - Converts JSON Strings to POJO's to send and receive data to TwitterAPI

OAuth 1.0 and HttpClient4 - Authenticate, connect and communicate to Twitter API using HTTP requests

Junit/Mockito - Testing purposes



## Enhancements and Issues

- Implement Update tweet functionality
- Add functionality to output only requested fields in show tweet





# Java JDBC App

## Introduction

This application uses JDBC API to access and manage a PostgreSQL RDBMS database. Through the JDBC API, this application follows the CRUD (Create, Read, Update, Delete) data access pattern to query and update data in the database. This application uses a PostgreSQL database to maintain customer, order, and product information.



## Usage

This application does not take in any command line arguments however the `DatabaseConnectionManager` requires several parameters specific to each user. This includes the `hostName`, `databaseName`, `PSQLUsername`, and `PSQLPassword`.

```java
DatabaseConnectionManager dcm = 
    new DatabaseConnectionManager("hostName", "databaseName", "userName", "password");
```



## Design and Implementation

The application runs from the `JDBCExecutor` by initially creating a PostgreSQL `Connection` dependent on the user specific `DatabaseConnectionManager` as seen in the **usage** section. The user can then create a `CustomerDAO` or `OrderDAO` to pass CRUD operations to the PostgreSQL database through the JDBC Driver Manager.

The CRUD operations are implemented by calling the following DAO methods :

`findById`, `update`, `create`, and `delete`



*Pseudocode*:

```pseudocode
Connection connection = new DatabaseConnectionManager.getConnection()
DAO dao = new DAO(connection)
dao.findById(id)
dao.update(dto)![Blank Diagram](C:\Users\aaant\Downloads\Blank Diagram.png)
dao.create(dto)
dao.delete(id)
```



### Libraries

Apache Maven - project management and build automation

PostgreSQL - JDBC Driver Manager to connect to database



### High Level Work Flow

<img src="C:\Users\aaant\Downloads\JDBCWorkFlow.png" >



### Database Entity Relationship Diagram

<img src="C:\Users\aaant\Downloads\Blank Diagram (1).png" width="500" >

## Enhancements and Issues

- Implement `ProductDAO`, `OrderLineDAO` and `SalespersonDao`
- Add the remaining CRUD functions to `OrderDAO`





# Java Grep App

## Introduction

This application searches for text patterns by recursively parsing through each file in a given directory and outputs any matched lines to a text file. 



## Usage

To run this application, three arguments must be passed including:

`regex` - a special text string used for describing a search pattern

`rootPath` - root directory path

`outFile` - output file name

These three arguments can be passed in the following order `USAGE: regex rootPath outFile` 

> i.e.  `.*data.* /home/centos/dev/jrvs/bootcamp/ /tmp/grep.out`

This example looks through each file in the `/home/centos/dev/jrvs/bootcamp/` directory and outputs all lines containing the keyword **data** to `/tmp/grep.out` text file



## Design and Implementation

The applications main method takes in the three arguments passed and sets the values for the `regex`, `rootPath`, and `outFile`. It then creates a `JavaGrepImp` object and calls the `process` function to run the program. 

The process function recursively iterates through a list of files from the `rootPath` and checks that each line in every file contains the `regex` pattern. If a line contains the pattern, it is written to the `outPath` text file.

*Pseudocode*:

```pseudocode
matchedLines = []
for file in listFiles(rootDir)
	for line in readLines(file)
		if containsPattern(line)
			matchedLines.add(line)
writeToFile(matchedLines)
```

### Libraries

Apache Maven - project management and build automation



## Enhancements and Issues

- Categorize each matched line by it's file name in the`outFile`. 
  - Add the filename where the match took place as a header, followed by all matched lines in said file.
- Include number of matched instances in each file.
- Distinctly show where each new matched line begins and ends.

