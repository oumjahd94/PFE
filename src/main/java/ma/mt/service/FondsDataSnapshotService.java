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

import ma.mt.dao.FondsDataSnapshotJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.FondsDataSnapshot;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataSnapshotService;
import ma.mt.util.Constants;

/**
 * The Class FondsDataSnapshotService.
 */
@Service
public class FondsDataSnapshotService implements IFondsDataSnapshotService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataSnapshotService.class);

	/** The fonds data snapshot jpa repository. */
	@Autowired
	private FondsDataSnapshotJpaRepository fondsDataSnapshotJpaRepository;

	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataSnapshotService#deleteFondsDataSnapshot(java.lang.
	 * Long)
	 */
	@Override
	public boolean deleteFondsDataSnapshot(Long idScenariosFondsDDT) {
		try {
			fondsDataSnapshotJpaRepository.delete(idScenariosFondsDDT);
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
	 * @see
	 * ma.mt.service.IFondsDataSnapshotService#addFondsDataSnapshot(ma.mt.entity.
	 * FondsDataSnapshot)
	 */
	@Override
	public boolean addFondsDataSnapshot(MultipartFile snapshotFile, Date datePublication, Date dateExpiration,
			Date arrete, Long idTypeCredilog) { 
		
		if (snapshotFile == null || snapshotFile.isEmpty()) {
			LOG.info("Null or empty snapshot file to upload");
			return false;
		}   
		
		try {
			// get snapshot file name
			String name = snapshotFile.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/snapshot");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/snapshot" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/snapshot");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("File upload path : %s:" + path);
			Files.copy(snapshotFile.getInputStream(), path);  
			
			FondsDataSnapshot fondsDataSnapshot = new FondsDataSnapshot();
			
			fondsDataSnapshot.setDateArrete(arrete);
			fondsDataSnapshot.setDateExpiration(dateExpiration);
			fondsDataSnapshot.setDatePublication(datePublication);
			fondsDataSnapshot.setFicheSnapshot(tmpFolder);
			fondsDataSnapshot.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilog));
			fondsDataSnapshot.setFicheSnapshot(dateString + File.separator + name);
			
			fondsDataSnapshotJpaRepository.save(fondsDataSnapshot);
			LOG.trace("TypeCredilogService : file uploaded successfully");
		
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataSnapshotService#listFondsDataSnapshot()
	 */
	@Override
	public List<FondsDataSnapshot> listFondsDataSnapshot() {
		try {
			return fondsDataSnapshotJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataSnapshotJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataSnapshotService#getByTypeCredilog(ma.mt.entity.
	 * TypeCredilog)
	 */
	@Override
	public List<FondsDataSnapshot> getByTypeCredilog(TypeCredilog typeCredilog) {
		return fondsDataSnapshotJpaRepository.getByTypeCredilog(typeCredilog);
	}
	
	@Override
	public Date parseStringDate(String stringDate) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			return df.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public FondsDataSnapshot addFondsDataSnapshot(FondsDataSnapshot fondsDataSnapshot) {
		return fondsDataSnapshotJpaRepository.save(fondsDataSnapshot);
	}

}
