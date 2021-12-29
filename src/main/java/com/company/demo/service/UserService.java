package com.company.demo.service;

import com.company.demo.entity.User;
import com.company.demo.model.request.ChangePasswordReq;
import com.company.demo.model.request.CreateUserReq;
import com.company.demo.model.request.UpdateProfileReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(CreateUserReq req);

    void changePassword(User user, ChangePasswordReq req);

    User updateProfile(User user, UpdateProfileReq req);
}
