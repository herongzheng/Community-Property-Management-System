package com.laioffer.communitymanagement.db.repository;

import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
