package com.allin.filmface.member.repository;

import com.allin.filmface.member.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    @Query("SELECT g FROM Guest AS g WHERE g.socialCode LIKE :socialCode")
    Guest findGuestMemberByCode(String socialCode);
}
