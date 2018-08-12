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

import ma.mt.dao.FondsDataPricingJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.FondsDataPricing;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataPricingService;
import ma.mt.util.Constants;

/**
 * The Class FondsDataPricingService.
 */
@Service
public class FondsDataPricingService implements IFondsDataPricingService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataPricingService.class);

	/** The fonds data pricing jpa repository. */
	@Autowired
	private FondsDataPricingJpaRepository fondsDataPricingJpaRepository;
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataPricingService#deleteFondsDataPricing(java.lang.Long)
	 */
	@Override
	public boolean deleteFondsDataPricing(Long idFondsDataPricing) {
		try {
			fondsDataPricingJpaRepository.delete(idFondsDataPricing);
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
	 * @see ma.mt.service.IFondsDataPricingService#addFondsDataPricing(ma.mt.entity.
	 * FondsDataPricing)
	 */
	@Override
	public boolean addFondsDataPricing(FondsDataPricing fondsDataPricing) {
		try {
			fondsDataPricingJpaRepository.save(fondsDataPricing);
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't created successfully !");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataPricingService#listFondsDataPricing()
	 */
	@Override
	public List<FondsDataPricing> listFondsDataPricing() {
		try {
			return fondsDataPricingJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataPricingJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataPricingService#getByTypeCredilog(ma.mt.entity.
	 * TypeCredilog)
	 */
	@Override
	public List<FondsDataPricing> getByTypeCredilog(TypeCredilog typeCredilog) {
		return fondsDataPricingJpaRepository.getByTypeCredilog(typeCredilog);
	}

	@Override
	public boolean addPricingFonds(MultipartFile PricingFondsFile, Date datePublication, Date dateExpiration,
			Long idTypeCredilog) { 
		
		if (PricingFondsFile == null || PricingFondsFile.isEmpty()) {
			LOG.info("Null or empty snapshot file to upload");
			return false;
		} 
		
		try {
			// get PricingFonds file name
			String name = PricingFondsFile.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/pricing");
			File repertoireHistoriqueFonds = new File(tmpDirectory + "/" + "fonds/pricing" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireHistoriqueFonds.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/pricing");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("File upload path : %s:" + path);
			Files.copy(PricingFondsFile.getInputStream(), path);
			FondsDataPricing fondsDataPricing = new FondsDataPricing();
			fondsDataPricing.setDateExpiration(dateExpiration);
			fondsDataPricing.setDatePublication(datePublication);
			fondsDataPricing.setFichePricing(tmpFolder);
			fondsDataPricing.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilog));
			fondsDataPricingJpaRepository.save(fondsDataPricing);
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

}
