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

import ma.mt.dao.EcheancierPrevisionnelJpaRepository;
import ma.mt.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.dao.ScenariosFondsRAJpaRepository;
import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.EcheancierPrevisionnel;
import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IEcheancierPrevisionnelService;
import ma.mt.util.Constants;

/**
 * The Class EcheancierPrevisionnelService.
 */
@Service
public class EcheancierPrevisionnelService implements IEcheancierPrevisionnelService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(EcheancierPrevisionnelService.class);

	/** The echeancier previsionnel jpa repository. */
	@Autowired
	private EcheancierPrevisionnelJpaRepository echeancierPrevisionnelJpaRepository;

	/** The scenarios fonds DDT jpa repository. */
	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/** The scenarios fonds RA jpa repository. */
	@Autowired
	private ScenariosFondsRAJpaRepository scenariosFondsRAJpaRepository;

	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IEcheancierPrevisionnelService#deleteEcheancierPrevisionnel(
	 * java.lang.Long)
	 */
	@Override
	public boolean deleteEcheancierPrevisionnel(Long idEcheancierPrevisionnel) {
		try {
			echeancierPrevisionnelJpaRepository.delete(idEcheancierPrevisionnel);
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
	 * ma.mt.service.IEcheancierPrevisionnelService#addEcheancierPrevisionnel(ma.mt.
	 * entity.EcheancierPrevisionnel)
	 */
	@Override
	public boolean addEcheancierPrevisionnel(EcheancierPrevisionnel echeancierPrevisionnel) {
		try {
			echeancierPrevisionnelJpaRepository.save(echeancierPrevisionnel);
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't created successfully !");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IEcheancierPrevisionnelService#listEcheancierPrevisionnel()
	 */
	@Override
	public List<EcheancierPrevisionnel> listEcheancierPrevisionnel() {
		try {
			return echeancierPrevisionnelJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listEcheancierPrevisionnelJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IEcheancierPrevisionnelService#createNewEcheancierPrevisionnel(
	 * org.springframework.web.multipart.MultipartFile,
	 * org.springframework.web.multipart.MultipartFile,
	 * org.springframework.web.multipart.MultipartFile, java.util.Date,
	 * java.util.Date, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean createNewEcheancierPrevisionnel( 
			
			MultipartFile partP1,
			MultipartFile partP2,
			MultipartFile partS,		
			Date datePublication, 
			Date dateExpiration, 
			Long scenariosFondsDDTs,
			Long scenariosFondsRa,
			Long idTypeCredilogType) {  
		
		// partP1 is null or empty
		if (partP1 == null || partP1.isEmpty()) {
			LOG.info("Null or empty partp1 file to upload");
			return false;
		} 
		
		// partP2 is null or empty
		if (partP2 == null || partP2.isEmpty()) {
			LOG.info("Null or empty partp2 file to upload");
			return false;
		} 
		
		// partS is null or empty
		if (partS == null || partS.isEmpty()) {
			LOG.info("Null or empty partS file to upload");
			return false;
		}   
		
		try { 
			
			System.out.println("d'abord est ce  que j'arrive à cde niveau");
			// get file name of partP1
			String partP1Name = partP1.getOriginalFilename();
			// get file name of partP2
			String partP2Name = partP2.getOriginalFilename();
			// get file name of partS
			String partSName = partS.getOriginalFilename();
			// get tmp directory  
			
			
			System.out.println("nous allons mnt afficher les noms des fichiers \n  part A ===> "+ "\n part S"+ partSName );
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			
			File repertoire = new File(tmpDirectory + "/" + "EcheancierPrevisionnel");
			File repertoireDest = new File(tmpDirectory + "/" + "EcheancierPrevisionnel" + "/" + dateString);     
					
			if (repertoire.mkdirs() && repertoireDest.mkdirs()) { 
				
				// repertoires created
				Path rootLocation = Paths.get(tmpDirectory); 
				
				// tmpFolder for partP1
				String tmpFolderPartP1 = dateString + File.separator + partP1Name; 
				
				// tmpFolder for partP2
				String tmpFolderPartP2 = dateString + File.separator + partP2Name;
				// tmpFolder for partS
				String tmpFolderPartS = dateString + File.separator + partSName;   
				
				// resolve tmpFolderPartP1
				Path pathPartP1 = rootLocation.resolve(tmpFolderPartP1);
				// resolve tmpFolderPartP2
				Path pathPartP2 = rootLocation.resolve(tmpFolderPartP2);
				// resolve tmpFolderPartS
				Path pathPartS = rootLocation.resolve(tmpFolderPartS);   
				
				LOG.info("File partP1 upload path: %s" + pathPartP1);
				LOG.info("File partP2 upload path: %s" + pathPartP2);
				LOG.info("File partS upload path: %s" + pathPartS); 
				
				
				// copy partP1 file
				Files.copy(partP1.getInputStream(), pathPartP1);
				// copy partP2 file
				Files.copy(partP2.getInputStream(), pathPartP2);
				// copy partS file
				Files.copy(partS.getInputStream(), pathPartS); 
				
				// set Echeancier Previsionnel
				EcheancierPrevisionnel echeancierPrevisionnel = new EcheancierPrevisionnel(); 
				
				echeancierPrevisionnel.setPartP1(tmpFolderPartP1);
				echeancierPrevisionnel.setPartP2(tmpFolderPartP2);
				echeancierPrevisionnel.setPartS(tmpFolderPartS);
				echeancierPrevisionnel.setDatePublication(datePublication);
				echeancierPrevisionnel.setDateExpiration(dateExpiration);
				echeancierPrevisionnel.setScenarioFondsDdt(scenariosFondsDDTJpaRepository.findOne(scenariosFondsDDTs));
				echeancierPrevisionnel.setScenarioFondsRa(scenariosFondsRAJpaRepository.findOne(scenariosFondsRa));
				echeancierPrevisionnel.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilogType));
				
				echeancierPrevisionnelJpaRepository.save(echeancierPrevisionnel);
				LOG.trace("TypeCredilogService : file uploaded successfully");
				return true;  
				
				
			} else {
				LOG.info("TypeCredilogService : Error creating directory");
				return false;
			}
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
	}

	@Override
	public List<EcheancierPrevisionnel> getByTypeCredilog(TypeCredilog typeCredilog) {
		return echeancierPrevisionnelJpaRepository.getByTypeCredilog(typeCredilog);
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
	public EcheancierPrevisionnel addFondsDataEcheancer(EcheancierPrevisionnel echeancierPrevisionnel) {
		return echeancierPrevisionnelJpaRepository.save(echeancierPrevisionnel);
	}

	@Override
	public boolean addNewEcheancerPrevisionnel( 
			
			MultipartFile file,
			MultipartFile myFilesPart1,
			MultipartFile myFilesPart2, 
			String datePublication, 
			String dateExpiration, 
			String scenariora,
			String scenarioddt) { 
		
		System.out.println("au moins je suis à ce niveau là nn");    
		
		String message;   
		Date datePubltion = parseStringDate(datePublication);            
		Date dateExp = parseStringDate(dateExpiration);                
	       
		System.out.println("on va afficher les valeurs des fichiers \n part A ===> "+ 
		file + "\npart B ==> "+
		myFilesPart1+ "\n part S ==> "
		+ myFilesPart2); 
		
		System.out.println("on affiche les autres infos \n dae de pub ===> "+ 
		datePublication +"\n date 'expiration ==> "+
				dateExpiration+ "\nscenario RA==> "+ scenariora+
				"\n scenario DDT ==>"+ scenarioddt);	  
		
		
		try {
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());   
			
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/echeancer");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/echeancer" + "/" + dateString);
					
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/echeancer");
			
			
			// file1
			String name = file.getOriginalFilename();  
			String tmpFolder = dateString + File.separator + name;  
			
			System.out.println("affichage de premier PART P1 ==> "+ name +"\n et de chemin de copie ===>"+ tmpFolder);  
			Path path = rootLocation.resolve(tmpFolder);
			Files.copy(file.getInputStream(), path);
			
			// file2   
			String namePartFile1 = myFilesPart1.getOriginalFilename();
			String tmpFolder1 = dateString + File.separator + namePartFile1;   
			
			System.out.println("affichage de 2ème PART P2 ==> "+ namePartFile1 +"\n et de chemin de copie ===>"+ tmpFolder1);  

			Path path1 = rootLocation.resolve(tmpFolder1); 
			Files.copy(myFilesPart1.getInputStream(), path1);
			
			// file3   
			String namePartFileS = myFilesPart2.getOriginalFilename();
			String tmpFolder2 = dateString + File.separator + namePartFileS;  
			
			System.out.println("affichage de 3ème  PART P3 ==> "+ namePartFileS +"\n et de chemin de copie ===>"+ tmpFolder2);  

			Path path3 = rootLocation.resolve(tmpFolder2);
			Files.copy(myFilesPart2.getInputStream(), path3);       
			
			
			EcheancierPrevisionnel echeancierPrevisionnel = new EcheancierPrevisionnel();
					
			echeancierPrevisionnel.setDateExpiration(dateExp);
			echeancierPrevisionnel.setDatePublication(datePubltion);
			echeancierPrevisionnel.setPartP1(dateString + File.separator + name);
			echeancierPrevisionnel.setPartP2(dateString + File.separator + namePartFile1);
			echeancierPrevisionnel.setPartS(dateString + File.separator + namePartFileS);
			
			TypeCredilog fonds = typeCredilogJpaRepository.findOne(Constants.idFondsSakane);
			ScenariosFondsDDT scenarioDdt = scenariosFondsDDTJpaRepository.findOne(Long.parseLong(scenarioddt));
			ScenariosFondsRA scenarioRa = scenariosFondsRAJpaRepository.findOne(Long.parseLong(scenariora));
			echeancierPrevisionnel.setScenarioFondsDdt(scenarioDdt);
			echeancierPrevisionnel.setScenarioFondsRa(scenarioRa);
			echeancierPrevisionnel.setTypeCredilog(fonds);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return addEcheancierPrevisionnel(echeancierPrevisionnel);
		} catch (Exception e) {
			message = "FAIL to upload" + file.getOriginalFilename() + "!";
			return false;
		}
	}

}