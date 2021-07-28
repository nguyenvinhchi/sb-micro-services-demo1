
## Build jar & docker images:
cd api-gateway/

mvn clean package

cd config-server/

mvn clean package

cd naming-server/

mvn clean package

cd currency-exchange-service/

mvn clean package

cd currency-conversion-service/

mvn clean package

## Start services in docker

docker-compose up -d

## Test services

###naming-server: 

http://localhost:8761

###api-gateway test route:

curl -L -X GET 'localhost:8765/get'

###zipkin server: 

http://localhost:9411

###limits:

curl -L -X GET 'localhost:8081/limits'

###centralize config

curl -L -X GET 'localhost:8888/litmitsservice/qa'

###currency exchange: 

curl -L -X GET 'localhost:8765/currency-exchange/from/USD/to/INR'

###currency conversion

curl -L -X GET 'localhost:8765/currency-conversion/from/USD/to/INR/quantity/10'

curl -L -X GET 'localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10'

###refresh config

curl -X GET localhost:8081/limits

Change config in git-localconfig-repo/limits.properties

curl -X POST localhost:8888/actuator/busrefresh

curl -X GET localhost:8081/limits

## Generate self-certificate key store
<code>
keytool -genkeypair -alias apiKeyStore -keyalg RSA \
-dname "CN=Chi Nguyen,OU=API development,O=example.com,L=Hanoi,S=VN,C=HN" \
-keysize 2048 -keystore apiKeyStore.jks -validity 3650 \
-storepass 123456 -keypass 123456
</code>

