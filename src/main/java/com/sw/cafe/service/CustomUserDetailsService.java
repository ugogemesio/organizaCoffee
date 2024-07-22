package com.sw.cafe.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.repository.ColaboradorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ColaboradorRepository colaboradorRepository;

    public CustomUserDetailsService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Colaborador colaborador = colaboradorRepository.findByCpf(cpf);
        if (colaborador == null) {
            throw new UsernameNotFoundException("Usuário Não encontrado com CPF " + cpf);
        }
        return new User(
                colaborador.getCpf(),
                colaborador.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority(colaborador.getPermissao()))
        );
    }
}
