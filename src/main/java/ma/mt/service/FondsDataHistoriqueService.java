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

import ma.mt.dao.FondsDataHistoriqueJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.FondsDataHistorique;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataHistoriqueService;
import ma.mt.util.Constants;

/**
 * The Class FondsDataHistoriqueService.
 */
@Service
public class FondsDataHistoriqueService implements IFondsDataHistoriqueService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataHistoriqueService.class);

	/** The fonds data historique jpa repository. */
	@Autowired
	private FondsDataHistoriqueJpaRepository fondsDataHistoriqueJpaRepository;

	/** The Type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository TypeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataHistoriqueService#deleteFondsDataHistorique(java.lang
	 * .Long)
	 */
	@Override
	public boolean deleteFondsDataHistorique(Long idFondsDataHistorique) {
		try {
			fondsDataHistoriqueJpaRepository.delete(idFondsDataHistorique);
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
	public List<FondsDataHistorique> listFondsDataHistorique() {
		try {
			return fondsDataHistoriqueJpaRepository.findAll();
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
	public List<FondsDataHistorique> getByTypeCredilog(TypeCredilog typeCredilog) {
		return fondsDataHistoriqueJpaRepository.getByTypeCredilog(typeCredilog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.interfaces.IFondsDataHistoriqueService#addFondsHistorique(org.
	 * springframework.web.multipart.MultipartFile, java.util.Date, java.util.Date,
	 * java.lang.Long)
	 */
	@Override
	public boolean addFondsHistorique(MultipartFile fondsDataHistoriqueFile, Date datePublication, Date dateExpiration,
			Long idTypeCredilog) {
		if (fondsDataHistoriqueFile == null || fondsDataHistoriqueFile.isEmpty()) {
			LOG.info("Null or empty snapshot file to upload");
			return false;
		}
		try {
			// get fondsDataHistorique file name
			String name = fondsDataHistoriqueFile.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/historique");
			File repertoireHistoriqueFonds = new File(tmpDirectory + "/" + "fonds/historique" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();   
			// create repertoire
			repertoire.mkdirs();
			repertoireHistoriqueFonds.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/historique");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("File upload path : %s:" + path);
			Files.copy(fondsDataHistoriqueFile.getInputStream(), path);
			FondsDataHistorique fondsDataHistorique = new FondsDataHistorique();
			fondsDataHistorique.setDateExpiration(dateExpiration);
			fondsDataHistorique.setDatePublication(datePublication);
			fondsDataHistorique.setFicheHistorique(tmpFolder);
			fondsDataHistorique.setTypeCredilog(TypeCredilogJpaRepository.findOne(idTypeCredilog));
			fondsDataHistoriqueJpaRepository.save(fondsDataHistorique);
			LOG.trace("TypeCredilogService : file uploaded successfully");
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
		return true;
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

	@Override
	public FondsDataHistorique addFondsDataHistorique(FondsDataHistorique fondsDataHistorique) {
		return fondsDataHistoriqueJpaRepository.save(fondsDataHistorique);
	}

}
