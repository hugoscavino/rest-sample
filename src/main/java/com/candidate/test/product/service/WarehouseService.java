package com.candidate.test.product.service;

import com.candidate.test.product.dto.WarehouseDTO;
import com.candidate.test.product.entity.WarehouseEntity;
import com.candidate.test.product.entity.builder.WarehouseEntityBuilder;
import com.candidate.test.product.repository.WarehouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository repository;

    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    private final static Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    public Long save(WarehouseDTO dto){

        WarehouseEntity entity = new WarehouseEntityBuilder().name(dto.getName()).zipCode(dto.getZipCode()).build();
        logger.info("Attempting to save " + dto);

        WarehouseEntity saved = repository.save(entity);
        Long id = saved.getId();
        logger.info("Saved with ID " + id);
        return id;
    }

    public Optional<WarehouseDTO> find(Long id) {
        Optional<WarehouseEntity> optionalWarehouse = repository.findById(id);
        Optional<WarehouseDTO> result = Optional.empty();

        if (optionalWarehouse.isPresent()){
            WarehouseEntity found = optionalWarehouse.get();
            result = Optional.of(new WarehouseDTO(found.getId(), found.getName(), found.getZipCode()));
        }

        return result;
    }

    /**
     * Return the ID if deleted. Will throw excecption otherwise
     * @param id PK to delete
     * @return PK of row deleted
     */
    public Long delete(Long id) {
        WarehouseEntity entity = new WarehouseEntityBuilder().id(id).build();
        repository.delete(entity);
        return id;
    }
}
