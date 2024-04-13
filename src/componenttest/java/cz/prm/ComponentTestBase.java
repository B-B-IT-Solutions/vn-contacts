package cz.prm;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("componenttest")
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class ComponentTestBase {

   @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
   private String authServerUrl;

   @Autowired
   protected RestTemplate restTemplate;

   @Container
   static KeycloakContainer keycloak = new KeycloakContainer(.withRealmImportFile("classpath:utils/keycloak/realm.json");

   @BeforeAll
   public static void setup() throws Exception {
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

         var headers = new HttpHeaders();
         headers.setContentType(APPLICATION_FORM_URLENCODED);

         var formData = new LinkedMultiValueMap<String, String>();
         formData.put("grant_type", singletonList("password"));
         formData.put("client_id", singletonList("prm"));
         formData.put("username", singletonList("bobuskysergej"));
         formData.put("password", singletonList("password123"));

         var entity = new HttpEntity<>(formData, headers);
         var result = restTemplate.postForEntity(authServerTokenURL, entity, String.class).getBody();
         var jsonParser = new JacksonJsonParser();
         return "Bearer " + jsonParser.parseMap(result).get("access_token").toString();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

}
