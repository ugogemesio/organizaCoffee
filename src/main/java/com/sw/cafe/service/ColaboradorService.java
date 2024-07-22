package com.sw.cafe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.repository.ColaboradorRepository;

@Service
public class ColaboradorService {

    private static final Logger logger = LoggerFactory.getLogger(ColaboradorService.class);

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void criarColaborador(String nome, String cpf, String senha) {
        logger.info("Service: Criando colaborador: Nome = {}, CPF = {}", nome, cpf);
        if (cpf.length() != 11 || colaboradorRepository.findByCpf(cpf) != null) {
            throw new IllegalArgumentException("CPF inválido ou já existente");
        }

        Colaborador colaborador = new Colaborador();
        colaborador.setNome(nome);
        colaborador.setCpf(cpf);
        colaborador.setSenha(passwordEncoder.encode(senha));
        colaborador.setPermissao("ROLE_USER");
        colaboradorRepository.save(colaborador);
    }

    public Colaborador achaPorCpf(String cpf) {
        logger.info("Service: Procurando colaborador por CPF = {}", cpf);
        return colaboradorRepository.findByCpf(cpf);
    }

    public Colaborador achaPorId(Long id) {
        logger.info("Service: Procurando colaborador por ID = {}", id);
        return colaboradorRepository.findById(id).orElse(null);
    }

    public void atualizarColaborador(Colaborador colaborador) {
        logger.info("Service: Atualizando colaborador: Nome = {}, CPF = {}", colaborador.getNome(), colaborador.getCpf());
        colaboradorRepository.save(colaborador);
    }

    public void deletarColaborador(Long id) {
        logger.info("Service: Deletando colaborador com ID = {}", id);
        colaboradorRepository.deleteById(id);
    }
}
