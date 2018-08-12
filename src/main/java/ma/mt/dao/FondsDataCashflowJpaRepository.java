package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataCashflow;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface FondsDataCashflowJpaRepository.
 */
public interface FondsDataCashflowJpaRepository extends JpaRepository<FondsDataCashflow, Long>{
	List<FondsDataCashflow> getByTypeCredilog(TypeCredilog typeCredilog);
}
