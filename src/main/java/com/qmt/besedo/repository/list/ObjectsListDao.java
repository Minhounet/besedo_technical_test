package com.qmt.besedo.repository.list;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.service.search.SearchResults;
import io.vavr.control.Try;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * Basic database where objects are stored in {@link CopyOnWriteArrayList} to handle concurrency.
 */
public class ObjectsListDao implements MessageDao {

    private final List<MessageDatabaseObject> objects = new CopyOnWriteArrayList<>();

    @Override
    public Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject entity) {
        return Try.of(() -> {
            objects.add(entity);
            return entity;
        });
    }

    @Override
    public Try<SearchResults> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value, Pageable pageable) {
        return Try.of(() -> {
            Predicate<MessageDatabaseObject> doesValueMatch = messageDatabaseObject -> {
                String fieldValue = attributeName.getMessageDatabaseObjectAttributeFn().apply(messageDatabaseObject);
                return switch (operator) {
                    case CONTAINS -> fieldValue.contains(value);
                    case STARTS_WITH -> fieldValue.startsWith(value);
                    case EQUALS -> fieldValue.equals(value);
                };
            };
            List<MessageDatabaseObject> messageDatabaseObjects = objects.stream()
                    .filter(doesValueMatch)
                    .toList();

            int maxCount = messageDatabaseObjects.size();
            int pageNumber = pageable.getPageNumber();
            int pageSize = pageable.getPageSize();
            int pagesCount = Math.ceilDivExact(maxCount, pageSize);

            List<MessageDatabaseObject> partialResults = paginateList(messageDatabaseObjects, maxCount, pageNumber, pageSize);
            return new SearchResults(partialResults, maxCount, pagesCount, pageNumber);
        });
    }

    private List<MessageDatabaseObject> paginateList(List<MessageDatabaseObject> objects, int maxCount, int pageNumber, int pageSize) {
        if (objects.isEmpty()) {
            return List.of();
        } else {
            var fromIndex = pageNumber * pageSize;
            // Case when pageIndex is out of range
            if (fromIndex >= maxCount) {
                return paginateList(objects, maxCount, pageNumber -1, pageSize);
            } else {
                return objects.subList(fromIndex, Math.min(fromIndex + pageSize, maxCount));
            }
        }
    }

    @Override
    public Try<List<MessageDatabaseObject>> getObjects() {
        return Try.of(() -> objects);
    }

}
