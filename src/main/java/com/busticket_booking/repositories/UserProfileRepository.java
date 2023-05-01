package com.busticket_booking.repositories;


import com.busticket_booking.entities.User;
import com.busticket_booking.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUser(User user);

}
