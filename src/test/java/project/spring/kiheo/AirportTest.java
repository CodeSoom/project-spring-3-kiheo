package project.spring.kiheo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import project.spring.kiheo.application.AirportService;
import project.spring.kiheo.application.UserService;
import project.spring.kiheo.domain.AirportRepository;
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

@Slf4j
//@WebMvcTest
@SpringBootTest
//@DataJpaTest
public class AirportTest {
    private AirportService airportService;

    @Autowired
    private AirportRepository airportRepository;
    //private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp(){
        log.info("========setup==========");
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        airportService = new AirportService(mapper, airportRepository);
    }

    @Test
    void test2(){
        airportService.registerAirports();
    }

    @Test
    void encodeTest2(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        try {
            factory.setReadTimeout(1000*60*5);
            factory.setConnectTimeout(5000);
            RestTemplate rt = new RestTemplate(factory);
            Map<String, String> param = new HashMap<>();

            param.put("serviceKey", "s4BjbNeg2AwsuaGQ0sFZIuF/HYGdH/5vOHpgNn2bhnY2RT3ZEwOB0ojQutPAKsXGs2FV86L5i6lhccG/BAhTgw==");
            param.put("numOfRows", "10");
            //String URI = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList";
            String URI = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getArprtList";
            Object result = rt.getForObject(URI, Object.class, param);

            System.out.println(result.toString());
        } catch (RestClientException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    void encodeTest222(){
        //passwordEncoder
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getArprtList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=s4BjbNeg2AwsuaGQ0sFZIuF/HYGdH/5vOHpgNn2bhnY2RT3ZEwOB0ojQutPAKsXGs2FV86L5i6lhccG/BAhTgw=="); /*Service Key*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            //System.out.println(sb.toString());
            String xml = sb.toString();
            JSONObject jObject = XML.toJSONObject(xml);
            JSONArray jArr = jObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
            JSONArray arr = new JSONArray();

            for(Object jObj : jArr){
                System.out.println(jObj);
                System.out.println((JSONObject)jObj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void encodeTest(){
        //passwordEncoder
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=s4BjbNeg2AwsuaGQ0sFZIuF/HYGdH/5vOHpgNn2bhnY2RT3ZEwOB0ojQutPAKsXGs2FV86L5i6lhccG/BAhTgw=="); /*Service Key*/
            //urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("depAirportId","UTF-8") + "=" + URLEncoder.encode("NAARKJJ", "UTF-8")); /*출발공항ID*/
            urlBuilder.append("&" + URLEncoder.encode("arrAirportId","UTF-8") + "=" + URLEncoder.encode("NAARKPC", "UTF-8")); /*도착공항ID*/
            urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode("20201201", "UTF-8")); /*출발일*/
            //urlBuilder.append("&" + URLEncoder.encode("airlineId","UTF-8") + "=" + URLEncoder.encode("AAR", "UTF-8")); /*항공사ID*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            //System.out.println(sb.toString());
            String xml = sb.toString();
            JSONObject jObject = XML.toJSONObject(xml);
            System.out.println(jObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
