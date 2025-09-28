package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.model.Profile;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.repository.ProfileRepository;
import com.bbm.fomezero.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public void createProfile(User user, String phoneNumber) {
        Profile profile = new Profile();
        profile.setPhoneNumber(phoneNumber);
        profile.setUser(user);
        profileRepository.save(profile);
    }
}
