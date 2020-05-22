package com.przeskocz.AdvertEduPortal.model;

/**
 * T DTO model
 * K DAO model
 * Konwerter modeli DTO i DAO
 */
public interface ModelConverter<T,K> {
    K convertToDAO(T dto);
    T convertToDTO(K dao);
}
