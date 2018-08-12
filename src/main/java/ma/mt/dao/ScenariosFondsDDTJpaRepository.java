package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface ScenariosFondsDDTJpaRepository.
 */
public interface ScenariosFondsDDTJpaRepository extends JpaRepository<ScenariosFondsDDT, Long>{
	List<ScenariosFondsDDT> getByTypeCredilog(TypeCredilog typeCredilog);
}
