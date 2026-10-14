package com.test.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.dirkfw.annotation.Controller;
import com.dirkfw.annotation.JsonResponse;
import com.dirkfw.annotation.ObjectParam;
import com.dirkfw.annotation.UrlMapping;
import com.dirkfw.mapping.ModelAndView;
import com.dirkfw.mapping.UrlHTTPMethod;
import com.test.model.Category;
import com.test.model.Message;
import com.test.model.User;
import com.test.service.MessageService;

@Controller
public class MessageController {

    private static final String CIN_UPLOAD_DIR = "/home/tomefydirk/Documents/CIN-UPLOADS";

    public MessageController() {}

    private MessageService service(ApplicationContext ctx) {
        return ctx.getBean("messageService", MessageService.class);
    }

    @UrlMapping(value = "/message/form")
    public ModelAndView form(ApplicationContext applicationContext) {
        ModelAndView mv = new ModelAndView("message-form");
        mv.setAttribute("messages", service(applicationContext).findAll());
        return mv;
    }

    @JsonResponse
    @UrlMapping(value = "/api/message")
    public List<Message> getAll(ApplicationContext applicationContext) {
        return service(applicationContext).findAll();
    }

    @JsonResponse
    @UrlMapping(value = "/api/message/create", httpMethod = UrlHTTPMethod.POST)
    public List<Message> create(
            @ObjectParam(name = "user") User user,
            @ObjectParam(name = "category") Category category,
            @ObjectParam(name = "messages") List<Message> messages,
            ApplicationContext applicationContext) {

        if (user.getCinFile() != null && !user.getCinFile().isEmpty()) {
            try {
                Path stored = user.getCinFile().transferToDirectory(CIN_UPLOAD_DIR);
                user.setCinFilePath(stored.toString());
            } catch (IOException e) {
                throw new RuntimeException("Sauvegarde CIN échouée", e);
            }
        }

        return service(applicationContext).createAll(user, category, messages);
    }
}