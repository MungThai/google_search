<h1 align="center">ReportPortal</h1>

***
## Set Up

### To generate a self-signed TLS(SSL) certificate using the OpenSSL, complete the following steps:
1. Write down the Common Name (CN) for your SSL Certificate. The CN is the fully qualified name for the system that uses the certificate. If you are using Dynamic DNS, your CN should hve a wild-card, for example: *.api.com. Otherwise, use the hostname or IP address set in your Gatway Cluster (example. 192.16.183.131 or dp1.acme.com).

2. Run the following OpenSSL command to generate private key and public certificate.\
```openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 3650 -out cert.pem```
   
3. Because Java doesn't understand PEM format, and it supports JKS or PKCS12. You need to convert certificate and private key (.pem) into a single certificate file (.p12) format.\
```openssl pkcs12 -export -inkey key.pem -in cert.pem -out reportportal.p12```\
   Note: The present certificate **must** be password protected.
   
4. Generate a JKS format keystore from PKCS12 format keystore\
```keytool -importkeystore -srckeystore reportportal.p12 -srcstoretype PKCS12 -destkeystore reportportal.jks -deststoretype JKS```\
   Note: The present certificate **must** be password protected.
---
### Create a certificate.toml file with the content as below:
```
defaultEntryPoints = ["http", "https"]
[web]
address = ":8080"

[entryPoints]
[entryPoints.http]
address = ":80"

[entryPoints.https]
address = ":443"

[entryPoints.https.tls]

[[tls.certificates]] #first certificate
  certFile = "/certs/cert.pem"
  keyFile = "/certs/key.pem"
   
[tls.stores]
  [tls.stores.default]
   [tls.stores.default.defaultCertificate]
  certFile = "/certs/cert.pem"
  keyFile = "/certs/key.pem"
```
---
### How to apply TLS (SSL) certificate reportportal containers
1. Create folders on the server as a start point to configure certifications and data.\
```mkdir -p certs data```\
   Note: Move all the created files into ./certs (ie: *.pem, reportportal.*, certificate.toml)\
   ```mv *.pem ./certs/.```\
   ```mv reportportal.* ./certs/.```\
   ```mv certificate.toml ./certs/.```
   
   
2. Apply the following values in docker-compose.yml file
```
gateway:
    image: traefik:v2.2.8
    ports:
      - "80:8080" # HTTP exposed
      - "443:8443" # HTTPS exposed
      - "8081:8081" # HTTP Administration exposed
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ./certs/:/certs/
    command:
      - --providers.docker=true
      - --providers.docker.constraints=Label(`traefik.expose`, `true`)
      - --providers.file.directory=/certs/
      - --providers.file.watch=true
      - --entrypoints.web.address=:8080
      - --entrypoints.websecure.address=:8443
      - --entrypoints.traefik.address=:8081
      - --api.dashboard=true
      - --api.insecure=true
    restart: always
   
In each service, ensure the volumes mount to "./data"
   volumes:
       - ./data/elasticsearch:/usr/share/elasticsearch/data
     
In each service, add the following below the label:
   labels:
      - "traefik.http.routers.uat.tls=true"
      - "traefik.http.routers.uat.entrypoints=websecure" 
```
---
### How to execute ReportPortal container
1. Run start-restart-with-ssl.sh
2. Run docker-compose.yml in command line
```
   docker-compose -f docker-compose.yml -p reportportal up -d --force-create
```
3. Verify the ReportPortal is up and running
```
Open browser with "https://<hostname>/ui"
```
---
### Integrate ReportPortal with Test Automation Framework
1. In Maven pom.xml file, add the following
```
   <repositories>
      ...
       <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>bintray-epam-reportportal</id>
            <name>bintray</name>
            <url>http://dl.bintray.com/epam/reportportal</url>
        </repository>
    </repositories>

   <dependencies>
     ...
     <dependency>
         <groupId>com.epam.reportportal</groupId>
         <artifactId>agent-java-cucumber6</artifactId>
         <version>5.0.1</version>
         <scope>test</scope>
     </dependency>
     <dependency>
         <groupId>com.epam.reportportal</groupId>
         <artifactId>logger-java-logback</artifactId>
         <version>5.0.3</version>
     </dependency>
   </dependencies>
```
---
2. Create reportportal.properties file. This content copy from ReportPortal profile\
   a. Open ReportPortal\
   b. Login\
   c. Click User -> Profile\
   ![img_2.png](src/img_2.png)
   Note: Copy the rp.endpoint, rp.uuid, rp.project
   ```
   rp.endpoint = https://localhost
   rp.uuid = 473c76ae-140d-499e-8997-27fe98864982
   rp.launch = JunitTest 
   rp.project = google_search
   rp.keystore.resource = ./certs/reportportal.jks
   rp.keystore.password = Password
   
   Note: rp.launch, you can define your own launch (ie. ABC, TestNG, ...) 
   rp.keystore.*, that you already created

---
### How to configure Runner with ReportPortal
1. Open the runner class
2. Add the following
   ```
   plugin = {"pretty", "com.epam.reportportal.cucumber.ScenarioReporter"}
   ```
   same as below:
   ```
   @CucumberOptions(
           features = "src/test/resources/features/GoogleSearch.feature",
           glue = {"com.google.web.search.stepdefs"},
           plugin = {"pretty", "com.epam.reportportal.cucumber.ScenarioReporter"}
   )
   ```
---   
### Execute TestNG with overwrite ReportPortal Launch
```
   mvn clean verify -Dtestng.dtd.http=true -Drp.launch=<new launch>
```