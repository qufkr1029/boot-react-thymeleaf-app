package ai.junbeom.demo.account.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ai.junbeom.demo.account.domain.User;
import ai.junbeom.demo.account.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping({"/login"})
    public String loginPage(HttpServletRequest request, Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        String clientIp = request.getRemoteAddr();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);

        return "account/login";
    }

    @GetMapping({"/"})
    public String indexPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUserId(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("사용자 없음"));

        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("중단점");
        request.getSession().invalidate();

        return "redirect:/login";
    }
    
}
