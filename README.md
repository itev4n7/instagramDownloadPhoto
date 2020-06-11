# instagramDownloadPhoto
In the project i used libraries such as:
- testng
- selenium/selenide
- webdrivermanager
- log4j
- reportportal
- mariadb
- owner
- j2html
- c3p0

# Used patterns in the project: page object & page factory, [builder](https://github.com/itev4n7/instagramDownloadPhoto/blob/master/src/test/java/com/instagram/download/photo/parameters/UserParameters.java), [singleton](https://github.com/itev4n7/instagramDownloadPhoto/blob/master/src/test/java/com/instagram/download/photo/connections/DataPoolingConnection.java)
In the project i work with:
- webdriver(selenuim/selenide/selenoid)
- building xpath locators
- database and sql
- listeners
- jenkins & mariadb & selenoid & reportportal is spin up in docker
- [jenkinsfile](https://github.com/itev4n7/instagramDownloadPhoto/blob/master/Jenkinsfile)
- parallel test methods([testNG features](https://github.com/itev4n7/instagramDownloadPhoto/blob/master/TestNG.xml#L3))
- parallel connection to database(c3p0)

# Made a [maven plugin](https://github.com/itev4n7/blobtohtml-maven-plugin)
This plugin make html-report with photo inside the database (creating html-report using j2html)
I stick to the SOLID rule -> Single responsibility


