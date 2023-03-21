package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.service.search.SearchResults;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class H2Dao implements MessageDao {
    private final MessageRepository messageRepository;
    private final FindMethodsDictionary findMethodsDictionary;

    public H2Dao(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        findMethodsDictionary = new FindMethodsDictionary(messageRepository);
    }

    @Override
    public Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject entity) {
        return Try.of(() -> {
            messageRepository.saveAndFlush(entity);
            return entity;
        });
    }

    @Override
    public Try<SearchResults> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value, Pageable pageable) {
        return Try.of(() -> {
            var findMethodAndCount = findMethodsDictionary.getMatchingFindMethod(attributeName, operator);
            int maxCount = findMethodAndCount.countMethod.apply(value);
            var results = findMethodAndCount.findMethod.apply(value, pageable);
            int pagesCount = Math.ceilDivExact(maxCount, pageable.getPageSize());
            return new SearchResults(results, maxCount, pagesCount, pageable.getPageNumber());
        });
    }

    @Override

    public Try<List<MessageDatabaseObject>> getObjects() {
        return Try.of(messageRepository::findAll);
    }


    /**
     * Hold all find methods. From a given {@link MessageAttributeName} and a {@link SearchOperator} you will get
     * the matching find/count method to run against the database.
     */
    private static final class FindMethodsDictionary {

        private final MessageRepository messageRepository;
        private final Map<Tuple2<MessageAttributeName, SearchOperator>, FindAndCountMethod> findMethods = new HashMap<>();

        private FindMethodsDictionary(MessageRepository messageRepository) {
            this.messageRepository = messageRepository;
            initDictionary();
        }

        private void initDictionary() {
            findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.EQUALS), new FindAndCountMethod(messageRepository::findById, messageRepository::countById));
            findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.STARTS_WITH), new FindAndCountMethod(messageRepository::findByIdStartsWith, messageRepository::countByIdStartsWith));
            findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.CONTAINS), new FindAndCountMethod(messageRepository::findByIdContains, messageRepository::countByIdContains));
            findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.EQUALS), new FindAndCountMethod(messageRepository::findByEmail, messageRepository::countByEmail));
            findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.STARTS_WITH), new FindAndCountMethod(messageRepository::findByEmailStartsWith, messageRepository::countByEmailStartsWith));
            findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.CONTAINS), new FindAndCountMethod(messageRepository::findByEmailContains, messageRepository::countByEmailContains));
            findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.EQUALS), new FindAndCountMethod(messageRepository::findByTitle, messageRepository::countByTitle));
            findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.STARTS_WITH), new FindAndCountMethod(messageRepository::findByTitleStartsWith, messageRepository::countByTitleStartsWith));
            findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.CONTAINS), new FindAndCountMethod(messageRepository::findByTitleContains, messageRepository::countByTitleContains));
            findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.EQUALS), new FindAndCountMethod(messageRepository::findByBody, messageRepository::countByBody));
            findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.STARTS_WITH), new FindAndCountMethod(messageRepository::findByBodyStartsWith, messageRepository::countByBodyStartsWith));
            findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.CONTAINS), new FindAndCountMethod(messageRepository::findByBodyContains, messageRepository::countByBodyContains));
        }

        /**
         * Wrap the find method with its matching count method
         *
         * @param findMethod  the function to find object by an attribute
         * @param countMethod the function to count objects by an attribute
         */
        private record FindAndCountMethod(Function2<String, Pageable, List<MessageDatabaseObject>> findMethod,
                                          Function<String, Integer> countMethod) {

        }

        /**
         * @param messageAttributeName the {@link MessageAttributeName}
         * @param searchOperator       the {@link SearchOperator}
         * @return the matching findMethod to run against the database.
         */
        FindAndCountMethod getMatchingFindMethod(MessageAttributeName messageAttributeName, SearchOperator searchOperator) {
            return findMethods.get(Tuple.of(messageAttributeName, searchOperator));
        }
    }


}
