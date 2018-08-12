package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.EcheancierPrevisionnel;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface EcheancierPrevisionnelJpaRepository.
 */
public interface EcheancierPrevisionnelJpaRepository extends JpaRepository<EcheancierPrevisionnel, Long> {
	List<EcheancierPrevisionnel> getByTypeCredilog(TypeCredilog typeCredilog);
}
