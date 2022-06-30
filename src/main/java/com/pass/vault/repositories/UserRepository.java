package com.pass.vault.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pass.vault.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("from UserEntity where email=?1")
    public Optional<UserEntity> findByEmail(String email);

}
