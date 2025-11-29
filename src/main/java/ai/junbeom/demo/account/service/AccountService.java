package ai.junbeom.demo.account.service;

import ai.junbeom.demo.account.domain.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService extends UserDetailsService {
    Account manualLogin(String username, String rawPassword);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
