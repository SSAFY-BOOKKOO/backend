package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quote extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private MemberInfo memberInfo;

    //출처
    @Setter
    @Column(name = "source")
    private String source;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name = "background_img_url")
    private String backgroundImgUrl;

    @Setter
    @Column(name = "font_name")
    private String fontName;

    @Setter
    @Column(name = "font_color")
    private String fontColor;

    @Setter
    @Column(name = "font_size")
    private Integer fontSize;

    @Builder
    public Quote(Long id, MemberInfo memberInfo, String source, String content,
        String backgroundImgUrl,
        String fontName, String fontColor, Integer fontSize) {
        this.id = id;
        this.memberInfo = memberInfo;
        this.source = source;
        this.content = content;
        this.backgroundImgUrl = backgroundImgUrl;
        this.fontName = fontName;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
    }
}
