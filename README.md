# Spring Boot and BackboneJS
This example demonstrates how **Spring Boot**, **Spring Data JPA** and in the front-end **BackboneJS** can be used together to write web applications easily.

## Frameworks

### Front-end

#### Bootstrap
For rapidly creating prototypes of a web application, a UI toolkit or library will become really handy. There are many choices available, and for this example I chose Twitter Bootstrap.

#### BackboneJS
Backbone.js gives structure to web applications by providing models with key-value binding and custom events, collections with a rich API of enumerable functions, views with declarative event handling, and connects it all to your existing API over a RESTful JSON interface.

### Back-end

#### Spring Boot
Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration.

#### Spring Data JPA
Implementing a data access layer of an application has been cumbersome for quite a while. Too much boilerplate code has to be written to execute simple queries as well as perform pagination, and auditing. Spring Data JPA aims to significantly improve the implementation of data access layers by reducing the effort to the amount that’s actually needed. As a developer you write your repository interfaces, including custom finder methods, and Spring will provide the implementation automatically.

## Installation
Installation is quite easy:

```
mvn clean package
```

Now you can run the Java application quite easily:
```
cd target
java -jar target/winecellar-0.0.1-SNAPSHOT.jar
```
