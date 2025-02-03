package lee.rentalbook.global.auth.service;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @file PrincipalDetailsService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userKey) throws UsernameNotFoundException {
        User user = userService.findByUserKey(userKey);
        return new PrincipalDetails(user);
    }
}
