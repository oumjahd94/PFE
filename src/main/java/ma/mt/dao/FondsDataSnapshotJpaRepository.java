package ma.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataSnapshot;
import ma.mt.entity.TypeCredilog;
/*
 * Hassan
 */
/**
 * The Interface FondsDataSnapshotJpaRepository.
 */
public interface FondsDataSnapshotJpaRepository extends JpaRepository<FondsDataSnapshot, Long> {
	List<FondsDataSnapshot> getByTypeCredilog(TypeCredilog typeCredilog);
}
