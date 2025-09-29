package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.exception.ResourceNotFoundException;
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
    public void createProfile(User user, String phoneNumber, String avatarUrl) {
        Profile profile = new Profile();
        profile.setPhoneNumber(phoneNumber);
        profile.setAvatarUrl(avatarUrl);
        profile.setUser(user);
        profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Profile not found."));
    }

    @Override
    @Transactional
    public void updateProfile(User user, String phoneNumber, String avatarUrl) {
        var profile = getProfileByUser(user);
        profile.setPhoneNumber(phoneNumber);
        profile.setAvatarUrl(avatarUrl);
        profileRepository.save(profile);
    }
}
