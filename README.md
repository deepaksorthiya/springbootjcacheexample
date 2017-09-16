# Spring-boot-sample-ehcache-list-object
-Sample Spring boot + Ehcache with Rest API and Spring JPA. <br/>
-Configure Ehcache with Java (xml-less). <br/>
-How to Modify Cache List with Ehcache.

# How to use
-Create new database name "sampledatabase" in MySQL or SQL Server. <br/>
-Configure database in src/main/resources/application.yml (user/password/host and port) <br/>
-Modify configure cache (cache remove policy, max element, time to remove cache) in src/main/java/com/example/config/CacheConfig.java <br/>
-Change dialect to what database you use. <br/>
-Configure auto generate table when start app in dll-auto. (create, create-drop, update) <br/>
-Send HTTP Get to localhost:9999/user/model, use response data model to work in this project. <br/>

# Contributing
-If this project help you, clicking Star button will help author happy. <br/>
-If you have any question, just open an issue. <br/>
-If you want to add or modify anything please fork it and create pull request.

# Author
-<b>Hau van</b> <br/>
-Email: hau.vd1606@gmail.com
