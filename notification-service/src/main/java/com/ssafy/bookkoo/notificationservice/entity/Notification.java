package com.ssafy.bookkoo.notificationservice.entity;

import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import com.ssafy.bookkoo.notificationservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Notification extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "dtype", insertable = false, updatable = false)
    private NotificationType dtype;

    public Notification(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }
}
