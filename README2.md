spring-demo
===========

Simple Annotation Based Spring Demo Web-Application for Cloud Foundry.

Dependencies
------------

-    Running mysql database (application.properties)
-    maven2
-    Spring Web
-    Hibernate 4.3

Usage
-----

Local

    mvn clean package
    cp target/cf-spring-demo-1.0.war <tomcat-app-dir>
    <Start Tomcat>

Cloud

    mvn clean package
    cf push spring-demo -p target/cf-spring-demo-1.0.war -m 256m --no-start

    cf create-service <mysql-service> <plan-name> spring-demo-db
    cf bind-service spring-demo spring-demo-db
    cf start spring-demo

If you want to use the "manifest.yml" file to save typing the parameters for
the comman "cf push", you probably need to edit the file prior to execution
of "cf push".

Configuration
-------------

Local

If you want to connect to a mysql database which does not run on localhost or
your connection settings differ from the provided defaults, then you want to
modify the file `src/main/resources/application.properties`.

Cloud

In the class `SpringDemoWebApplication` it is assumed that exactly one
service instance is bound to this application (see JavaDoc comments). This
service instance is assumed to be mysql or mariadb. If these assumptions
are not valid, you want to modify the code.

Feedback
--------

Most welcome. Create pull-requests or contact me via email.
