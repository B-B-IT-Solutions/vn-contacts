package cz.prm;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import cz.prm.utils.ComponentTestUser;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@DirtiesContext
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
      startKeycloak();
   }

   public static void startKeycloak() {
      if (isNull(keycloak)) {
         keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm.json");
         keycloak.start();
      }
   }

   @DynamicPropertySource
   static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
      registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/prm");
      registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/prm");
   }

   protected <T> T getOne(String url, ComponentTestUser user, TypeRef<T> type) {
      return given().spec(getRequestSpec(user)).when().get(url).then().log().ifError().assertThat()
          .statusCode(OK.value())
          .extract()
          .as(type);
   }

   protected <T> List<T> getMany(String url, ComponentTestUser user, TypeRef<List<T>> type) {
      return given().spec(getRequestSpec(user)).when().get(url).then().log().ifError().assertThat()
          .statusCode(OK.value())
          .extract()
          .as(type);
   }

   protected void getExpectNotFount(String url, ComponentTestUser user) {
      getExpectStatus(url, user, HttpStatus.NOT_FOUND);
   }

   protected void getExpectStatus(String url, ComponentTestUser user, HttpStatus status) {
      given().spec(getRequestSpec(user)).expect().when().get(url).then().statusCode(status.value());
   }

   protected RequestSpecification getRequestSpec(ComponentTestUser user) {
      var accessToken = getAccessToken(user);
      return new RequestSpecBuilder().setAccept(ContentType.JSON).setContentType(ContentType.JSON).addHeader(AUTHORIZATION, accessToken).build();
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
      formData.put("client_id", singletonList("componenttest"));
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
