package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterMemberTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MemberDomainRepository memberDomainRepository;

    @BeforeEach
    void setUp() {
        if (RestAssured.UNDEFINED_PORT == RestAssured.port) {
            RestAssured.port = port;
        }
    }

    @Test
    @DisplayName("회원을 등록한다")
    void registerMember() {
        // given
        final String loginId = "loginId";
        final String password = "password";
        final String name = "name";
        final String email = "email";
        final MemberRole role = MemberRole.MEMBER;
        final RegisterMember.Request request = new RegisterMember.Request(
                loginId,
                password,
                name,
                email,
                role
        );

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/member-list")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        assertThat(memberDomainRepository.findAll()).hasSize(1);
    }
}
