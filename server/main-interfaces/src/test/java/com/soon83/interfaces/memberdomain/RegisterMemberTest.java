package com.soon83.interfaces.memberdomain;

import com.soon83.interfaces.common.ApiTest;
import com.soon83.interfaces.common.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterMemberTest extends ApiTest {
    @Autowired
    private MemberDomainRepository memberDomainRepository;

    @Test
    @DisplayName("회원을 등록한다")
    void registerMember() {
        // given
        Scenario.registerMember().request();
        Scenario
                .registerMember().request();

        // then
        assertThat(memberDomainRepository.findAll()).hasSize(1);
    }

}
