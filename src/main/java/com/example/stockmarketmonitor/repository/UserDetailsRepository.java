package com.example.stockmarketmonitor.repository;

import com.example.stockmarketmonitor.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<CustomUser, Long> {

    @Query("select c from CustomUser c where c.email = :email")
    Optional<CustomUser> findByEmail(@Param("email") String email);
}
