package net.gark.mysports.services;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ServiceDetailsImpRepositoryUser implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDetailsImpRepositoryUser.class);

    @Autowired
    private IRepositoryUser userRepository;

    private List<GrantedAuthority> buildUserAuthority(final String role) {
        final Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(role));
        return new ArrayList<>(setAuths);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        LOGGER.info(String.format("Checking for email '%s'", email));

        final IUser myUser = userRepository.findByEmail(email.trim());
        if (myUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        final List<GrantedAuthority> authorities = buildUserAuthority(myUser.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER");

        return buildUserForAuthentication(myUser, authorities);
    }

    private User buildUserForAuthentication(final IUser user,
                                            final List<GrantedAuthority> authorities) {

        LOGGER.info("User authentication - " + user.getEmail() + ":" + user.getPassword());

        return new User(user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
