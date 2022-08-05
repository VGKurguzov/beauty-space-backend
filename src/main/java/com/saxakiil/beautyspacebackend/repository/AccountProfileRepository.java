package com.saxakiil.beautyspacebackend.repository;

import com.saxakiil.beautyspacebackend.model.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
}
