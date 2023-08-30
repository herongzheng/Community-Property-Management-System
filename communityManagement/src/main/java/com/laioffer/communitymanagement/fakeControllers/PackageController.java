package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.Package;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/*
* Controllers in this class are only used by the HOA
* */
@RestController
public class PackageController {

    public PackageController() {
    }

    /*
    * Display all packages newest to lowest, here we limit them to 20, so that I will not take long
    * */
    @GetMapping("/packages")
    public List<Package> displayPackages() {
        Package pack1 = new Package().setDescription("TBA00123456").setRead(true).setPickupDate(LocalDate.now());
        Package pack2 = new Package().setDescription("contains lithium batteries").setRead(true)
                .setPickupDate(LocalDate.of(2023, Month.AUGUST, 30));
        Package pack3 = new Package().setDescription("A heavy box that may need two people to carry").setRead(false);
        return List.of(pack1, pack2, pack3);
    }

    /*
    * This API will create a new record in the package table and this record is marked as unread
    * */
    @PostMapping("/packages/create")
    @ResponseStatus(HttpStatus.OK)
    public void createANewPackage(@RequestBody Package pack) {
//        use the package repository to create a new package record
//        Once the package record is created, the frontend should display all the packages including this new package
    }

    /*
    * when the resident picks up the package, the HOA employee will click on the button "picked up"
    * and then in UI this package will be marked as "picked up";
    * from the backend
    */
    @PostMapping("/packages/pickup/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public void markPickup(@PathVariable long packageId) {
        // given the packageId, backend can update the corresponding record in the package table
        // update pickupDate to be LocalDate.now()
    }

}
