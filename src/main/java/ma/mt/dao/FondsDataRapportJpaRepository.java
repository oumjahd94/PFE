package ma.mt.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.entity.FondsDataRapport;
import ma.mt.entity.TypeCredilog;

public interface FondsDataRapportJpaRepository  extends JpaRepository<FondsDataRapport, Long>{
	List<FondsDataRapport> getByTypeCredilog(TypeCredilog typeCredilog); 
}
