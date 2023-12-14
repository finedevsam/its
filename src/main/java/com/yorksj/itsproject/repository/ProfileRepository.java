package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    /* Create Queries using Data JPA*/

    Profile findUserProfileByUserId(String userId);
}
