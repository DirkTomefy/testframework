package com.test.controller;

import com.test.model.User;
import com.test.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dirkfw.annotation.*;
import com.dirkfw.mapping.ModelAndView;
import com.dirkfw.mapping.UrlHTTPMethod;
import org.springframework.context.ApplicationContext;

@Controller
public class UserController {

    public UserController() {}

    private UserService service(ApplicationContext ctx) {
        return ctx.getBean("userService", UserService.class);
    }

    @UrlMapping(value = "/form")
    public ModelAndView form() {
        return new ModelAndView("form");
    }

    @UrlMapping(value = "/test")
    public ModelAndView test(ApplicationContext applicationContext) {
        ModelAndView mv = new ModelAndView("index");
        mv.setAttribute("users", new ArrayList<>(service(applicationContext).findAll()));
        return mv;
    }

    // -------- API JSON --------

    @JsonResponse
    @UrlMapping(value = "/api/user")
    public List<User> getUsers(ApplicationContext applicationContext) {
        return service(applicationContext).findAll();
    }

    @JsonResponse
    @UrlMapping(value = "/api/user/by-id")
    public User getById(@RequestParam(name = "id") Long id,
                        ApplicationContext applicationContext) {
        Optional<User> user = service(applicationContext).findById(id);
        return user.orElse(null);
    }

    @JsonResponse
    @UrlMapping(value = "/api/user/search")
    public List<User> search(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "limit", isRequired = false) Integer limit,
            ApplicationContext applicationContext) {

        List<User> all = service(applicationContext).findAll();
        List<User> result = new ArrayList<>();
        for (User u : all) {
            if (u.getUsername() != null
                    && u.getUsername().toLowerCase().contains(username.toLowerCase())) {
                result.add(u);
            }
        }
        if (limit != null && limit > 0 && result.size() > limit) {
            return result.subList(0, limit);
        }
        return result;
    }

   
    @JsonResponse
    @UrlMapping(value = "/api/user/create", httpMethod = UrlHTTPMethod.POST)
    public User create(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            ApplicationContext applicationContext) {
        return service(applicationContext).createUser(username, password);
    }
}