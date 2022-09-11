## Mikhak-backend project
This project is intended to manage information about transportation and registeredRoad maintenance in database.
It receives data in csv format then process it and save them to database.

## Run the Application
To run this application, you need the PostgreSQL service as a database,
a JDK to build a java application , and Configuration file to connect them.

### Step 1 -- Install PostgreSQL
#### Install on Ubuntu
Ubuntu’s default repositories contain Postgres packages, so you can install these using the apt packaging system.

Since this is your first time using apt in this session, refresh your local package index. Then, install the Postgres
package along with a -contrib package that adds some additional utilities and functionality:

```
sudo apt update
sudo apt install postgresql postgresql-contrib
```

To download and install on other operating systems  [click here](https://www.postgresql.org/download/)

### Step 2 -- Using PostgreSQL Roles and Databases

To run the server:

`sudo service postgresql start`

There are a few ways to utilize this account to access Postgres. One way is to switch over to the postgres account on
your server and access the Postgres prompt by typing:
> `sudo -u postgres psql`

To create a user named **_transportation_user_** and to encrypt it with password 'transportation_user',
you can run the following command:
> postgres=# `CREATE ROLE transportation_user with encrypted password 'transportation_user';`

To see all roles:
> postgres=# `\du;`

You can create the **transportation_database** database with the _**CREATE**_ command.
> postgres=# `CREATE DATABASE transportation_database;`
_**transportation_database**_ database will be created.

To grants the CREATE , CONNECT , and TEMPORARY privileges on the **_transportation_database_** database to
**_transportation_user_** role (users are properly referred to as roles):
> postgres=# `GRANT ALL PRIVILEGES ON DATABASE transportation_database TO transportation_user;`

To show databases, run the following commands.
> postgres=# `\l;`
>
To connect to _**transportation_database**_ Database use PostgreSQL database command:
> postgres=# `\c transportation_database;`

TO show all tables:
> postgres=# `\dt`
## Configuration file
First open the project and go to _**src/main/resources**_ directory:

>  `cd src/main/resources`

now create _**application.properties**_ file and open it:

```
touch application.properties
vim application.properties
```

now put following phrases in it:

``` 
# DataSource settings: set here your own configurations for the database connection.
# In this example we have "transportation_database" as database name and 
# "transportation_user" as username and password.

spring.datasource.url=jdbc:postgresql://localhost:5432/transportation_database 


# Login username of the database.

spring.datasource.username=transportation_user


# Login password of the database.

spring.datasource.password=transportation_user


# DDL mode. This is actually a shortcut for the "hibernate.hbm2ddl.auto" property.
# Defaults to "create-drop" when using an embedded database and no schema manager was detected. Otherwise, defaults to "none".

spring.jpa.hibernate.ddl-auto=update


# Whether to enable logging of SQL statements.

spring.jpa.show-sql=true


# server HTTP port.

server.port=8081

```

## Java Development Kit (JDK)
* Install a JDK version 18 (at least)
* Download the dependencies
* Run the DemoApplication as main class
