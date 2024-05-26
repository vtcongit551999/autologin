@echo off
rem Chdir to the project directory
cd D:\HOC JAVA 14\2.JAVACORE\loginghe

rem Run the Maven command to build and execute the project
call mvn clean install exec:java

PAUSE