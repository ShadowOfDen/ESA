package com.example.rumblr.service;

import com.example.rumblr.domain.Role;
import com.example.rumblr.domain.User;
import com.example.rumblr.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserSevice implements UserDetailsService
{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override // Загружаем пользователей по имени
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username);

        if(user == null)
        {
            throw new BadCredentialsException("Пользователь не найден");
        }

        return user;
    }

    // Добавляем нового пользователя
    public boolean addUser(User user)
    {
        // Проверяем существует ли такое же имя
        User userFromDb = userRepo.findByUsername(user.getUsername());

        // Если да, дропаем
        if (userFromDb != null) { return false; }

        // Если нет, то
        user.setRoles(Collections.singleton(Role.USER)); // Выдаем пользователю роль
        user.setActivationCode(UUID.randomUUID().toString()); // И создаем код активации
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Шифруем пароль


        userRepo.save(user); // Сохраняем пользователя в базе
        sendMessage(user); // Высылаем ему код активации

        return true; // Возвращаем true об успешном создании нового пользователя
    }

    // Отправка мейла пользователю
    private void sendMessage(User user)
    {
        // Если строка мейла не пустая
        if(!StringUtils.isEmpty(user.getEmail()))
        {
            // Генерируем сообщение
            String message = String.format(
                    "Здраствуйте, дорогой %s! \n" +
                            "Приветствуем вас на Rumblr. Пожалуйста, перейдите по следующей ссылке для активации аккаунта: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            // И отправляем его
            mailService.send(user.getEmail(), "Activation code", message);
        }
    }

    // Активация аккаунта пользователя
    public boolean activateUser(String code)
    {
        // Находим пользователя по коду активации
        User user = userRepo.findByActivationCode(code);

        // Если кода нет, то пользователь уже активирован
        if (user == null) { return false; }

        // Если нет, то
        user.setActive(true);  // Активируем пользователя
        user.setActivationCode(null);  // И удаляем код

        userRepo.save(user);  // Сохраняем профиль пользователя

        return true;  // Возвращаем успех активации
    }

    // Нахождение всех пользователей
    public List<User> findAll()
    {
        return userRepo.findAll();  // Возвращаем лист пользователей
    }

    // Сохранение пользователя
    public void saveUser(User user, String username, Map<String, String> form)
    {
        // Установка имени
        user.setUsername(username);

        // Установка роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet())
        {
            if (roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        // Сохранение данных
        userRepo.save(user);
    }

    // Обновление данных в профиле пользователя
    public void updateProfile(User user, String password, String email)
    {
        // Мыло
        String userEmail = user.getEmail();

        // Узнаем, поменял ли пользователь мыло
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        // Если пользователь ввел новый пароль
        if (!StringUtils.isEmpty(password))
            user.setPassword(password);  // То меняем пароль

        // Если поменял, то
        if (isEmailChanged)
        {
            // Создаем письмо
            user.setEmail(email);

            // И генерируем новый код для пользователя
            if (!StringUtils.isEmpty(email))
                user.setActivationCode(UUID.randomUUID().toString());

            // Отправляем письмо
            sendMessage(user);
        }

        // Сохраняем данные
        userRepo.save(user);
    }
}
