package project.spring.kiheo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
    public String sayHello() {
        System.out.println("==================");
        return "hello";
    }
}
