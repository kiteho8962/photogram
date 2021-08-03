package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistence API ( 자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)
// DB에 데이터를 넣기위한 작업

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 테이블을 생성
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다. oracle-sequence, mysql-autoincrement 이렇게 따라감
	private int id;
	
	@Column(length = 20, unique = true) // unique = 제약조건
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; // 웹 사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한

	// mappedBy 나는 연관관계의 주인이 아니다! 그러므로 테이블에 컬럼을 만들지마!!
	// User를 Select할 때 해당 User id로 등록된 image들을 다 가져와!
	// LAZY ==> User를 Select할 때 해당 User id로 등록된 image들을 다가져오는게 아니라! -> 대신 getImages() 함수가 호출될때 가져와!!
	// EAGER ==> User를 Select할 때 해당 User id로 등록된 image들을 전부 join해서 가져와!!
	// 양방향 매핑!!
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) // Images안에 있는 user를 무시한다
	private List<Image> images;
	
	private LocalDateTime createDate; // 데이터베이스는 항상 언제 들어왔는지가 필요하다.
	
	@PrePersist // DB에 Insert 되기 직전에 실행, 위에 값을 넣어주면 자동으로 들어감
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
