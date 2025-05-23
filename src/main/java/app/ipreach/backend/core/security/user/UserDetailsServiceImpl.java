package app.ipreach.backend.core.security.user;

import app.ipreach.backend.core.exception.custom.RequestException;
import app.ipreach.backend.shared.constants.Messages;
import app.ipreach.backend.users.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl loadUserByUsername(String email) {
        app.ipreach.backend.users.db.model.User user = userRepository.findByEmail(email).orElseThrow(() ->
            new RequestException(HttpStatus.NOT_FOUND, Messages.ErrorClient.USER_NOT_FOUND));

        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
            .map(r -> new SimpleGrantedAuthority(r.name()))
            .toList();

        UserDetails userDetails = User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRolesUserDetails())
            .authorities(authorities)
            .build();

        return new UserDetailsImpl(userDetails, user.getId());

    }

}
