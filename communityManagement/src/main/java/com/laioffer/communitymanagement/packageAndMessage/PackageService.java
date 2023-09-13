package com.laioffer.communitymanagement.packageAndMessage;

import com.laioffer.communitymanagement.db.PackageRepository;
import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.db.entity.Package;
import com.laioffer.communitymanagement.model.PackageBody;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

@Service
public class PackageService {
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;

    public PackageService(PackageRepository packageRepository, UserRepository userRepository) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
    }

    public List<Package> listPackagesByHOA() {
        return packageRepository.findAllByOrderByResident_UsernameAscPickupDateAsc();
    }

    @Transactional
    public void addPackage(PackageBody aPackage) throws ResidentNotFoundException {
        User resident = userRepository.findByUsername(aPackage.username());
        if (resident == null) {
            throw new ResidentNotFoundException("Resident is not found.");
        }
        Package thisPackage = new Package()
                .setDescription(aPackage.description())
                .setResident(resident).setReceivedDate(LocalDate.now());
        packageRepository.save(thisPackage);
    }

    @Transactional
    public void markPackageAsPickedUp(Long packageId) throws PackageNotExistException, PackageAlreadyPickedUpException {
        Optional<Package> aPackage = packageRepository.findById(packageId);
        if (aPackage.equals(Optional.empty())) {
            throw new PackageNotExistException("Package doesn't exist.");
        }
        if (aPackage.get().getPickupDate() != null) {
            throw new PackageAlreadyPickedUpException("Package is already picked up.");
        }
        packageRepository.updatePickedUpDateByPackageId(packageId, LocalDate.now());
    }
}
