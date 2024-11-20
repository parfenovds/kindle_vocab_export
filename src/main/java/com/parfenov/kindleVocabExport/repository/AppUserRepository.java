package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.basic.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  public Optional<AppUser> findByEmail(String email);
}
