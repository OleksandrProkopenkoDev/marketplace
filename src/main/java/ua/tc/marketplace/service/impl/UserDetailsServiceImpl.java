package ua.tc.marketplace.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.config.UserDetailsImpl;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      //TODO make changes in flyway migration, Let email be unique
      Optional<User> user = repository.findByEmail(email);
      return user.map(UserDetailsImpl::new)
          .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
}
