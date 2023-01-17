package com.example.rumblr.controller;

import com.example.rumblr.domain.User;
import com.example.rumblr.domain.dto.CaptchaResponseDto;
import com.example.rumblr.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController
{
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserSevice userSevice;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")  // Переход на ссылку регистрации
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")  // Обработка регистрации пользователя
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponce,
            @Valid User user,
            BindingResult bindingResult,
            Model model)
    {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean differentPasswords = !user.getPassword().equals(passwordConfirm);

        // Капча
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Необходимо заполнить reCaptcha");
        }

        if(isConfirmEmpty)
        {
            model.addAttribute("password2Error", "Необходимо повторно ввести пароль");
        }

        // Если при регистрации пароли не сходятся
        if(user.getPassword() != null && differentPasswords)
        {
            model.addAttribute("passwordError", "Пароли не совпадают!");
        }

        // Если не заполнены данные
        if(isConfirmEmpty || differentPasswords || bindingResult.hasErrors() || !response.isSuccess())
        {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userSevice.addUser(user))  // Если пользователь уже зарегистрирован
        {
            model.addAttribute("usernameError", "Данное имя пользователя уже используется!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")  // Активация пользователя
    public String activate(Model model, @PathVariable String code)
    {
        boolean isActivated = userSevice.activateUser(code);  // Узнаем активирован ли данный пользователь

        if (isActivated)
        {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Код активации успешно подтвержден!");
        }
        else
        {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден!");
        }

        return "login";
    }
}
