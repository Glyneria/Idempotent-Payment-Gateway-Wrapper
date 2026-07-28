package com.glyneria.iwrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.glyneria.iwrapper.model.entities.User;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<User, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM User WHERE a.id = :id")
    Optional<User> findByIdWithLock(UUID id);
}
