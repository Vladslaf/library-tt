package dev.home.library.service.mapper;

public interface ResponseDtoMapper<D, M> {
    D mapToDto(M m);
}
