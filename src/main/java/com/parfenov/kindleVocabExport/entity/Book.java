package com.parfenov.kindleVocabExport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "source_db_id")
  private String sourceDbId;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "changed_title")
  private String changedTitle;
  @Column(name = "language")
  private String language;
  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private Author author;
  @OneToMany(mappedBy = "book")
  List<Context> contexts;
}
