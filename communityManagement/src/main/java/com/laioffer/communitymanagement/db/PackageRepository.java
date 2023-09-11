package com.laioffer.communitymanagement.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.laioffer.communitymanagement.db.entity.Package;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query(value = "SELECT id FROM package WHERE tenant_id = ?1", nativeQuery = true)
    List<Long> findPackageIdByUsername(String username);
}