package com.bbm.fomezero.service;

import com.bbm.fomezero.model.Profile;
import com.bbm.fomezero.model.User;

public interface ProfileService {

    void createProfile(User user, String phoneNumber, String avatarUrl);

    Profile getProfileByUser(User user);

    void updateProfile(User user, String phoneNumber, String avatarUrl);
}
