package com.saxakiil.beautyspacebackend.service;

import com.saxakiil.beautyspacebackend.model.AccountProfile;
import com.saxakiil.beautyspacebackend.repository.AccountProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountProfileService {
    private final AccountProfileRepository accountProfileRepository;

    @Transactional
    public void save(final AccountProfile accountProfile) {
        accountProfileRepository.save(accountProfile);
    }

    @Transactional
    public Optional<AccountProfile> findById(final Long id) {
        return accountProfileRepository.findById(id);
    }
}
