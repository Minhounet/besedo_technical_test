# Besedo technical test

This project is an answer to the test described in [PD-Javatechnicaltest-140323-1000.pdf](Documentation%2FPD-Javatechnicaltest-140323-1000.pdf).
The goal is be able to inject kind of "Message" , to search them and to generate a csv report.

## Summary

- [Prerequisites](#prerequisites)
- [Build](#build)
- [Run](#run)
- [Author](#author)

## Prerequisites

Java 19 is needed to compile the project.
Please go to https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html (url should still be correct, otherwise google it !)

## Build

You can use the dos command line or the unix command line

```
gradlew clean build
```

If you work with several java versions you have to specify the java version and avoid the error below:
![img.png](Documentation/compilation_failed.png)

```
gradlew -Dorg.gradle.java.home=PATH_TO_YOUR_JDK19 clean build 
```

## Run

We use directly the gradle command as we decided in this case not to bother creating a bash/dos script.

```
gradlew bootrun
```
or
```
gradlew -Dorg.gradle.java.home=PATH_TO_YOUR_JDK19 bootrun 
```

## Friendly UI
Swagger is installed in this project, go to http://localhost:8080/swagger-ui/index.html.

![img.png](Documentation/swagger.png)


## Author

Quang-Minh TRAN