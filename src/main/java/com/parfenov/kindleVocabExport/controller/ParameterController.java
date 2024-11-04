package com.parfenov.kindleVocabExport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParameterController {

  @GetMapping("/parameters")
  public String showParametersForm(@RequestParam String userKey, Model model) {
    model.addAttribute("userKey", userKey);
    return "parameters";
  }
}
