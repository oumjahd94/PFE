package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface ScenariosFondsRAJpaRepository.
 */
public interface ScenariosFondsRAJpaRepository extends JpaRepository<ScenariosFondsRA, Long>{  
	
	List<ScenariosFondsRA> getByTypeCredilog(TypeCredilog typeCredilog); 
	
}
