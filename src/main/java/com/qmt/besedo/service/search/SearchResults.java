package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.MessageDatabaseObject;

import java.util.List;

/**
 * Represents the results to display to the user handling the pagination.
 *
 * @param results the results to display to the end user (partial results)
 * @param maxResultsCount max nb of results
 * @param totalPages max nb of pages
 * @param currentPage current page being displayed
 */
public record SearchResults(List<MessageDatabaseObject> results, int maxResultsCount, int totalPages, int currentPage) {
}
