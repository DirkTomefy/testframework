package com.test.controller;

import org.springframework.context.ApplicationContext;

import com.dirkfw.annotation.Controller;
import com.dirkfw.annotation.JsonResponse;
import com.dirkfw.annotation.RequestParam;
import com.dirkfw.annotation.SessionRequired;
import com.dirkfw.annotation.UrlMapping;
import com.dirkfw.mapping.ModelAndView;
import com.dirkfw.mapping.UrlHTTPMethod;
import com.dirkfw.session.DfwSession;
import com.test.model.User;
import com.test.repository.UserRepository;
import com.test.service.MessageService;

@Controller
public class AuthController {

    private UserRepository repo(ApplicationContext ctx) {
        return ctx.getBean(UserRepository.class);
    }

    private MessageService messageService(ApplicationContext ctx) {
        return ctx.getBean("messageService", MessageService.class);
    }

    @UrlMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @UrlMapping(value = "/login", httpMethod = UrlHTTPMethod.POST)
    public ModelAndView login(
            @RequestParam(name = "name", isRequired = true) String name,
            ApplicationContext ctx,
            DfwSession session) {

        UserRepository repo = repo(ctx);

        User user = repo.findFirstByName(name).orElseGet(() -> {
            User u = new User();
            u.setName(name);
            u.setEmail(name.toLowerCase().replaceAll("[^a-z0-9]", ".") + "@test.local");
            return repo.save(u);
        });

        session.setAttribute("user", user);

        ModelAndView mv = new ModelAndView("message-form");
        mv.setAttribute("messages", messageService(ctx).findAll());
        mv.setAttribute("currentUser", user);
        return mv;
    }

    private User findOrCreate(ApplicationContext ctx, String name) {
        UserRepository repo = repo(ctx);
        return repo.findFirstByName(name).orElseGet(() -> {
            User u = new User();
            u.setName(name);
            u.setEmail(name.toLowerCase() + "@test.local");
            return repo.save(u);
        });
    }

    @JsonResponse
    @UrlMapping("/connectAsAdmin")
    public String connectAsAdmin(ApplicationContext ctx, DfwSession session) {
        session.setAttribute("user", findOrCreate(ctx, "admin"));
        session.setAttribute("role", "ADMIN");
        return "OK";
    }

    @JsonResponse
    @UrlMapping("/connectAsSimpleUser")
    public String connectAsSimpleUser(ApplicationContext ctx, DfwSession session) {
        session.setAttribute("user", findOrCreate(ctx, "simple"));
        session.setAttribute("role", "USER");
        return "OK";
    }

    @JsonResponse
    @SessionRequired(value = "ADMIN")
    @UrlMapping("/api/role/admin")
    public String adminOnly() {
        return "admin zone";
    }

    @JsonResponse
    @SessionRequired(value = "USER")
    @UrlMapping("/api/role/user")
    public String userOnly() {
        return "user zone";
    }

    @JsonResponse
    @SessionRequired(value = { "ADMIN", "USER" })
    @UrlMapping("/api/role/any")
    public String anyRole() {
        return "any role zone";
    }

    @JsonResponse
    @SessionRequired
    @UrlMapping("/api/me")
    public User me(DfwSession session) {
        return session.getAttribute("user", User.class);
    }

    @JsonResponse
    @UrlMapping(value = "/api/logout", httpMethod = UrlHTTPMethod.POST)
    public String logout(DfwSession session) {
        session.removeAttribute("user");
        session.removeAttribute("role");
        return "OK";
    }
}