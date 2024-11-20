package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.basic.AppUser;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
  private final AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(AppUser.class, email));
    return User.withUsername(appUser.getEmail())
        .password(appUser.getPassword())
        .build();
  }
}
