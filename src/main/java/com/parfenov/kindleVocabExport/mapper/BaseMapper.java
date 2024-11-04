package com.parfenov.kindleVocabExport.mapper;


public interface BaseMapper<S, T> {
  T mapFrom(S source);
}