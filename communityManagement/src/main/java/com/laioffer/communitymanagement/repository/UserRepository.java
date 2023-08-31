package com.laioffer.communitymanagement.repository;

import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
