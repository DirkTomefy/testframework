package com.test.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import com.dirkfw.session.*;
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
    public List<Message> getAll(ApplicationContext applicationContext, DfwSession session) {

        Integer count = session.getAttribute("requestCount", Integer.class);
        session.setAttribute("requestCount", (count == null ? 0 : count) + 1);

        return service(applicationContext).findAll();
    }

    @JsonResponse
    @UrlMapping(value = "/api/message/create", httpMethod = UrlHTTPMethod.POST)
    public List<Message> create(
            @ObjectParam(name = "user") User user,
            @ObjectParam(name = "category") Category category,
            @ObjectParam(name = "messages") List<Message> messages,
            ApplicationContext applicationContext,
            DfwSession session) {

        if (user.getCinFile() != null && !user.getCinFile().isEmpty()) {
            try {
                Path stored = user.getCinFile().transferTo(CIN_UPLOAD_DIR);
                user.setCinFilePath(stored.toString());
            } catch (IOException e) {
                throw new RuntimeException("Sauvegarde CIN échouée", e);
            }
        }

        List<Message> created = service(applicationContext).createAll(user, category, messages);

        session.setAttribute("lastUserName", user.getName());
        session.setAttribute("lastUserCin", user.getCin());

        Integer count = session.getAttribute("createdCount", Integer.class);
        session.setAttribute("createdCount", (count == null ? 0 : count) + created.size());

        return created;
    }

    @JsonResponse
    @UrlMapping(value = "/api/session")
    public Map<String, Object> sessionInfo(DfwSession session) {
        return session.getAttributes();
    }

    @JsonResponse
    @UrlMapping(value = "/api/session/reset")
    public String resetSession(DfwSession session) {
        session.clear();
        return "OK";
    }
}