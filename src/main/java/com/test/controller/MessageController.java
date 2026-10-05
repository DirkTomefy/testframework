package com.test.controller;

import com.dirkfw.annotation.Controller;
import com.dirkfw.annotation.JsonResponse;
import com.dirkfw.annotation.UrlMapping;
import com.dirkfw.mapping.ModelAndView;
import com.dirkfw.mapping.UrlHTTPMethod;
import com.test.model.Message;
import com.test.service.MessageService;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    public MessageController() {}

    private MessageService service(ApplicationContext ctx) {
        return ctx.getBean("messageService", MessageService.class);
    }

    @UrlMapping(value = "/message/form")
    public ModelAndView form(ApplicationContext applicationContext) {
        ModelAndView mv = new ModelAndView("message-form");
        mv.setAttribute("messages", new ArrayList<>(service(applicationContext).findAll()));
        return mv;
    }

    @JsonResponse
    @UrlMapping(value = "/api/message")
    public List<Message> getAll(ApplicationContext applicationContext) {
        return service(applicationContext).findAll();
    }

 

    @JsonResponse
    @UrlMapping(value = "/api/message/create", httpMethod = UrlHTTPMethod.POST)
    public Message create(Message message, ApplicationContext applicationContext) {
        return service(applicationContext).save(message);
    }
}