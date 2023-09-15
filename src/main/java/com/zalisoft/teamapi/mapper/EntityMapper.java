package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.BaseDto;
import com.zalisoft.teamapi.model.AbstractModel;

import java.util.List;

public interface EntityMapper<D extends BaseDto, E extends AbstractModel> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
