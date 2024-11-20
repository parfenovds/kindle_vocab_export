package com.parfenov.kindleVocabExport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "WordContext")
@Table(name = "word_context")
public class WordContext {
  @EmbeddedId
  private WordContextId id;
  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("wordId")
  private Word word;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("contextId")
  private Context context;

  public WordContext() {
  }
  public WordContext(Word word, Context context) {
    this.word = word;
    this.context = context;
    this.id = new WordContextId(word.getId(), id.getContextId());
  }

  public WordContextId getId() {
    return id;
  }

  public void setId(WordContextId id) {
    this.id = id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Word getWord() {
    return word;
  }

  public void setWord(Word word) {
    this.word = word;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WordContext that)) {
      return false;
    }
    return Objects.equals(word, that.word) && Objects.equals(context, that.context);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word, context);
  }
}
