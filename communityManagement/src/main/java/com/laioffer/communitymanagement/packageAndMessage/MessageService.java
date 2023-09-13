package com.laioffer.communitymanagement.packageAndMessage;

import com.laioffer.communitymanagement.db.PackageRepository;
import com.laioffer.communitymanagement.db.entity.Package;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {
    private final PackageRepository packageRepository;
    public MessageService (PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> listByUsername(String username) {
        return packageRepository.findByResident_UsernameOrderByIsReadAsc(username);
    }

    @Transactional
    public void markMessageAsRead(String username) {
        packageRepository.updateIsReadByResident_Username(username, true);
    }

    public int checkAllUnreadMessages(String username) {
        return packageRepository.getCountByResident_UsernameAndIsReadIsFalse(username);
    }

}
