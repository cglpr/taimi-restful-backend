# taimi-restful-backend

Provides restful webservices for Taimi front.

# Building
Requires JDK >= 1.7 

* Gradle (v. 2.3 or higher)
> gradle clean build

* Gradlew(.bat) (no local gradle installation needed)
> gradlew.bat clean build

# Usage
Deploy TaimiBackend.war into server container (Tomcat 7 used during development)

Currently provides only google DataTable data for google.visualization chart types.
URL (Returns DataTable data as json):
http://<host>:<port>/TaimiBackend/rest/graph/googlechart
