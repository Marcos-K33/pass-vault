package com.pass.vault.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pass.vault.config.JwtTokenUtil;
import com.pass.vault.entities.VaultPassEntity;
import com.pass.vault.repositories.UserRepository;
import com.pass.vault.repositories.VaultPassRepository;
import com.pass.vault.requests.VaultPassRequest;
import com.pass.vault.utils.SecurePass;

@Service
public class VaultPassService {

    @Autowired
    JwtTokenUtil jUtil;

    @Autowired
    UserRepository uRepository;

    @Autowired
    VaultPassRepository vPRepository;

    public boolean savePassword(VaultPassRequest request, String token) {
        try {
            token = token.replace("Bearer ", "");
            String email = jUtil.getSubjectFromtoken(token).split(",")[1];
            Integer idUser = uRepository.getIdUserByEmail(email);
            String encryptPassword = SecurePass.encrypt(request.getPassword(), request.getSecret());

            VaultPassEntity entity = new VaultPassEntity(encryptPassword, request.getAccount(), request.getPlatform(),
                    new Date(), new Date(), idUser.longValue());
            vPRepository.save(entity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
