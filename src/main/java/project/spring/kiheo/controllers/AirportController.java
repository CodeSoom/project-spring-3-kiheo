package project.spring.kiheo.controllers;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import project.spring.kiheo.application.AirportService;
import project.spring.kiheo.application.UserService;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.dto.SearchFaresData;
import project.spring.kiheo.dto.UserRegistrationData;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/airport")
@CrossOrigin
public class AirportController {
    private final AirportService airportService;

    @Value("${api.servicekey}")
    private String secret;

    private final String URL_AIRPORT_INFO = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getArprtList";
    private final String URL_FARE_INFO = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList";

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    String selectAirports() {
        String result = "";
        try {
            StringBuilder urlBuilder = new StringBuilder(URL_AIRPORT_INFO);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + secret);
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
            String xml = sb.toString();
            JSONObject jObject = XML.toJSONObject(xml);
            JSONArray jArr = jObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
            result = jArr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("fares")
    String selectFares(@RequestBody @Valid SearchFaresData searchFaresData) {
        log.info("==========="+searchFaresData.getStartDt());
        log.info("==========="+searchFaresData.getArriveAirport());
        log.info("==========="+searchFaresData.getDepartureAirport());
        String result = "";
        try {
            StringBuilder urlBuilder = new StringBuilder(URL_FARE_INFO);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + secret);
            //urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("depAirportId","UTF-8") + "=" + URLEncoder.encode(searchFaresData.getDepartureAirport(), "UTF-8")); /*출발공항ID*/
            urlBuilder.append("&" + URLEncoder.encode("arrAirportId","UTF-8") + "=" + URLEncoder.encode(searchFaresData.getArriveAirport(), "UTF-8")); /*도착공항ID*/
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
            String xml = sb.toString();
            JSONObject jObject = XML.toJSONObject(xml);
            log.info("=="+jObject.toString());
            JSONArray jArr = jObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
            result = jArr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /*
    Spring Security 와 RestTemplate 관련 로직 처리 필요
    @PostMapping
    Map<String, Object> selectAirports() {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setReadTimeout(1000*60*5);
            factory.setConnectTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            Map<String, Object> param = new HashMap<>();
            param.put("serviceKey", "s4BjbNeg2AwsuaGQ0sFZIuF/HYGdH/5vOHpgNn2bhnY2RT3ZEwOB0ojQutPAKsXGs2FV86L5i6lhccG/BAhTgw==");
            param.put("numOfRows", "10");
            String URI = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList";
            result = restTemplate.getForObject(URI, HashMap.class, param);

        } catch (RestClientException e) {
            log.error("===callApi Exception=== {}", e.toString());
            e.printStackTrace();
        }
        return result;
    }
    */
}
