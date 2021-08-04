package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"} // 실제 데이터베이스 테이블 컬럼명 // 두개의 컬럼을 unique조건 줄때
                )
        }
)
public class Likes { // N, 1 // N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 무한 참조됨
    @JoinColumn(name = "imageId")
    @ManyToOne // 기본 EAGER 전략
    private Image image; // 1, 1

    // 오류가 터지고나서 잡아봅시당~
    @JsonIgnoreProperties("images")
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // 1,

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


}
