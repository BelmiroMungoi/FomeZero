package com.bbm.fomezero.service;

import com.bbm.fomezero.model.Profile;
import com.bbm.fomezero.model.User;

public interface ProfileService {

    Profile createProfile(User user, String phoneNumber);
}
