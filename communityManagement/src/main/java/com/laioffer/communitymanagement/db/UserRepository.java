package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.password = ?2, u.email = ?3, u.phoneNumber = ?4, u.firstName = ?5, u.lastName = ?6 WHERE u.username = ?1")
    void updateUserInfo(String username, String newPassword, String email, String phoneNumber, String firstName, String lastName);


    @Query(value = "SELECT * FROM user WHERE apt_number != '000' ORDER BY apt_number ASC", nativeQuery = true)
    List<User> selectAllResidentsOrdered();

    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    User selectUser(String username);

}

