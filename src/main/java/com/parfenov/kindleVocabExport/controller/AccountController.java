package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.dto.RegisterDTO;
import com.parfenov.kindleVocabExport.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {
  private final UserService userService;

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("registerDTO", new RegisterDTO());
    model.addAttribute("success", false);
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(
      Model model,
      @Valid @ModelAttribute RegisterDTO registerDTO,
      BindingResult result) {
    if (result.hasErrors()) {
      return "register";
    }

    try {
      userService.registerUser(registerDTO);
      model.addAttribute("success", true);
      model.addAttribute("registerDTO", new RegisterDTO()); // Очищаем форму
    } catch (IllegalArgumentException e) {
      result.rejectValue("email", "error.registerDTO", e.getMessage());
    } catch (Exception e) {
      result.reject("error.registerDTO", "An unexpected error occurred");
    }

    return "register";
  }
}
