package com.test.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.dirkfw.annotation.Controller;
import com.dirkfw.annotation.JsonResponse;
import com.dirkfw.annotation.UrlMapping;
import com.dirkfw.mapping.ModelAndView;
import com.dirkfw.mapping.UrlHTTPMethod;
import com.test.model.Category;
import com.test.model.Message;
import com.test.model.User;
import com.test.service.MessageService;

@Controller
public class MessageController {

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
    public List<Message> create(User user,
                                Category category,
                                List<Message> messages,
                                ApplicationContext applicationContext) {

        return service(applicationContext).createAll(user, category, messages);
    }
}