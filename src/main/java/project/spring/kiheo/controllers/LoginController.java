package project.spring.kiheo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping
    public String login(){
        System.out.println("========LOGIN==========");
        return "/login";
    }

    @PostMapping("/loginProcess")
    public void loginProcess(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model) throws IOException {
        System.out.println("========LOGIN PROCESS==========");
        response.sendRedirect("/main");
    }

    @GetMapping("/main")
    public String main(){
        System.out.println("========MAIN==========");
        return "/success";
    }


}
