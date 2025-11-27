# 데모 프로젝트

이 프로젝트는 Gradle로 초기화된 기본적인 Spring Boot 애플리케이션입니다.

## 시작하기

이 지침은 개발 및 테스트 목적으로 로컬 머신에 프로젝트를 설치하고 실행하는 데 도움이 됩니다.

### 전제 조건

시스템에 Java Development Kit (JDK) 17 이상이 설치되어 있어야 합니다.

### 저장소 복제 (Cloning the repository)

```bash
git clone <당신의-저장소-URL>
cd demo
```
`<당신의-저장소-URL>`을 실제 Git 저장소 URL로 바꾸세요.

## 프로젝트 빌드

이 프로젝트는 Gradle을 사용합니다. Gradle Wrapper를 사용하여 프로젝트를 빌드할 수 있습니다.

### 빌드

프로젝트를 컴파일하고 JAR 파일을 빌드하려면:

```bash
./gradlew build
```

이 명령어는 테스트를 실행하고, Java 코드를 컴파일하며, `build/libs` 디렉토리에 JAR 파일을 생성합니다.

### 테스트 실행

모든 테스트를 실행하려면:

```bash
./gradlew test
```

## 애플리케이션 실행

Spring Boot 애플리케이션을 실행하려면:

```bash
./gradlew bootRun
```

또는, 프로젝트 빌드 후 생성된 JAR 파일을 직접 실행할 수 있습니다:

```bash
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 추가 문서

더 자세한 정보는 다음 문서를 참조하세요:

*   [공식 Gradle 문서](https://docs.gradle.org)
*   [Spring Boot Gradle 플러그인 참조 가이드](https://docs.spring.io/spring-boot/3.4.12/gradle-plugin)
*   [OCI 이미지 생성](https://docs.spring.io/spring-boot/3.4.12/gradle-plugin/packaging-oci-image.html)

## Spring Boot + React + Thymeleaf 통합 설정

이 프로젝트는 Spring Boot 백엔드 위에 React 프런트엔드와 Thymeleaf를 함께 사용하는 하이브리드 웹 애플리케이션의 기반 설정을 포함합니다. 주요 특징은 다음과 같습니다.

### 1. 통합 빌드 시스템 (Gradle)
- 하나의 Gradle 프로젝트 내에서 Spring Boot 백엔드와 React 프런트엔드를 동시에 관리합니다.
- `./gradlew build` 명령 실행 시, `frontend` 디렉터리에서 `npm install` 및 `npm run build`가 자동으로 실행되어 React 앱을 빌드합니다.
- 빌드된 React 정적 파일(JS, CSS, HTML 등)은 Spring Boot의 `src/main/resources/static/react` 폴더로 자동 복사됩니다.

### 2. 백엔드 라우팅 및 페이지 처리
- **동적 페이지 (Thymeleaf):** `PageController`가 `/`, `/login` 등 특정 경로의 요청을 받아 Thymeleaf 템플릿(`templates/*.html`)을 렌더링합니다.
- **단일 페이지 애플리케이션 (React):**
    - `ReactAppController`가 `/react/**` 경로의 모든 요청을 처리합니다.
    - 파일 확장자(`.`)가 없는 경로(예: `/react/my-page`)는 React의 클라이언트 사이드 라우팅으로 간주하고, 앱의 진입점인 `index.html`로 요청을 전달하여 새로고침 시 404 오류를 방지합니다.
    - 이 복잡한 URL 패턴을 처리하기 위해 `application.yaml`에서 경로 매칭 전략을 `ant-path-matcher`로 설정했습니다.

### 3. 프런트엔드 설정 (Vite)
- `vite.config.js`에 `base: '/react/'` 설정을 추가했습니다.
- 이를 통해 React 앱이 `/react` 하위 경로에서 제공될 것을 명시하여, 빌드된 `index.html` 파일이 참조하는 JS, CSS 파일의 경로가 올바르게 생성되도록 합니다. (404 오류 해결)

### 4. 개발 환경 실행
- `./gradlew bootRun` 명령 하나로 프런트엔드 빌드와 백엔드 서버 실행이 모두 이루어지도록 설정하여 개발 편의성을 높였습니다.
