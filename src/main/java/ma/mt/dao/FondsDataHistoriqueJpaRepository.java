package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataHistorique;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface FondsDataHistoriqueJpaRepository.
 */
public interface FondsDataHistoriqueJpaRepository extends JpaRepository<FondsDataHistorique, Long> {
	List<FondsDataHistorique> getByTypeCredilog(TypeCredilog typeCredilog);
}
