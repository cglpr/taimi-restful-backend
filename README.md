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

For Google DataTable (usable directly as source data) (json):
http://<host>:<port>/TaimiBackend/rest/skilldemand/googlechart

For orher/nvd3 charts:
http://host:port//TaimiBackend/rest/skilldemand/nvd3
Supports JSONP callback =>
http://host:port//TaimiBackend/rest/skilldemand/nvd3?callback=drawChart

