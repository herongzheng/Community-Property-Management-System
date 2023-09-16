package com.laioffer.communitymanagement.packageAndMessage;

import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.db.entity.Package;
import com.laioffer.communitymanagement.model.PackageBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PackageController {
    private final PackageService packageService;
    private final UserRepository userRepository;
    public PackageController(PackageService packageService, UserRepository userRepository) {
        this.packageService = packageService;
        this.userRepository = userRepository;
    }

    // Use case: For hoa, return a list of packages/messages from ALL residents per ascending order of apartment number. Within each apartment number, list unpicked up package messages before pickedup package messages.
    @GetMapping(value = "/packages")
    public List<Package> listPackages() {
        return packageService.listPackagesByHOA();
    }

    // user case: After HOA receiving packages from delivery man, create a package per resident last_name, apt_number and description.
    // check if last_name matches the resident info in user repo. if no, throw resident not found exception; if yes, save package in repo.
    @PostMapping("/packages/create")
    public void createPackage (@RequestBody PackageBody aPackage) throws ResidentNotFoundException {
        packageService.addPackage(aPackage);
    }

    // Use case: When a resident picks up the package, update the table in database to change the pickUpDate from null to today's date.
    @PostMapping ("/packages/pickup/{packageId}")
    public void markPackageAsPickedUp(@PathVariable Long packageId) throws PackageNotExistException, PackageAlreadyPickedUpException {
        packageService.markPackageAsPickedUp(packageId);
    }
}
