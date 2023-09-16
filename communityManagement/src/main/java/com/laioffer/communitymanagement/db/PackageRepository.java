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

    List<Package> findByPickupDateIsNullOrderByReceivedDateDescResident_UsernameAsc();

    List<Package> findByPickupDateIsNotNullOrderByPickupDateDescReceivedDateDescResident_UsernameAsc();

    List<Package> findByResident_UsernameAndPickupDateIsNullOrderByReceivedDateDesc(String username);

    List<Package> findByResident_UsernameAndPickupDateIsNotNullOrderByPickupDateDescReceivedDateDesc(String username);

//    @Modifying
//    @Query (value = "UPDATE Package aPackage SET aPackage.isRead = :read WHERE aPackage.resident.username = :username")
//    void updateIsReadByResident_Username(String username, Boolean read);

    @Query (value = "SELECT COUNT(apackage) FROM Package apackage WHERE apackage.resident.username = :username AND apackage.pickupDate IS NULL")
    int getCountByResident_UsernameAndPickupDateIsNull(String username);

    @Modifying
    @Query (value = "UPDATE Package aPackage SET aPackage.pickupDate = :pickedUpDate WHERE aPackage.id = :packageId")
    void updatePickedUpDateByPackageId(Long packageId, LocalDate pickedUpDate);
}