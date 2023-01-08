package com.example.rumblr.controller;

import com.example.rumblr.domain.Message;
import com.example.rumblr.domain.User;
import com.example.rumblr.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController
{
    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")  // Основная страница
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")  // Страница с блогом
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model)
    {
        Iterable<Message> messages;  // Получаем лист всех сообщений

        // Фильтр сообщений, если он не пустой и не равен null
        if (filter != null && !filter.isEmpty())
            messages = messageRepo.findByTag(filter);  // Находим сообщения по тэгу
        else
            messages = messageRepo.findAll();  // В противном случае, отображаем все

        // Добавляем атрибуты
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")  // Добавление сообщений на форум
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file")MultipartFile file
            ) throws IOException
    {
        // Создаем сообщение
        message.setAuthor(user);

        // Если у нас есть ошибки при создании сообщения
        if (bindingResult.hasErrors())
        {
            // То обрабатываем ошибки
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        }
        else  // Иначе создаем сообщение и сохраняем в базе данных
        {
            // Добавление изображения, если оно не пустое, и имя изображение не пустое
            if(file != null && !file.getOriginalFilename().isEmpty())
            {
                // Создаем директорию файлу
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists())
                {
                    uploadDir.mkdir();
                }

                // Создаем уникальное имя
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                // перемещаем файл
                file.transferTo(new File(uploadPath + "/" + resultFileName));

                // И добавляем к изображению
                message.setFilename(resultFileName);
            }

            // Удаляем модель валидации
            model.addAttribute("message", null);

            // Сохраняем в базе
            messageRepo.save(message);
        }

        // Находим все сообщения
        Iterable<Message> messages = messageRepo.findAll();

        // Пихаем в модель
        model.addAttribute("messages", messages);

        return "main";
    }
}
