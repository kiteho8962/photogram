package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying // INSERT, DELETE, UPDATE를 네이티브 쿠러리로 작성하려면 어노테이션 필요!!
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) Values(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId); // 성공 1(변경된 행의 갯수만큼 return), 실패 -1

    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId); // 성공 1, 실패 -1

    // 구독 여부 여기서 1이라는건 구독 된상태
    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId=:principalId AND toUserId=:pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    // 구독자 수 여기서 1이라는건 구독한 구독자수
    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId=:pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);

    // Subscribe 형태로 리턴받기때문에 이건 여기서 사용할수가 없다.
//    @Query(value = "SELECT u.id, u.username, u.profileImageUrl,\n" +
//            "    if((SELECT 1 FROM subscribe WHERE fromUserId = 1 AND toUserId = u.id), 1, 0) subscribeState,\n" +
//            "    if((1=u.id), 1, 0) equalUserState\n" +
//            "    FROM user u INNER JOIN subscribe s\n" +
//            "    ON u.id = s.toUserId\n" +
//            "    WHERE s.fromUserId = 2")

}
