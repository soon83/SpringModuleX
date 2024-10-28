package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    private RegisterMember registerMember;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        memberRepository = new MemberRepository();
        registerMember = new RegisterMember(memberRepository);
    }

    @Test
    @DisplayName("회원을 등록한다")
    void registerMember() {
        // given
        String loginId = "loginId";
        String password = "password";
        String name = "name";
        String email = "email";
        MemberRole role = MemberRole.MEMBER;
        RegisterMember.Request request = new RegisterMember.Request(
                loginId,
                password,
                name,
                email,
                role
        );

        // when
//        registerMember.request(request);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/member-list")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        assertThat(memberRepository.findAll()).hasSize(1);
    }
}
