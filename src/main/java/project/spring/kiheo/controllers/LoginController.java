package project.spring.kiheo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import project.spring.kiheo.application.AuthenticationService;
import project.spring.kiheo.application.UserService;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.dto.UserData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {
    private final UserService userService;
    private AuthenticationService authenticationService;

    public LoginController(UserService userService, AuthenticationService authenticationService){
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String login(){
        log.info("===login===");
        return "/login";
    }

    @PostMapping("/loginProcess")
    public void loginProcess(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model) throws IOException {
        log.info("===loginProcess==");
        //User user = userService.findUser("kiheo@gmail.com");
        log.info("===loginProcess==" + request.getParameter("email"));
        log.info("===loginProcess==" + request.getParameter("password"));

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String accessToken = authenticationService.authentication(email, password);
        response.addHeader("Authorization", "tttt");
        response.setHeader("Authorization", "tttt");
        response.sendRedirect("/main");
    }

    @GetMapping("/main")
    //@PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public String main(){
        log.info("===main===");
        System.out.println("========MAIN==========");

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        try {
            factory.setReadTimeout(1000*60*5);
            factory.setConnectTimeout(5000);
            RestTemplate rt = new RestTemplate(factory);
            Map<String, String> param = new HashMap<>();

            param.put("serviceKey", "s4BjbNeg2AwsuaGQ0sFZIuF/HYGdH/5vOHpgNn2bhnY2RT3ZEwOB0ojQutPAKsXGs2FV86L5i6lhccG/BAhTgw==");
            param.put("numOfRows", "10");
            String URI = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList";
            JSONObject result = rt.getForObject(URI, JSONObject.class, param);
            //System.out.println(result);
        } catch (RestClientException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return "/main";
    }

}
