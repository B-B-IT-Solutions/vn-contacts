package cz.prm;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import cz.prm.utils.ComponentTestUser;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("componenttest")
@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class ComponentTestBase {

   protected static KeycloakContainer keycloak;
   @Autowired
   protected RestTemplate restTemplate;

   protected JacksonJsonParser jsonParser = new JacksonJsonParser();

   @BeforeAll
   public static void setup() {
      RestAssured.baseURI = "http://localhost/";
      RestAssured.port = 8091;
      keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm.json");
      keycloak.start();

   }

   @DynamicPropertySource
   static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
      registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/prm");
      registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/prm");
   }

   public <T> List<T> getMany(String url, ComponentTestUser user, TypeRef<List<T>> type) {
      var accessToken = getAccessToken(user);
      return given().header(AUTHORIZATION, accessToken).when().get(url).then().log().ifError().assertThat()
          .statusCode(OK.value())
          .extract()
          .as(type);
   }

   protected String getAccessToken(ComponentTestUser user) {
      try {
         var tokenUrl = accessTokenUrl();
         var formData = accessTokenFormData(user);
         var headers = accessTokenHeaders();
         var entity = new HttpEntity<>(formData, headers);
         var response = restTemplate.postForEntity(tokenUrl, entity, String.class).getBody();
         return toBearerToken(response);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private MultiValueMap<String, String> accessTokenFormData(ComponentTestUser user) {
      var formData = new LinkedMultiValueMap<String, String>();
      formData.put("grant_type", singletonList("password"));
      formData.put("client_id", singletonList("prm"));
      formData.put("username", singletonList(user.getUsername()));
      formData.put("password", singletonList(user.getPassword()));
      return formData;
   }

   private HttpHeaders accessTokenHeaders() {
      var headers = new HttpHeaders();
      headers.setContentType(APPLICATION_FORM_URLENCODED);
      return headers;
   }

   private String accessTokenUrl() {
      return format("%s/realms/prm/protocol/openid-connect/token", keycloak.getAuthServerUrl());
   }

   protected String toBearerToken(String tokenResponse) {
      var accessToken = jsonParser.parseMap(tokenResponse).get("access_token").toString();
      return format("Bearer %s", accessToken);
   }

}
