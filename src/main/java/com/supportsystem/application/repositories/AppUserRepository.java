package com.supportsystem.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supportsystem.application.domains.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
