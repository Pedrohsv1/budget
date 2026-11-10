package com.pedro.budget.service;

import com.pedro.budget.dto.GlobalSearchResponse;
import com.pedro.budget.dto.RankedSearchItem;
import com.pedro.budget.dto.SearchItem;
import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Card;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.User;
import com.pedro.budget.enums.BankAccountType;
import com.pedro.budget.enums.SearchItemType;
import com.pedro.budget.repository.BankAccountRepository;
import com.pedro.budget.repository.CardRepository;
import com.pedro.budget.repository.PersonRepository;
import com.pedro.budget.repository.Transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PersonRepository personRepository;
    private final CardRepository cardRepository;


    public GlobalSearchResponse GlobalSearch(User user, String q) {
        String query = q.trim().toLowerCase();

        if (query.length() < 2) {
            return new GlobalSearchResponse(List.of());
        }

        List<RankedSearchItem> transactionItens = fromTransaction(transactionRepository.search(user.getId(), query), query);
        List<RankedSearchItem> personItens = fromPerson(personRepository.search(user.getId(), query), query);
        List<RankedSearchItem> cardItens = fromCard(cardRepository.search(user.getId(), query), query);
        List<RankedSearchItem> bankAccountItens = fromBank(bankAccountRepository.search(user.getId(), query, BankAccountType.matchingTypes(query)), query);


        List<RankedSearchItem> items = new ArrayList<>();
        items.addAll(transactionItens);
        items.addAll(personItens);
        items.addAll(cardItens);
        items.addAll(bankAccountItens);

        items.sort(
                Comparator.comparing(RankedSearchItem::score).reversed()
        );

        List<SearchItem> results = items.stream()
                .map(RankedSearchItem::item)
                .limit(20)
                .toList();

        return new GlobalSearchResponse(results);
    }

    private int calculateScore(SearchItemType type, String title, String subtitle, String q) {
        int base = textScore(title, subtitle, q);
        if (base == 0) return 0;

        int weight = switch (type) {
            case TRANSACTION  -> 4;
            case PERSON       -> 3;
            case BANK_ACCOUNT -> 2;
            case CARD         -> 1;
        };

        return base + weight;
    }

    private int textScore(String title, String subtitle, String q) {
        if(title != null && title.toLowerCase().equals(q)) {
            return 100;
        }

        if(title != null && title.toLowerCase().contains(q)) {
            return 50;
        }

        if(subtitle != null && subtitle.toLowerCase().equals(q)) {
            return 30;
        }

        if(subtitle != null && subtitle.toLowerCase().contains(q)) {
            return 20;
        }

        return 0;
    }

    private List<RankedSearchItem> fromTransaction(List<Transaction> search,String q) {
        return search.stream().map(item -> new RankedSearchItem( new SearchItem(
                item.getId(),
                SearchItemType.TRANSACTION,
                item.getDescription(),
                item.getAmount() + " " + item.getDate().toString()
        ), calculateScore(
                SearchItemType.TRANSACTION,
                item.getDescription(),
                item.getAmount() + " " + item.getDate().toString(),
                q)
        )).toList();
    }

    private List<RankedSearchItem> fromPerson(List<Person> search, String q) {
        return search.stream().map(item -> new RankedSearchItem( new SearchItem(
                item.getId(),
                SearchItemType.PERSON,
                item.getName(),
                item.getEmail()
        ), calculateScore(
                SearchItemType.PERSON,
                item.getName(),
                item.getEmail(),
                q)
        )).toList();
    }

    private List<RankedSearchItem> fromCard(List<Card> search, String q) {
        return search.stream().map(item -> new RankedSearchItem( new SearchItem(
                item.getId(),
                SearchItemType.CARD,
                item.getCardNumber(),
                item.getDescription()
        ), calculateScore(
                SearchItemType.CARD,
                item.getCardNumber(),
                item.getDescription(),
                q)
        )).toList();
    }

    private List<RankedSearchItem> fromBank(List<BankAccount> search, String q) {


        return search.stream().map(item -> {
            int scoreDisplayName = calculateScore(
                    SearchItemType.BANK_ACCOUNT,
                    item.getType().getDisplayName(),
                    item.getDescription(),
                    q);
            int scoreName = calculateScore(
                    SearchItemType.BANK_ACCOUNT,
                    item.getType().name(),
                    item.getDescription(),
                    q);

            return new RankedSearchItem( new SearchItem(
                item.getId(),
                SearchItemType.BANK_ACCOUNT,
                item.getType().getDisplayName(),
                item.getDescription()
            ), Math.max(scoreDisplayName, scoreName)
        ); }).toList();
    }
}