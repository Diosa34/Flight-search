package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.SignRead;
import com.flightsearch.services.mapping.SignMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SignService {
    final SignRepository signRepository;

    final SignMapper signMapper;

    public SignService(SignRepository signRepository, SignMapper signMapper) {
        this.signRepository = signRepository;
        this.signMapper = signMapper;
    }

    public SignRead confirm(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public SignRead reject(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.REJECTED);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public void delete(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        signRepository.delete(sign);
    }
}
