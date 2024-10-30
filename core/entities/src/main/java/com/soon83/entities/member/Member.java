package com.soon83.entities.member;

import com.soon83.dtos.enums.MemberRole;
import com.soon83.entities.BaseEntity;
import com.soon83.utils.AssertUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member",
        indexes = {
                @Index(name = "ix_loginId", columnList = "login_id"),
                @Index(name = "ix_name", columnList = "name"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uix_loginId", columnNames = {"login_id"})}
)
@Entity
@Comment("회원")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    @Comment("아이디")
    private Long memberId;

    @Column(name = "login_id", nullable = false, length = 63)
    @Comment("로그인 아이디")
    private String loginId;

    @Column(name = "password", nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(name = "name", nullable = false, length = 31)
    @Comment("이름")
    private String name;

    @Column(name = "email", nullable = false, length = 31)
    @Comment("이메일")
    private String email;

    @Column(name = "role", nullable = false, length = 31)
    @Enumerated(EnumType.STRING)
    @Comment("역할")
    private MemberRole role;

    public void update(
            String loginId,
            String password,
            String name,
            String email,
            MemberRole role
    ) {
        AssertUtil.notNull(loginId, "loginId");
        AssertUtil.notNull(password, "password");
        AssertUtil.notNull(name, "name");
        AssertUtil.notNull(email, "email");
        AssertUtil.notNull(role, "role");

        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}