package com.soon83.interfaces.memberdomain.api;

import com.soon83.dtos.enums.MemberRole;
import com.soon83.interfaces.main.member.MemberRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterMemberApi {
    private String loginId = "loginId";
    private String password = "password";
    private String name = "name";
    private String email = "email";
    private MemberRole role = MemberRole.MEMBER;

    public RegisterMemberApi loginId(final String loginId) {
        this.loginId = loginId;
        return this;
    }

    public RegisterMemberApi password(final String password) {
        this.password = password;
        return this;
    }

    public RegisterMemberApi name(final String name) {
        this.name = name;
        return this;
    }

    public RegisterMemberApi email(final String email) {
        this.email = email;
        return this;
    }

    public RegisterMemberApi role(final MemberRole role) {
        this.role = role;
        return this;
    }

    public void request() {
        MemberRegisterRequest request = new MemberRegisterRequest(
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
                .post("/api/member-list")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}