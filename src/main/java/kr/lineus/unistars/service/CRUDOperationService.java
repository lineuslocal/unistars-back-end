package kr.lineus.unistars.service;

import java.util.List;

public interface CRUDOperationService<DTO> {
	
    List<DTO> findAll();

    DTO findOneById(String id);

    DTO create(DTO dto);

    DTO update(String id, DTO dto);

    void delete(String id);

    void deleteAll();
    
    long count();
	

}
