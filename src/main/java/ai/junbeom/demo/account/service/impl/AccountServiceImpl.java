package ai.junbeom.demo.account.service.impl;

import ai.junbeom.demo.account.domain.Account;
import ai.junbeom.demo.account.repository.AccountRepository;
import ai.junbeom.demo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account manualLogin(String username, String rawPassword) {
        Account account = accountRepository.findByUsername(username)
                .orElse(null);

        if (account != null && passwordEncoder.matches(rawPassword, account.getPassword())) {
            return account;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return buildUserDetails(account);
    }

    private UserDetails buildUserDetails(Account account) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole());
        return new User(
                account.getUsername(),
                account.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
