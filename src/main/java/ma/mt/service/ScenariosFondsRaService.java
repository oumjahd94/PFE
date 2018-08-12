package ma.mt.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.dao.ScenariosFondsRAJpaRepository;
import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IScenariosFondsRaService;

/**
 * The Class ScenariosFondsRaService.
 */
@Service
public class ScenariosFondsRaService implements IScenariosFondsRaService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ScenariosFondsRaService.class);

	/** The scenarios fonds RA jpa repository. */
	@Autowired
	private ScenariosFondsRAJpaRepository scenariosFondsRAJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IScenariosFondsRaService#deleteScenarioFondsRa(java.lang.Long)
	 */
	@Override
	public boolean deleteScenarioFondsRa(Long idScenarioFondsRa) {
		try {
			scenariosFondsRAJpaRepository.delete(idScenarioFondsRa);
			LOG.trace("Element has deleted successfully!");
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't deleted successfully !" + e.getMessage());
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsRaService#addScenarioFondsRa(ma.mt.entity.
	 * ScenariosFondsRA)
	 */
	@Override
	public boolean addScenarioFondsRa(ScenariosFondsRA scenariosFondsRA) {
		try {
			scenariosFondsRAJpaRepository.save(scenariosFondsRA);
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't created successfully !");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsRaService#listScenarioFondsRa()
	 */
	@Override
	public List<ScenariosFondsRA> listScenarioFondsRa() {
		try {
			return scenariosFondsRAJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listScenariosFondsRAJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsRaService#getByTypeCredilog(ma.mt.entity.
	 * TypeCredilog)
	 */
	@Override
	public List<ScenariosFondsRA> getByTypeCredilog(TypeCredilog typeCredilog) {
		return scenariosFondsRAJpaRepository.getByTypeCredilog(typeCredilog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.interfaces.IScenariosFondsRaService#deleteScenariosFondsRA(java
	 * .lang.Long)
	 */
	@Override
	public boolean deleteScenariosFondsRA(Long idScenariosFondsRA) {
		try {
			scenariosFondsRAJpaRepository.delete(idScenariosFondsRA);
			LOG.trace("Element has deleted successfully!");
			return true;
		} catch (Exception e) {
			ScenariosFondsRA scenarioFondsRa = scenariosFondsRAJpaRepository.findOne(idScenariosFondsRA);
			scenarioFondsRa.setFlagDeleted(true);
			scenariosFondsRAJpaRepository.saveAndFlush(scenarioFondsRa);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.interfaces.IScenariosFondsRaService#findScenarioRaById(java.
	 * lang.Long)
	 */
	@Override
	public ScenariosFondsRA findScenarioRaById(Long idScenarioRa) {
		return scenariosFondsRAJpaRepository.findOne(idScenarioRa);
	}

	/**
	 * Find scenario fonds ra.
	 *
	 * @param idScenarioFondsRa
	 *            the id scenario fonds ra
	 * @return the scenarios fonds RA
	 */
	public ScenariosFondsRA findScenarioFondsRa(Long idScenarioFondsRa) {
		return scenariosFondsRAJpaRepository.findOne(idScenarioFondsRa);
	}

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
}
