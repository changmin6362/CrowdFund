# 1. Base 이미지 설정 (Java 17 버전 사용)
FROM eclipse-temurin:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너 안으로 복사
# build.gradle에서 설정한 app.jar 이름을 사용합니다.
COPY build/libs/app.jar app.jar

# 4. 컨테이너 실행 시 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
