package com.laioffer.communitymanagement.packageAndMessage;

import com.laioffer.communitymanagement.db.PackageRepository;
import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.Package;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {
    private final PackageRepository packageRepository;
    public MessageService (PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> listByUsername(String username) {
        List<Package> notPickedup = packageRepository.findByResident_UsernameAndPickupDateIsNullOrderByReceivedDateDesc(username);
        List<Package> pickedUp = packageRepository.findByResident_UsernameAndPickupDateIsNotNullOrderByPickupDateDescReceivedDateDesc(username);
        List<Package> allPackages = Stream.of(notPickedup, pickedUp)
                .flatMap(Collection::stream)
                .toList();
        return allPackages;
    }

//    @Transactional
//    public void markMessageAsRead(String username) {
//        packageRepository.updateIsReadByResident_Username(username, true);
//    }

    public int checkPackagesNotPickedUp(String username) {
        return packageRepository.getCountByResident_UsernameAndPickupDateIsNull(username);
    }

}
