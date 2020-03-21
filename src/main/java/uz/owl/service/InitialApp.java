package uz.owl.service;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.owl.domain.Authority;
import uz.owl.domain.User;
import uz.owl.repository.AuthorityRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.AuthoritiesConstants;

import java.util.HashSet;
import java.util.Set;

@Service
public class InitialApp {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public void init() {
        insertAuthorities();
        insertBasicUser();
    }

    private void insertAuthorities() {
        {
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.ADMIN);
            authorityRepository.save(authority);
        }
        {
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.ANONYMOUS);
            authorityRepository.save(authority);
        }
        {
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.USER);
            authorityRepository.save(authority);
        }

    }

    private void insertBasicUser() {
        User user = new User();
        user.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findByName(AuthoritiesConstants.ADMIN));
        authorities.add(authorityRepository.findByName(AuthoritiesConstants.USER));
        user.setAuthorities(authorities);
        user.setEmail("makhmudovbekhruzish@gmail.com");
        user.setFirstName("Bekhruz");
        user.setImageUrl("https://dl.kraken.io/api/e5/be/54/f6d6dbd7fe1108bd4c296729a6/me.jpg");
        user.setLangKey("en");
        user.setLastName("Makhmudov");
        user.setLogin("bekhruz");
        user.setPassword(passwordEncoder.encode("admin"));

        userRepository.save(user);
    }

}
