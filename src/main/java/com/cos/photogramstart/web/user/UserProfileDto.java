package com.cos.photogramstart.web.user;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {

    private boolean pageOwnerState; // 이페이지가 본인인지 아닌지
    private int imageCount; // 최종 데이터를 만들어가는게 좋다!
    private boolean subscribeState;
    private int subscribeCount;
    private User user;

}
