package com.pass.vault.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pass.vault.entities.VaultPassEntity;

@Repository
public interface VaultPassRepository extends CrudRepository<VaultPassEntity, Long> {

    @Query("FROM VaultPassEntity WHERE encrypt_value=:hash AND id_user=:idUser")
    public VaultPassEntity getByIdUserAndHash(@Param("hash") String hash, @Param("idUser") Long idUser);

    @Query("FROM VaultPassEntity WHERE id_user=?1")
    public List<VaultPassEntity> getPaswords(Long id);
    // @Query(value = "SELECT * FROM vault_pass WHERE id_user=?1", nativeQuery =
    // true)
    // public List<VaultPassEntity> getPaswords(Long id);
}
