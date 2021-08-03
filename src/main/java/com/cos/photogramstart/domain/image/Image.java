package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
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
public class Image { // N, 1
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String caption;
    private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버에 특정 폴더에 저장 -> DB에 그 저장된 경로를 insert

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER) // 이미지를 select하면 조인해서 User정보를 같이 들고옴
//    @ManyToOne
    private User user; // 1, 1

    // 이미지 좋아요?

    // 이미지에 댓글?

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    // 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력 되지 않게 함!!
    /*@Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", postImageUrl='" + postImageUrl +
                ", createDate=" + createDate +
                '}';
    }*/
}
