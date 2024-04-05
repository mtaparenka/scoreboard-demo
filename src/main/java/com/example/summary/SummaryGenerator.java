package com.example.summary;

/**
 * Generic generator interface which allows to generate a summary.
 * @param <T>
 */
public interface SummaryGenerator<T> {
    /**
     *
     * @return Generated summary
     */
    T generateSummary();
}
