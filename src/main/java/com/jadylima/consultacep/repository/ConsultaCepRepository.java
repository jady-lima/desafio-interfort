package com.jadylima.consultacep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jadylima.consultacep.entity.ConsultaCep;

public interface ConsultaCepRepository extends JpaRepository<ConsultaCep, Long> {
}
