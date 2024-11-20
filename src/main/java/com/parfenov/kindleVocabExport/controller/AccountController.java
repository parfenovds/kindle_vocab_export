package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.dto.RegisterDTO;
import com.parfenov.kindleVocabExport.entity.basic.AppUser;
import com.parfenov.kindleVocabExport.repository.AppUserRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {
  private final AppUserRepository appUserRepository;

  @GetMapping("/register")
  public String register(Model model) {
    RegisterDTO registerDTO = new RegisterDTO();
    model.addAttribute(registerDTO);
    model.addAttribute("success", false);
    return "register";
  }

  @PostMapping("/register")
  public String register(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result) {
    if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
      result.addError(new FieldError(
          "registerDTO",
          "confirmPassword",
          "Password and Confirm Password do not match"
      ));
    }
    Optional<AppUser> appUserOptional = appUserRepository.findByEmail(registerDTO.getEmail());
    if (appUserOptional.isPresent()) {
      result.addError(new FieldError(
          "registerDTO",
          "email",
          "Email address is already used"
      ));
    }
    if (result.hasErrors()) {
      return "register";
    }
    try {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      AppUser newUser = AppUser.builder()
          .email(registerDTO.getEmail())
          .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
          .build();
      appUserRepository.save(newUser);
      model.addAttribute("registerDTO", new RegisterDTO());
      model.addAttribute("success", true);
    } catch (Exception e) {
      result.addError(new FieldError("registerDTO", "password", e.getMessage()));
    }

    return "register";
  }
}
