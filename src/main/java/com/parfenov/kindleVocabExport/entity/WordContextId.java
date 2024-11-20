package com.parfenov.kindleVocabExport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WordContextId implements Serializable {
  @Column(name = "word_id")
  private Long wordId;
  @Column(name = "context_id")
  private Long contextId;

  public WordContextId() {
  }

  public WordContextId(Long wordId, Long contextId) {
    this.wordId = wordId;
    this.contextId = contextId;
  }

  public Long getWordId() {
    return wordId;
  }

  public void setWordId(Long wordId) {
    this.wordId = wordId;
  }

  public Long getContextId() {
    return contextId;
  }

  public void setContextId(Long contextId) {
    this.contextId = contextId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WordContextId that)) {
      return false;
    }
    return Objects.equals(wordId, that.wordId) && Objects.equals(contextId, that.contextId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wordId, contextId);
  }
}
