package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
