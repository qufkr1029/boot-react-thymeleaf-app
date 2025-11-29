package ai.junbeom.demo.account.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AccountController {

    @GetMapping({"/", "/login"})
    public String login(HttpServletRequest request, Model model) {
        // log.trace("Trace 메시지: 디테일한 실행 흐름");
        // log.debug("Debug 메시지: 개발 중 확인용 값 = {}", 22);
        // log.info("Info 메시지: 서버가 정상적으로 시작됨 : {}", 32);
        // log.warn("Warn 메시지: 캐시가 초기화되지 않았습니다. : {}", 44);
        // log.error("Error 메시지: DB 연결 실패 : {}", 22);
        // log.info("Log 클래스는 무엇이냐면 : {}", log.getClass().getName());

        // Get client IP address
        String clientIp = request.getRemoteAddr();

        // Get current time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        // Add attributes to the model
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);

        return "account/login";
    }

    @GetMapping("/login2")
    public String customLogin() {
        return "account/login2";
    }

    @PostMapping("/login2")
    public String processCustomLogin(@RequestParam String username, @RequestParam String password) {
        // TODO: 수동 로그인 처리 로직 구현
        log.info("Attempting custom login for user: {}", username);
        return "redirect:/";
    }
}
