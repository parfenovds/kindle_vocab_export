package com.parfenov.kindleVocabExport.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "word")
public class Word {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "word", nullable = false, length = 100)
  private String word;
  @Column(name = "language", nullable = false)
  private String language;
  @ManyToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User user;
  @OneToMany(
      mappedBy = "word",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<WordContext> contexts = new ArrayList<>();

  public Word() {
  }

  public Word(String word, String language, User user) {
    this.word = word;
    this.language = language;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<WordContext> getContexts() {
    return contexts;
  }

  public void setContexts(List<WordContext> contexts) {
    this.contexts = contexts;
  }

  public void addContext(Context context, Timestamp timestamp) {
    boolean alreadyExists = contexts.stream()
        .anyMatch(wordContext -> wordContext.getContext().equals(context));
    if (!alreadyExists) {
      WordContext wordContext = new WordContext(this, context);
      wordContext.setTimestamp(timestamp);
      contexts.add(wordContext);
      context.getWords().add(wordContext);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Word word1)) {
      return false;
    }
    return Objects.equals(word, word1.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }

  @Override
  public String toString() {
    return "Word{" +
           "id=" + id +
           ", word='" + word + '\'' +
           ", language='" + language + '\'' +
           '}';
  }
}
