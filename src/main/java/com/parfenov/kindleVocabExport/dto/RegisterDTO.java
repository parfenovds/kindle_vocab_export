package com.parfenov.kindleVocabExport.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, message = "Minimum password length is 6 characters")
  private String password;
  @NotBlank
  private String confirmPassword;
}
