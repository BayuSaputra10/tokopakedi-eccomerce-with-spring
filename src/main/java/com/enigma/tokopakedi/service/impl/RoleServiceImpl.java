package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constant.Erole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.repository.RoleRepository;
import com.enigma.tokopakedi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(Erole eRole) {
        Optional<Role> optionalRole = roleRepository.findByRole(eRole);
        if (optionalRole.isPresent()) return optionalRole.get();

        Role role = Role.builder()
                .role(eRole)
                .build();
        return roleRepository.save(role);
    }
}
