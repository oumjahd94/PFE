package ma.mt.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IScenariosFondsDDTService;

/**
 * The Class ScenariosFondsDDTService.
 */
@Service
public class ScenariosFondsDDTService implements IScenariosFondsDDTService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ScenariosFondsDDTService.class);

	/** The scenarios fonds DDT jpa repository. */
	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IScenariosFondsDDTService#deleteScenariosFondsDDT(java.lang.
	 * Long)
	 */
	@Override
	public boolean deleteScenariosFondsDDT(Long idScenariosFondsDDT) {
		try {
			scenariosFondsDDTJpaRepository.delete(idScenariosFondsDDT);
			LOG.trace("Element has deleted successfully!");
			return true;
		} catch (Exception e) {
			ScenariosFondsDDT scenarioFondsDdt = scenariosFondsDDTJpaRepository.findOne(idScenariosFondsDDT);
			scenarioFondsDdt.setFlagDeleted(true);
			scenariosFondsDDTJpaRepository.saveAndFlush(scenarioFondsDdt);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IScenariosFondsDDTService#addScenariosFondsDDT(ma.mt.entity.
	 * ScenariosFondsDDT)
	 */
	@Override
	public boolean addScenariosFondsDDT(ScenariosFondsDDT scenariosFondsDDT) {
		try {
			scenariosFondsDDTJpaRepository.save(scenariosFondsDDT);
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't created successfully !");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsDDTService#listScenariosFondsDDT()
	 */
	@Override
	public List<ScenariosFondsDDT> listScenariosFondsDDT() {
		try {
			return scenariosFondsDDTJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listScenariosFondsDDTJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.interfaces.IScenariosFondsDDTService#getByTypeCredilog(ma.mt.
	 * entity.TypeCredilog)
	 */
	@Override
	public List<ScenariosFondsDDT> getByTypeCredilog(TypeCredilog typeCredilog) {
		return scenariosFondsDDTJpaRepository.getByTypeCredilog(typeCredilog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.interfaces.IScenariosFondsDDTService#findScenarioDdtById(java.
	 * lang.Long)
	 */
	@Override
	public ScenariosFondsDDT findScenarioDdtById(Long idScenarioDdt) {
		return scenariosFondsDDTJpaRepository.findOne(idScenarioDdt);
	}

	/**
	 * Find scenario fonds DDT.
	 *
	 * @param idScenarioFondsDdt
	 *            the id scenario fonds ddt
	 * @return the scenarios fonds DDT
	 */
	public ScenariosFondsDDT findScenarioFondsDDT(Long idScenarioFondsDdt) {
		return scenariosFondsDDTJpaRepository.findOne(idScenarioFondsDdt);
	}

	/* (non-Javadoc)
	 * @see ma.mt.service.interfaces.IScenariosFondsDDTService#parseStringDate(java.lang.String)
	 */
	@Override
	public Date parseStringDate(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see ma.mt.service.interfaces.IScenariosFondsDDTService#addScenarioFondsDdt(ma.mt.entity.ScenariosFondsDDT)
	 */
	@Override
	public boolean addScenarioFondsDdt(ScenariosFondsDDT scenariosFondsDDT) {
		try {
			scenariosFondsDDTJpaRepository.save(scenariosFondsDDT);
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't created successfully !");
			return false;
		}
	}
}
