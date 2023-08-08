package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Claim;
import com.example.restaurantepragma.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long> {

    List<Claim> findByClaimStatus(ClaimStatus claimStatus);

}
