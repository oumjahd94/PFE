package ma.mt.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.dao.FondsDataCollateralPerfJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.FondsDataCollateralPerformance;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataCollateralPerfService;
import ma.mt.util.Constants;

/**
 * The Class FondsDataCollateralService.
 */
@Service
public class FondsDataCollateralService implements IFondsDataCollateralPerfService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataHistoriqueService.class);

	/** The fonds data historique jpa repository. */
	@Autowired
	private FondsDataCollateralPerfJpaRepository fondsDataCollateralPerfJpaRepository;
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataHistoriqueService#deleteFondsDataHistorique(java.lang
	 * .Long)
	 */
	@Override
	public boolean deleteFondsDataCollateralPerf(Long idFondsDataCollateralPerf) {
		try {
			fondsDataCollateralPerfJpaRepository.delete(idFondsDataCollateralPerf);
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
	 * @see ma.mt.service.IFondsDataHistoriqueService#listFondsDataHistorique()
	 */
	@Override
	public List<FondsDataCollateralPerformance> listFondsDataCollateralPerformance() {
		try {
			return fondsDataCollateralPerfJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataHistorique items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataHistoriqueService#getByTypeCredilog(ma.mt.entity.
	 * TypeCredilog)
	 */
	@Override
	public List<FondsDataCollateralPerformance> getByTypeCredilog(TypeCredilog typeCredilog) {
		return fondsDataCollateralPerfJpaRepository.getByTypeCredilog(typeCredilog);
	}

	@Override
	public boolean addFondsDataSnapshotCollateralPerf(MultipartFile fondsDataCollateralPerfFile, Date datePublication,
			Date dateExpiration, Long idTypeCredilog) {
		if (fondsDataCollateralPerfFile == null || fondsDataCollateralPerfFile.isEmpty()) {
			LOG.info("Null or empty snapshot file to upload");
			return false;
		}
		try {
			// get fondsDataCollateralPerf file name
			String name = fondsDataCollateralPerfFile.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/collateralPerf");
			File repertoireCollateralPerfo = new File(tmpDirectory + "/" + "fonds/collateralPerf" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireCollateralPerfo.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/collateralPerf");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("File upload path : %s:" + path);
			Files.copy(fondsDataCollateralPerfFile.getInputStream(), path);
			FondsDataCollateralPerformance fondsDataCollateralPerformance = new FondsDataCollateralPerformance();
			fondsDataCollateralPerformance.setDateExpiration(dateExpiration);
			fondsDataCollateralPerformance.setDatePublication(datePublication);
			fondsDataCollateralPerformance.setFicheCollateral(tmpFolder);
			fondsDataCollateralPerformance.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilog));
			fondsDataCollateralPerfJpaRepository.save(fondsDataCollateralPerformance);
			LOG.trace("TypeCredilogService : file uploaded successfully");
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
		return true;
	}

	@Override
	public FondsDataCollateralPerformance addFondsDataSnapshotCollateralPerf(
			FondsDataCollateralPerformance fondsDataCollateralPerf) {
		return fondsDataCollateralPerfJpaRepository.saveAndFlush(fondsDataCollateralPerf);
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
