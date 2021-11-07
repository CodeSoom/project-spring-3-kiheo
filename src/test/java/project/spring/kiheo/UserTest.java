package project.spring.kiheo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import project.spring.kiheo.application.UserService;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.domain.UserRepository;
import project.spring.kiheo.dto.UserRegistrationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

@Slf4j
@SpringBootTest
//@DataJpaTest
public class UserTest {
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    //private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp(){
        log.info("========setup==========");
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository, passwordEncoder);
    }


    @Test
    void userTest(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder().build();

        user.changeWith(User.builder()
                .name("TEST")
                .build());

        log.error("==="+user.getName());
        log.error("==="+user.getPassword());
        assertThat(user.getName()).isEqualTo("TEST");
        assertThat(user.getPassword()).isEqualTo("TEST");
    }

    //@Test
    void userChangePassword(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder().build();

        user.changeWith(User.builder()
                .name("TEST")
                .build());

        //user.changePassword("TEST");
        log.error("==="+user.getName());
        log.error("==="+user.getPassword());
        assertThat(user.getName()).isEqualTo("TEST");
        assertThat(user.getPassword()).isEqualTo("TEST");
    }

    @Test
    void test2(){
        userService.registerUser(UserRegistrationData.builder()
                .email("kiheo@gmail.com")
                .password("test123qsd12e")
                .build());
    }
}
