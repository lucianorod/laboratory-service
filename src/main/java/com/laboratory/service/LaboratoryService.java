package com.laboratory.service;

import com.laboratory.dto.LaboratoryDto;
import com.laboratory.exception.BadRequestException;
import com.laboratory.exception.NotFoundException;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    private final ModelMapper modelMapper;

    public Laboratory find(Long laboratoryId) {
        log.info("M=find, finding laboratory with id {}", laboratoryId);
        return laboratoryRepository.findById(laboratoryId).orElseThrow(NotFoundException::new);
    }

    public Page<Laboratory> find(Pageable pageable) {
        return laboratoryRepository.findAll(pageable);
    }

    @Transactional
    public Laboratory save(LaboratoryDto laboratoryDto) {

        final Laboratory laboratory = modelMapper.map(laboratoryDto, Laboratory.class);
        log.info("M=save, saving laboratory={}", laboratory);
        return laboratoryRepository.save(laboratory);
    }

    public void delete(Long laboratoryId) {
        log.info("M=delete, deleting laboratory with id {}", laboratoryId);
        laboratoryRepository.deleteById(laboratoryId);
    }

    public Laboratory update(Long laboratoryId, LaboratoryDto laboratoryDto) {
        final Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(BadRequestException::new);

        final Laboratory updatedLab = modelMapper.map(laboratoryDto, Laboratory.class);
        updatedLab.setId(laboratory.getId());

        log.info("M=delete, updating laboratory {}", updatedLab);
        return laboratoryRepository.save(updatedLab);
    }
}
