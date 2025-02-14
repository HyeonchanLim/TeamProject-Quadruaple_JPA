package com.green.project_quadruaple.trip;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.project_quadruaple.trip.model.Weather;
import com.green.project_quadruaple.trip.model.dto.WeatherDto;
import com.green.project_quadruaple.trip.model.dto.WeatherItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherApiCall {

    private final String API_KEY;
    private final String BASE_URL;

    public final int TO_GRID = 0;
    public final int TO_GPS = 1;

    public WeatherApiCall(@Value("${weather-api-const.api-key}") String API_KEY,
                          @Value("${weather-api-const.base-url}") String BASE_URL) {
        this.API_KEY = API_KEY;
        this.BASE_URL = BASE_URL;
    }

    public String call(WebClient webClient, ObjectMapper objectMapper, String nx, String ny) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowFormat = now.format(formatter);
        try {
            LatXLngY latXLngY = convertGRID_GPS(TO_GRID, Double.parseDouble(nx), Double.parseDouble(ny));
            String strX = String.valueOf((int)latXLngY.x);
            String strY = String.valueOf((int)latXLngY.y);
            StringBuilder urlBuilder = new StringBuilder(BASE_URL + "/getVilageFcst"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + API_KEY); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo",StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8)); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows",StandardCharsets.UTF_8) + "=" + URLEncoder.encode("15", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType",StandardCharsets.UTF_8) + "=" + URLEncoder.encode("JSON", StandardCharsets.UTF_8)); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date",StandardCharsets.UTF_8) + "=" + URLEncoder.encode(nowFormat, StandardCharsets.UTF_8));
            urlBuilder.append("&" + URLEncoder.encode("base_time",StandardCharsets.UTF_8) + "=" + URLEncoder.encode("0500", StandardCharsets.UTF_8)); /*05시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx",StandardCharsets.UTF_8) + "=" + URLEncoder.encode(strX, StandardCharsets.UTF_8)); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny",StandardCharsets.UTF_8) + "=" + URLEncoder.encode(strY, StandardCharsets.UTF_8)); /*예보지점의 Y 좌표값*/
            URL url = new URL(urlBuilder.toString());
            String json = httpPostRequestReturnJson(webClient, url);
            WeatherDto weatherList = getWeatherList(json, objectMapper);
//            System.out.println(weatherList);
            for (WeatherItem item : weatherList.getItem()) {
                if(item.getCategory().equals("SKY")) {
                    return Weather.getNameByValue(Integer.parseInt(item.getFcstValue()));
                } else if(item.getCategory().equals("PTY") && !item.getFcstValue().equals("0")) {
                    return Weather.getNameByValue(Integer.parseInt(item.getFcstValue())+10);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String httpPostRequestReturnJson(WebClient webClient, URL url) throws URISyntaxException {
        return webClient.get()
                .uri(url.toURI())
                .retrieve() //통신 시도
                .bodyToMono(String.class) // 결과물을 String변환
                .block(); //비동기 > 동기
    }

    private WeatherDto getWeatherList(String json, ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            WeatherDto weatherDto =  objectMapper.convertValue(jsonNode.at("/response/body/items")
                    , new TypeReference<>() {});
            return weatherDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기준점 Y좌표(GRID)

        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = (Math.floor(ra * Math.sin(theta) + XO + 0.5));
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;
            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }

        return rs;
    }

    private static class LatXLngY {
        public double lat;
        public double lng;

        public double x;
        public double y;
    }
}
