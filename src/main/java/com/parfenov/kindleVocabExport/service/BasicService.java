package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.basic.Card;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import com.parfenov.kindleVocabExport.filter.CardFilter;
import java.io.ByteArrayInputStream;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class BasicService {
  private final TempFileService tempFileService;
  private final CardService cardService;
  private final CsvExportService csvExportService;

  public BasicService(TempFileService tempFileService, CardService cardService, CsvExportService csvExportService) {
    this.tempFileService = tempFileService;
    this.cardService = cardService;
    this.csvExportService = csvExportService;
  }

  public ByteArrayInputStream proceed(CardFilter filter, String userKey) {
    tempFileService.uploadDatabaseFile(filter.getFile(), userKey);
    Set<Lookup> preparedRawLookups = cardService.prepareRawLookups(
        filter.getDateFrom(),
        filter.getDateTo(),
        filter.getSourceLanguage(),
        filter.getLimit(),
        filter.getTimestamp(),
        userKey
    );
    Set<Card> translatedCards = cardService.translateCards(preparedRawLookups, filter.getTargetLanguage());
    Set<Card> completedCards = cardService.makeKeyWordsBold(translatedCards);
    return csvExportService.exportToCsv(completedCards);
  }
}
