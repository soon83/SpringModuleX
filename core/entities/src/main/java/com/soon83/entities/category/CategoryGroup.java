package com.soon83.entities.category;

import com.soon83.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category_group")
@Entity
@Comment("카테고리 그룹")
public class CategoryGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_group_id", nullable = false)
    @Comment("아이디")
    private Long categoryGroupId;

    @Column(name = "ordering", nullable = false)
    @Comment("순서")
    private Integer ordering;

    @Column(name = "name", nullable = false, length = 31)
    @Comment("이름")
    private String name;

    @Column(name = "description", nullable = false, length = 63)
    @Comment("설명")
    private String description;

    @Column(name = "icon", nullable = false, length = 511)
    @Comment("아이콘")
    private String icon;

    public void update(
            Integer ordering,
            String name,
            String description,
            String icon
    ) {
        this.ordering = ordering;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
}
