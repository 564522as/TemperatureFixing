package org.temperature.fixing.services;

import java.util.Optional;

public interface Service<T> {
    Optional<T> findByName(String name);
    void save()
}
