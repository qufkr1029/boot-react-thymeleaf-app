package ai.junbeom.demo;

import ai.junbeom.demo.account.domain.Account;
import ai.junbeom.demo.account.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder import 추가
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            log.info("Initializing sample data...");

            // 테스트용 계정 생성 (비밀번호는 반드시 인코딩)
            if (!accountRepository.existsByUsername("user")) {
                Account userAccount = Account.builder()
                        .username("user")
                        .password(passwordEncoder.encode("password")) // 비밀번호 인코딩
                        .email("user@example.com")
                        .role("USER")
                        .build();
                accountRepository.save(userAccount);
                log.info("Created user: {}", userAccount.getUsername());
            }

            if (!accountRepository.existsByUsername("admin")) {
                Account adminAccount = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("adminpass")) // 비밀번호 인코딩
                        .email("admin@example.com")
                        .role("ADMIN")
                        .build();
                accountRepository.save(adminAccount);
                log.info("Created admin: {}", adminAccount.getUsername());
            }

            log.info("Sample data initialization complete.");
        };
    }
}
