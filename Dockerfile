# 1. Java 17 환경을 기반으로 시작합니다.
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리를 만듭니다.
WORKDIR /app

# 3. Maven Wrapper 파일을 먼저 복사합니다. (빌드 속도 향상을 위한 캐싱)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 4. pom.xml을 기반으로 모든 라이브러리를 다운로드합니다.
RUN ./mvnw dependency:go-offline

# 5. 전체 소스 코드를 복사합니다.
COPY src ./src

# 6. Maven Wrapper를 사용하여 프로젝트를 빌드합니다. (war 파일 생성)
RUN ./mvnw clean install

# 7. 서버를 실행하는 최종 명령어입니다.
#    WAR 파일로 빌드되었으므로, java -jar로 실행합니다.
CMD ["java", "-jar", "target/web-project-0.0.1-SNAPSHOT.war"]