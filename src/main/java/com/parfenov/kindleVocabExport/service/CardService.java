package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.Card;
import com.parfenov.kindleVocabExport.entity.Lookup;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final LookupService lookupService;
  private final TranslationService translationService;

  public CardService(LookupService lookupService, TranslationService translationService) {
    this.lookupService = lookupService;
    this.translationService = translationService;
  }

  public Set<Lookup> prepareRawLookups(String dateFrom, String dateTo, String sourceLanguage, Integer limit, String timestamp, String userKey) {
    return lookupService.getFiltered(userKey, dateFrom, dateTo, sourceLanguage, limit, timestamp);
  }

  public Set<Card> translateCards(Set<Lookup> rawLookups, String targetLanguage) {
    return rawLookups.stream()
        .map(lookup -> {
          Card card = mapLookupToCard(lookup);
          return translationService.translate(card, targetLanguage);
        })
        .collect(Collectors.toSet());
  }

  private Card mapLookupToCard(Lookup lookup) {
    Card card = new Card();
    card.setOriginalSentence(lookup.getUsage());
    card.setWords(Set.of(lookup.getWordKey().split(":")[1]));
    card.setSourceLanguage(lookup.getWordKey().split(":")[0]);
    return card;
  }
}