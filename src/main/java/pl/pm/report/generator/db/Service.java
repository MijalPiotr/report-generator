package pl.pm.report.generator.db;

import java.io.Serializable;
import java.util.List;

public interface Service<T extends Serializable> {
    T find(Long id);
    void remove(T item);
    void persist(T item);
    void persistAll(List<T> item);
    T merge(T item);
}
