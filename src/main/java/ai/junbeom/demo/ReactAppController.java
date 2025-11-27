package ai.junbeom.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactAppController {

    /**
     * /react 경로에 대한 SPA(Single Page Application) 라우팅을 처리합니다.
     * 클라이언트 사이드 라우트처럼 보이는 경로(즉, 경로에 점(.)이 없는 경우)에 대한 요청만
     * React 애플리케이션의 진입점(index.html)으로 전달합니다.
     * 이는 '/react/**'가 '/react/index.html' 경로까지 잡아서 발생했던 무한 포워딩 루프를 방지합니다.
     *
     * 이 매핑이 처리하는 경로:
     * - /react
     * - /react/
     * - /react/any/path/without/a/dot (점이 없는 모든 경로)
     *
     * 처리하지 않는 경로:
     * - /react/index.html
     * - /react/assets/main.js
     */
    @RequestMapping(value = {"/react", "/react/", "/react/**/{path:[^\\.]*}"})
    public String forwardToReact() {
        return "forward:/react/index.html";
    }
}
