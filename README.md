# JDBC

# JDBC : Project setup

#
Theory:

Java DataBase Connectivity (JDBC)

JDBC drivers

Properties file

Factory and Singleton design patterns

Java try with resources

Practical tasks:

Create automation project

Description: Create automation project using Java 11 and Maven. Find and add the appropriate JDBC driver that:

is compatible with the target DB

is compatible with Java 11

Create PropertiesHelper and properties file

Description: Create read-only PropertiesHelper class that can retrieve different properties from a configuration file.

Create a config.properties file containing all the data connection configuration (username, password, URL, etc). Create a PropertiesHelper class that can read properties from that configuration file.

Create database connection that is then automatically closed in a Try with resources block

Description: create a database helper class with a method that creates and returns a new database connection. This connection then can be automatically closed by try with resources block.

Create a database connection before tests start and close it after last test is executed

Description: create a database helper class with a method that creates and returns a single database connection. New connection is created only if the old one gets closed and is not alive anymore. Connection is created before all tests and closed at the end of the test execution.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to create java projects that use JDBC drivers

how to establish database connections

how to use and implement Singleton and Factory design patterns

how to create and use configuration files
