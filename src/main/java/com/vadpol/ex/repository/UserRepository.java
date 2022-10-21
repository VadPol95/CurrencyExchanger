package com.vadpol.ex.repository;

import com.vadpol.ex.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneNumber(String phone);
}
