package com.parfenov.kindleVocabExport.mapper;

import java.util.List;

public interface BaseDBMapper<E, D>  {
  D toDto(E entity);

  E toEntity(D dto);

  List<D> toDtoList(List<E> entities);
}
