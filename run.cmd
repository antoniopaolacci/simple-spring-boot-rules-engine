@echo off

rem -------------------------------------------------------------------------
rem Set the java and maven environment variable for the active shell
rem -------------------------------------------------------------------------

set JAVA_HOME="C:\PROGRA~1\Java\jdk1.8.0_121"
set JRE_HOME="C:\PROGRA~1\Java\jdk1.8.0_121"
set PATH=%JAVA_HOME%\bin;%PATH%
@echo . Java 8 set.

set M2_HOME=C:\apache-maven-3.3.9
set M2=%M2_HOME%\bin
set PATH=%M2%;%PATH%
@echo . Mvn 3.3.9 set.

start cmd /c "java -jar target/simple-spring-boot-rules-engine-1.0.0.jar"

pause