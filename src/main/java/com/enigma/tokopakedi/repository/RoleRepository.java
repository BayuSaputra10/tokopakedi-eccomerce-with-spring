package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.constant.Erole;
import com.enigma.tokopakedi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role> findByRole(Erole role);
}
