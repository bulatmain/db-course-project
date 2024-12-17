package com.bulatmain.conference.application.model.fabric;

/**
 * Fabric pattern class
 * @param <Type> type of building
 * @param <DtoType> dto of building type
 */
public interface Fabric<Type, DtoType> {
    /**
     * Used for first building of object
     * @param dto
     * @return
     */
    Type create(DtoType dto);

    /**
     * Used for restoring object from stored data
     * @param dto
     * @return
     */
    Type restore(DtoType dto);
}
