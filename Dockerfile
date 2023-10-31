FROM openjdk:17

# 호스트 파일 시스템의 JKS 파일을 이미지 내부로 복사
COPY src/main/resources/ssl/keyStore.jks /config/ssl/keyStore.jks
# 파이어베이스 서비스 계정 키 파일을 이미지 내부로 복사
COPY src/main/resources/config/key/serviceAccountKey.json /config/key/serviceAccountKey.json
# db sql 파일을 이미지 내부로 복사
COPY src/main/resources/db/data.sql /config/db/data.sql

COPY build/libs/stokoin-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","app.jar"]