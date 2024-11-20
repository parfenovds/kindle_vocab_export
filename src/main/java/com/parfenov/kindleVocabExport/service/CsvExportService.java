package com.parfenov.kindleVocabExport.service;

import com.opencsv.CSVWriter;
import com.parfenov.kindleVocabExport.entity.basic.Card;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CsvExportService {

  public ByteArrayInputStream exportToCsv(Set<Card> cards) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {
      writer.writeNext(new String[]{"Translated Sentence", "Original Sentence"});
      for (Card card : cards) {
        writer.writeNext(new String[]{card.getTranslatedSentence(), card.getOriginalSentence()});
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ByteArrayInputStream(out.toByteArray());
  }
}
