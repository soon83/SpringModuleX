package com.soon83.entities.category;

import com.soon83.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category_item")
@Entity
@Comment("카테고리 아이템")
public class CategoryItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id", nullable = false)
    @Comment("아이디")
    private Long categoryItemId;

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
}
