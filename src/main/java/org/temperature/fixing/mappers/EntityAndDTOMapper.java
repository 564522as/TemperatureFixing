package org.temperature.fixing.mappers;

public interface SimpleMapper<D, T> {
    D toEntity(T t);
    T toDTO(D d);
}
