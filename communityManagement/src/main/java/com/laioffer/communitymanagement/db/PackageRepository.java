package com.laioffer.communitymanagement.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.laioffer.communitymanagement.db.entity.Package;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query(value = "SELECT id FROM package WHERE tenant_id = ?1", nativeQuery = true)
    List<Long> findPackageIdByUsername(String username);

    // find all the packages for hoa - fist ordering them by pickedUpDate with null values and then by username (apartmentNumber) in ascending order
    List<Package> findAllByOrderByResident_UsernameAscPickupDateAsc();

    //     find all packages for resident per username - First, orders them by the is_read column (unread first), followed by ordering by the id column in descending order (newest to oldest)
    List<Package> findByResident_UsernameOrderByIsReadAsc(String username);

    @Modifying
    @Query (value = "UPDATE Package aPackage SET aPackage.isRead = :read WHERE aPackage.resident.username = :username")
    void updateIsReadByResident_Username(String username, Boolean read);

    @Query (value = "SELECT COUNT(apackage) FROM Package apackage WHERE apackage.resident.username = :username AND apackage.isRead = FALSE ")
    int getCountByResident_UsernameAndIsReadIsFalse(String username);

    @Modifying
    @Query (value = "UPDATE Package aPackage SET aPackage.pickupDate = :pickedUpDate WHERE aPackage.id = :packageId")
    void updatePickedUpDateByPackageId(Long packageId, LocalDate pickedUpDate);
}