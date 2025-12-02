package ai.junbeom.demo.account.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.junbeom.demo.account.domain.User;
import ai.junbeom.demo.account.dto.Advice;
import ai.junbeom.demo.account.dto.UserSignupDto;
import ai.junbeom.demo.account.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;

    @GetMapping({"/login"})
    public String loginPage(HttpServletRequest request, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        try {
            final String ADVICE_API_URL = "https://korean-advice-open-api.vercel.app/api/advice";
            Advice advice = restTemplate.getForObject(ADVICE_API_URL, Advice.class);
            model.addAttribute("advice", advice);
        } catch (Exception e) {
            log.error("Failed to fetch advice from external API", e);
            model.addAttribute("advice", new Advice("가끔은 API도 쉬고 싶을 때가 있죠.", "개발자"));
        }
        String clientIp = request.getRemoteAddr();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);
        return "account/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        if (!model.containsAttribute("userSignupDto")) {
            model.addAttribute("userSignupDto", new UserSignupDto("", "", ""));
        }
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignupDto", bindingResult);
            redirectAttributes.addFlashAttribute("userSignupDto", userSignupDto);
            return "redirect:/signup";
        }
        try {
            userService.signup(userSignupDto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("userSignupDto", userSignupDto);
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/signup/check-id/{userId}")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkUserIdExists(userId));
    }

    @GetMapping("/signup/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmailExists(email));
    }


    @GetMapping({"/"})
    public String indexPage(Model model, Authentication authentication) {
        String userId = authentication.getName();
        User user = userService.findUserByUserId(userId);

        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
