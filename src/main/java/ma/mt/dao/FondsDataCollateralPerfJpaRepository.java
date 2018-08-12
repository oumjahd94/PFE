package ma.mt.dao;
/*
 * Hassan
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataCollateralPerformance;
import ma.mt.entity.TypeCredilog;

public interface FondsDataCollateralPerfJpaRepository extends JpaRepository<FondsDataCollateralPerformance, Long> {
	List<FondsDataCollateralPerformance> getByTypeCredilog(TypeCredilog typeCredilog);
}
