package cz.prm;

import static io.restassured.RestAssured.given;
import static java.net.URI.create;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@ActiveProfiles("componenttest")
@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class ComponentTestBase {

   @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
   private String authServerUrl;

   @BeforeEach
   public void setup() {
      RestAssured.baseURI = "http://localhost/";
      RestAssured.port = 8091;
   }

   public <T> List<T> getMany(String url, TypeRef<List<T>> type) {
      var accessToken = getAccessToken();
      return given().header(AUTHORIZATION, accessToken).when().get(url).then().log().ifError().assertThat()
          .statusCode(OK.value())
          .extract()
          .as(type);
   }

   protected String getAccessToken() {
      try {
         var authServerTokenURL = authServerUrl + "/protocol/openid-connect/token";
         var authorizationURI = create(authServerTokenURL);
         var webclient = WebClient.builder().build();
         var formData = new LinkedMultiValueMap<String, String>();
         formData.put("grant_type", singletonList("password"));
         formData.put("client_id", singletonList("prm"));
         formData.put("username", singletonList("bobuskysergej"));
         formData.put("password", singletonList("password123"));

         var result = webclient.post()
             .uri(authorizationURI)
             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
             .body(BodyInserters.fromFormData(formData))
             .retrieve()
             .bodyToMono(String.class)
             .block();

         JacksonJsonParser jsonParser = new JacksonJsonParser();

         return "Bearer " + jsonParser.parseMap(result)
             .get("access_token")
             .toString();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

}
