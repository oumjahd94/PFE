package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataPricing;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface FondsDataPricingJpaRepository.
 */
public interface FondsDataPricingJpaRepository extends JpaRepository<FondsDataPricing, Long>{
	List<FondsDataPricing> getByTypeCredilog(TypeCredilog typeCredilog);
}
