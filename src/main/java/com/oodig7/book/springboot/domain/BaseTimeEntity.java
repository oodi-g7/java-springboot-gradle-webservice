package com.oodig7.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
                                                // Auditing 기능 = Spring Data JPA에서 생성,수정시간을 자동으로 입력해주는 기능. 자동으로 시간 매핑 -> DB에 넣어줌.
public abstract class BaseTimeEntity {

    @CreatedDate // Entity가 생성되어 저장될때 시간이 자동 저장됨.
    private LocalDateTime createDate;

    @LastModifiedDate // 조회한 Entity의 값을 변경할때 시간이 자동 저장됨.
    private LocalDateTime modifiedDate;
}
