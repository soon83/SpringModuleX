package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterMember {
    private final MemberDomainRepository memberDomainRepository;

    public RegisterMember(final MemberDomainRepository memberDomainRepository) {
        this.memberDomainRepository = memberDomainRepository;
    }

    @PostMapping("/member-list")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid Request request) {
        MemberDomain member = request.toDomain();
        memberDomainRepository.save(member);
    }

    public record Request(
            @NotBlank(message = "로그인 아이디는 필수값 입니다.")
            String loginId,
            @NotBlank(message = "비밀번호는 필수값 입니다.")
            String password,
            @NotBlank(message = "이름은 필수값 입니다.")
            String name,
            @NotBlank(message = "이메일은 필수값 입니다.")
            String email,
            @NotNull(message = "회원유형은 필수값 입니다.")
            MemberRole role
    ) {
        public MemberDomain toDomain() {
            return new MemberDomain(
                    loginId,
                    password,
                    name,
                    email,
                    role
            );
        }
    }
}
