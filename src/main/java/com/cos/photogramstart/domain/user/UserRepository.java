package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 상속을 받아서 어노테이션이 없어도 IoC에 등록이 자동으로 됨
// User = 오브젝트, Integer = primary 키 type
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA Query method (JPQL)
	User findByUsername(String username);
	
}
