package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findUserModelByIdOrEmailAddressOrPhone(String id, String email, String phone);

    boolean existsUserModelByIdOrEmailAddressOrPhone(String id, String email, String phone);
}
