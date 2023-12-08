# Microsoft-assessment
Microsoft take-home exam, JobID:1601528 Rivera.Emanuel and v-smithstacy<p>
The basic steps here are to download the project as a zip file, unzip it on your computer, and follow the videos/instructions attached.

This will start with an in-line comment subject to change in the future depending on additional tasks, change in tasks, additional info obtained, connectivity with the database, etc., which will give the reader an overview of how I attempted the task, what was on my mind when doing the task, how I approached it, my logic, why I did something's over the other, and what was my approach at solving the task.<br>
If you want to jump to the tasks update directly, you can [Jump to Task](#task)<be><p>
There will be an update on Assumptions taken during the completion of the task which can be found here: [Jump to Assumptions](#assumptions)<br>

<a name="comments"></a>
## Comments<br>
for one, I started with the spring boot initializer at start.spring.io, and chose the basic dependencies: Spring Web, Spring Data JPA, and MySQL Driver.<br>
wrote a couple of assumptions to go together with why choose gradle over maven, and why I would assume something like that...<br>
I will be using Intellij which is already pre-installed on my machine, and I have a couple of plugins I already have pre-loaded (JSON viewer, gradle, Lombok, spring framework, docker, git, etc.)<br>
A couple more comments... The code is completed and needs to be uploaded after finishing the doc. There also need to be edits by tomorrow to the doc and the assumptions and the design doc before submission...<br><p>


<a name="assumptions"></a>
## Basic Startup Assumptions<br>

  1. I have chosen to go with the Gradle-Groovy Java project since I am not sure if this will be a typical task of performing CRUD operations on the Employee Dataset, and expose the same externally via APIs or it will get expanded on later in other rounds/interviews.<br>
  2. Here I assume that there will be future tasks entailed to this one, or some additional amendments required for the type of services that are being created, hence I have chosen to use Gradle over Maven.<br>
  3. It is easier to upgrade/change structure/add more functionality with Gradle at a later time.<br>

This is an important assumption because if I were to assume that whatever I am tasked to do is a one-off no more changes would be required moving forward, and that the task is completely defined, I would choose to use Maven for structure.<br>
  
  4. I have attached a couple of videos for the startup of this project with their proper heading.<br>
  5. For the Configuration of this project, I have used Java 17 (stability and familiarity), and not gone for 21. I have added videos for IntelliJ startup and project structuring from scratch.<br>
  6. For this task, since I am using Java-Spring Boot, for dependency injection, I am using the `@Autowired` annotation which ensures loose coupling between components.<br>
  7. For version control, I am using GIT SCM and GitHub desktop.<br><p>

<a name="startupassumptions"></a>
## Basic Project Assumptions<br>
  1. I have added additional functionality and files that are currently empty/not used in the project which help out with running this project on a docker-kubernetes environment, hosting it on cloud platforms, and maintaining CI/CD.<br>
  2. I have also left space for adding Swagger/Sonarqube/Whitesource etc. which I don't have a license for/other time constraints owing to which I haven't yet added them but left out files/space for the same.<br>
  3. With respect to the same, there will be git tokens, authorization with the cloud platform, OKTA, or other MFA like OAuth, etc. which need to be securely stored and used.<br>
  4. Gradle has functionality for adding Log4J directly as a dependency of the project which can be directly added to the build.gradle for logging errors etc. Other than that, we can use other external tools like SumoLogic, Kiali, Kinesis Firehose, etc. for keeping logs at multiple levels of the code. I have created opening files for the same which can be edited/completed later to have a total log of the project. This is important because anything that can go wrong can get caught with the proper server, API, database, etc. logs. Multilevel logs also help ascertain where exactly the break happens and shorten our debugging time if we don't know the code, but know the log.<br>
  5. Another important step that I have not covered because the project mentioned Open Endpoints, but in general is very useful and necessary for user Authorization/Authentication. Only authenticated users should be able to access the API endpoints and of those authenticated with ID Password (we can use standard SHA-256, but it can go as high as SHA-4096 for more security), only the Authorized users (using tokens/sessions) who have a certain level of access should be able to make Create/Update/Delete changes to the database. of the CRUD, Read is the least required to be managed, but the rest should be monitored properly.<br>
  6. As for Code Coverage, Spring Boot comes with its coverage option, or we can add the 'JaCoCo' code coverage locally to our project. I prefer using SonarQube (and its local plugin for testing code locally) for code coverage because integrating code with SonarQube also helps with better coding practices, it identifies code smells and vulnerabilities.<br>
  7. While designing code, it is also imperative to test the connectivity with every Microservice component we have, along with their RTT for connection. Older databases generally get slower along with larger datasets, and for certain processes, it is important to make sure that system timeout does not happen owing to processing speed/delays from the database, etc. which I am assuming can be expanded on later if the task deems so.<br>
  8. This is explained more in detail in the design doc, however, it is important to have a window service implementing FIFO (using proper Load Balancers) over the fragmented distributed databases which should have concurrency enabled for all Create/Update/Delete transactions taking place. Patch is a good use here.<br>
  9. I am assuming that we are making sure proper that care is being taken to choose a database implementation which supports proper granularity so that the employee records are properly locked/unlocked and accessible by different users.<br>
  10. Since I am using a localized database of MySQL, i have added the steps to run the same on your local machine and added the exported data file which can be imported on any local system, hosted online/locally, and the same database can be connected to using the file present on GitHub. Generally, databases have online hosting, which while development, I am used to connecting, while at the same time for specific projects, I also have a local copy on my machine to test certain things. Here, I have only uploaded one copy of a straightforward schema-1 table database to explain CRUD operations. In general, it is good to have multiple distributed and fragmented pieces of database, with different levels of access, and multiple backups for the same, in case one or more pieces are down.<br>
  11. Here, depending on the type of project/work, we can also add Redis or some other intermediary for caching. With this project of billions of employees, caching is not very useful, however, fragmentation can prove to be very useful. Delete being a write heavy request, I also want to make sure that whenever C-R-U requests come in, we check with caches like Redis to make sure that these operations are not being performed on something that is already to be deleted, and then batch delete everything once every set time interval. This will also reduce the required database indexing (if we are indexing) and the intervals at which we need to continue indexing it.<br>
  12. Fragmentation can be achieved based on multiple cases, most common ones would be:<br>
        &nbsp;&nbsp;a. If the case is searching/processing over employee names: Fragmenting the DB by first letters of names (further fragmentation can be done based on last names and so on) which yields 26 fragments (26 letters of the alphabet) of the data.<br>
        &nbsp;&nbsp;b. If the case is searching/processing over employee IDs: Fragmenting over Employee ID numbers is also good since we can divide the billion employees into 10 groups of 100 million which start from 0 to 99.99.. million, 100 million to 199.99.. million, etc.<br>
        &nbsp;&nbsp;c. If the case is area-based: Fragmentation can be done based on the geological locations of users/employees, something like East Coast/West Coast or region-based like Chicago/Virginia/Ohio, etc. or continent-based like NA/SA/Asia, etc. Here we can also have a combination of multiple of these like within North America (NA) we have multiple fragmented databases in Chicago, Ohio, Virginia, etc.<br>
        &nbsp;&nbsp;Here it is important to understand the concept of Concurrency and Load Balancers. Load Balancer should send the incoming requests to any of the databases using FIFO windows, and all update requests should be concurrently sent to all the databases at the same time.<br>
  13. To have consistency and availability of data, I assume that fragmenting data into separate databases will allow for the microservice to keep servicing at high RPS, in cases where a database or a microservice server fails, only users of that fragment will be affected. Even this issue can be partially mitigated by having multiple copies of each fragment.<br><p>
  14. Depending on the type of data being stored, it is also important to have WebHooks, file storage, etc., which need their own authentication, validation, constraints, etc.

<a name="task"></a>
## Section 1<br>
Setting Up Database:<br>
Usage of MySQL.<br>
Created Schema employee-microsoft.<br>
Created table employee.<br>
Associated columns like ID, Name, Role, Reports To, Salary, Department, IsActive. Additional columns/tables can be added as required by the task. For the current task of just showcasing CRUD operations, this one table is sufficient.<br>
Basic Primary key, not null, unique constraints added, types of columns, etc. are the basics and added as per my discretion.<br><p>

## Section 2<br>
Connecting with Spring-Boot:<br>
application.properties file in the main-->java-->resources folder which holds details about the database. It is important to change this file for database connectivity and for the project to boot up (if you want to test it out locally).<br>
With Java, we have Java Persistence API (JPA) available as ORM which is what I have used. There are other ORM's like Hibernate, ActiveJDBC, Torque, etc. For C# I am familiar with NHibernate, ORMLike, Dapper, etc. used in conjunction with service meshes like Istio, Linkered, etc.<br>
By using JPA, I have created Entity class which is the Java equivalent for adjoining database tables. An entity (instance of entity class) is the Java equivalent for table columns. Each entity object represents a record of any given table.<br><p>

## Section 3<br>
Creating Services and Repo's:<br>
Basic business Logic is added to these. They query the database and bridge it to the controller/API endpoint.<br>
CRUD is created, read, update, delete. There is also Patch which is useful. Of these, get is the only read operator, while all else deal with some form of database update (insert/update/delete).<br>
Appropriate instances/autowiring done for OOP.<br>
Java Spring Boot has basic annotations like @GetMapping, @PostMapping, @PutMapping, and @DeleteMapping for the same. Which is all implemented with easy to understand names of the same.<br><p>

## Section 4<br>
Testing:<br>
Testing is in multiple formats including Unit testing, functional, UAT, Regression, security, etc. Here the basic unit testing has been completed.<br>
Simple method testing allows us to test expected results against actual tests.<br>
Mocked controller endpoints as well to test whether the endpoints relay the data from controllers to services/repos properly.<br><p>

## Section 5<br>
Postman/Swagger testing the final code:
Creation of Postman Collection to check and test all the API requests for the corresponding controllers.<br>
Different formats of requests were sent.<br>
Exported the said postman collection that can be imported locally on other machines to test the same.<br>
