package com.bbm.fomezero.mapper;

import com.bbm.fomezero.dto.response.DriverResponseDTO;
import com.bbm.fomezero.dto.response.UserResponseDTO;
import com.bbm.fomezero.model.Driver;
import com.bbm.fomezero.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityConverter<E, D> {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void setup() {
        modelMapper.createTypeMap(Driver.class, DriverResponseDTO.class)
                .addMappings(mapper -> mapper.map(Driver::getUser, DriverResponseDTO::setUser));

        modelMapper.createTypeMap(User.class, UserResponseDTO.class)
                .addMappings(mapper -> mapper.map(User::getProfile, UserResponseDTO::setProfile));
    }

    public D mapEntityToDto(E entity, Class<D> dtoClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(entity, dtoClass);
    }

    public List<D> mapEntityToDtoList(List<E> entities, Class<D> dtoClass) {
        List<D> dtoList = new ArrayList<>();
        for (E entity : entities) {
            dtoList.add(mapEntityToDto(entity, dtoClass));
        }
        return dtoList;
    }

    public E mapDtoToEntity(D dto, Class<E> entityClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, entityClass);
    }
}
