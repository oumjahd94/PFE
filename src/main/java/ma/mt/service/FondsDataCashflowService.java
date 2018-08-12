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

import ma.mt.dao.FondsDataCashflowJpaRepository;
import ma.mt.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.dao.ScenariosFondsRAJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.FondsDataCashflow;
import ma.mt.entity.FondsDataPricing;
import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataCashflowService;
import ma.mt.util.Constants;

/**
 * The Class FondsDataCashflowService.
 */
@Service
public class FondsDataCashflowService implements IFondsDataCashflowService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataCashflowService.class);
	@Autowired
	private ScenariosFondsRAJpaRepository ScenariosFondsRAJpaRepository;
	/** The fonds data cashflow jpa repository. */
	@Autowired
	private FondsDataCashflowJpaRepository fondsDataCashflowJpaRepository;
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IFondsDataCashflowService#deleteFondsDataCashflow(java.lang.
	 * Long)
	 */
	@Override
	public boolean deleteFondsDataCashflow(Long idFondsDataCashFlow) {
		try {
			fondsDataCashflowJpaRepository.delete(idFondsDataCashFlow);
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
	 * ma.mt.service.IFondsDataCashflowService#addFondsDataCashFlow(ma.mt.entity.
	 * FondsDataCashflow)
	 */
/*	@Override
	public boolean addFondsDataCashFlow(
			MultipartFile CashflowFondsFile, 
			Date datePublication, Date dateExpiration,
			String scenarioRA, 
			String scenarioDDT,
			Long idTypeCredilog) {  
		
		 if(CashflowFondsFile == null || CashflowFondsFile.isEmpty())
		 {
				LOG.info("Null or empty snapshot file to upload");
				return false;
		 } 
		 
			try {
				// get PricingFonds file name
				String name = CashflowFondsFile.getOriginalFilename();
				// get tmp directory
				String tmpDirectory = Constants.uploadPathFile;
				
				// create a dateString directory to upload file
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
				String dateString = formatter.format(Calendar.getInstance().getTime());
				File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
				File repertoire = new File(tmpDirectory + "/" + "fonds/cashflow");
				File repertoireHistoriqueFonds = new File(tmpDirectory + "/" + "fonds/cashflow" + "/" + dateString);
				
				// create repertoireFonds
				repertoireFonds.mkdirs();
				// create repertoire
				repertoire.mkdirs();
				repertoireHistoriqueFonds.mkdirs();
				
				// repertoire created successfully !
				Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/cashflow");
				String tmpFolder = dateString + File.separator + name;
				Path path = rootLocation.resolve(tmpFolder);
				LOG.info("File upload path : %s:" + path); 
				
				Files.copy(CashflowFondsFile.getInputStream(), path);
				FondsDataCashflow W = new FondsDataCashflow() ; 
				
				W.getScenariosFondsRa().setValueScenarioFondsRa(Double.parseDouble(scenarioRA)); 
				W.getScenarioFondsDdt().setValueScenario(Double.parseDouble(scenarioDDT));
				W.setDateExpiration(dateExpiration); 
				W.setDatePublication(datePublication); 
				W.setFicheCashflowFonds(tmpFolder); 
				W.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilog));
				fondsDataCashflowJpaRepository.save(W);   
				
				LOG.trace("TypeCredilogService : file uploaded successfully");
			} catch (IOException e) {
				LOG.info("TypeCredilogService : Error uploading file");
				return false;
			}
			return true;	
	
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataCashflowService#listFondsDataCashflows()
	 */
	@Override
	public List<FondsDataCashflow> listFondsDataCashflows() {
		try {
			return fondsDataCashflowJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataCashflow items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataCashflowService#getByTypeCredilog(ma.mt.entity.
	 * TypeCredilog)
	 */
	@Override
	public List<FondsDataCashflow> getByTypeCredilog(TypeCredilog typeCredilog) {
		return fondsDataCashflowJpaRepository.getByTypeCredilog(typeCredilog);
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
	public FondsDataCashflow addFondsDataCashflow(FondsDataCashflow fondsDataCashflow) {
		return fondsDataCashflowJpaRepository.save(fondsDataCashflow);
	}

	@Override
	public boolean addNewCashflow(
			
			MultipartFile file, 
			String datePublication, 
			String dateExpiration, 
			String scenariora,
			String scenarioddt) {   
		
		String message;
		Date datePubltion = parseStringDate(datePublication);
		Date dateExp = parseStringDate(dateExpiration);   
		
		System.out.println("Avant ça afficher les données d'origine  ===> \n"); 
		System.out.println("=*=*=*=*="+datePublication+"=*=*=*=*=");
		System.out.println("=*=*=*=*="+dateExpiration+"=*=*=*=*="); 
		
		System.out.println("Et mnt après casting des strings en dates ===>\n ");
		System.out.println("=*=*=*=*="+datePubltion+"=*=*=*=*=");
		System.out.println("=*=*=*=*="+dateExp+"=*=*=*=*=");
		
		try {
			String name = file.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/cashflow");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/cashflow" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/cashflow");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			Files.copy(file.getInputStream(), path);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			FondsDataCashflow fondsDataCashflow = new FondsDataCashflow();
			fondsDataCashflow.setDateExpiration(dateExp);
			fondsDataCashflow.setDatePublication(datePubltion);
			fondsDataCashflow.setFicheCashflowFonds(dateString + File.separator + name);
			TypeCredilog fonds = typeCredilogJpaRepository.findOne(Constants.idFondsSakane);
			ScenariosFondsDDT scenarioDdt = scenariosFondsDDTJpaRepository.findOne(Long.parseLong(scenarioddt));
			ScenariosFondsRA scenarioRa = ScenariosFondsRAJpaRepository.findOne(Long.parseLong(scenariora));
			fondsDataCashflow.setScenarioFondsDdt(scenarioDdt);
			fondsDataCashflow.setScenariosFondsRa(scenarioRa);
			fondsDataCashflow.setTypeCredilog(fonds); 		
			
			 addFondsDataCashflow(fondsDataCashflow); 
			 return true ; 
		} catch (Exception e) {
			message = "FAIL to upload/ Save item cashflow" + file.getOriginalFilename() + "!";
			return false;
		}
	}




}
