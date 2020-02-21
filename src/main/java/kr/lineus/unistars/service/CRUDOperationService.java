package kr.lineus.unistars.service;

import java.util.List;

public interface CRUDOperationService<En, DTO> {
	
    List<En> findAll();

    En findOneById(String id);

    En save(DTO obj);

    void delete(String id);

    void deleteAll();
    
    long count();
	

}
