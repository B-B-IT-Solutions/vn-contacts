package cz.prm;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.utils.ComponentTestUser;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
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

    protected RestTemplate restTemplate = new RestTemplate();

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

    protected <T> void post(String url, ComponentTestUser user, T body) {
        given().spec(requestSpec(body, user)).expect().log().ifError().when().post(url).then().assertThat().statusCode(OK.value());
    }

    protected <T> void put(String url, ComponentTestUser user, T body) {
        given().spec(requestSpec(body, user)).expect().log().ifError().when().put(url).then().assertThat().statusCode(OK.value());
    }

    protected void delete(String url, ComponentTestUser user) {
        given().spec(requestSpec(user)).expect().log().ifError().when().delete(url).then().assertThat().statusCode(OK.value());
    }

    protected <T> T getOne(String url, ComponentTestUser user, TypeRef<T> type) {
        return given().spec(requestSpec(user)).expect().log().ifError().when().get(url).then().assertThat().statusCode(OK.value()).extract().as(type);
    }

    protected <T> PageDto<T> getPage(String url, ComponentTestUser user, TypeRef<PageDto<T>> type) {
        return given().spec(requestSpec(user)).expect().log().ifError().when().get(url).then().assertThat().statusCode(OK.value()).extract().as(type);
    }

    protected <T> void putExpectNotFound(String url, ComponentTestUser user, T body) {
        putExpectStatus(url, user, body, HttpStatus.NOT_FOUND);
    }

    protected void deleteExpectNotFound(String url, ComponentTestUser user) {
        deleteExpectStatus(url, user, HttpStatus.NOT_FOUND);
    }

    protected void getExpectNotFound(String url, ComponentTestUser user) {
        getExpectStatus(url, user, HttpStatus.NOT_FOUND);
    }

    protected <T> void putExpectStatus(String url, ComponentTestUser user, T body, HttpStatus status) {
        given().spec(requestSpec(body, user)).expect().when().put(url).then().statusCode(status.value());
    }

    protected void deleteExpectStatus(String url, ComponentTestUser user, HttpStatus status) {
        given().spec(requestSpec(user)).expect().when().delete(url).then().statusCode(status.value());
    }

    protected void getExpectStatus(String url, ComponentTestUser user, HttpStatus status) {
        given().spec(requestSpec(user)).expect().when().get(url).then().statusCode(status.value());
    }

    protected <T> RequestSpecification requestSpec(T body, ComponentTestUser user) {
        var accessToken = getAccessToken(user);
        return new RequestSpecBuilder().setAccept(ContentType.JSON).setContentType(ContentType.JSON).addHeader(AUTHORIZATION, accessToken)
            .setBody(body).build();
    }

    protected RequestSpecification requestSpec(ComponentTestUser user) {
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

    protected String toUrlPaginationParams(PaginationDto pg) {
        var sb = new StringBuilder();
        if (nonNull(pg)) {
            if (pg.getPageNumber() > 0) {
                sb.append("pagination.pageNumber=");
                sb.append(pg.getPageNumber());
                sb.append("&");
            }
            if (pg.getPageSize() > 0) {
                sb.append("pagination.pageSize=");
                sb.append(pg.getPageSize());
                sb.append("&");
            }
        }
        return sb.toString();
    }

    protected String toUrlSortParams(String sort) {
        var sb = new StringBuilder();
        if (nonNull(sort)) {
            sb.append("sort=");
            sb.append(sort);
            sb.append("&");
        }
        return sb.toString();
    }
}
