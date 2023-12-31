package ra.projectintern.service;


import ra.projectintern.exception.CustomException;

public interface IGenericMapper<T, K, V> {
    T toEntity(K k) throws CustomException;

    V toResponse(T t) ;
}
