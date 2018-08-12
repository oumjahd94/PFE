package ma.mt.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.dao.FondsDataRapportJpaRepository;
import ma.mt.entity.FondsDataRapport;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataRapportService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;

@Service
public class FondsDataRapportService implements IFondsDataRapportService {

	private static final Logger LOG = Logger.getLogger(FondsDataRapportService.class);

	@Autowired
	private FondsDataRapportJpaRepository fondsDataRapportJpaRepository;

	@Autowired
	private IFondsDataRapportService fondsDataRapportService ; 
	
	
	@Autowired  
	private ITypeCredilogService typeCredilogService;  
	
	@Override
	public List<FondsDataRapport> listFondsDataRapport(TypeCredilog typeCredilog) {
		try { 
			
			System.out.println("VOIR CE MESSAGE IMPORTATNT !! ");
			return fondsDataRapportJpaRepository.getByTypeCredilog(typeCredilog);
		} catch (Exception e) {
			LOG.info("Error listeDataRapport items !");
		}
		return null;
	}

	@Override
	public boolean addDataFondsRapport(FondsDataRapport rapport) {
		try {
			fondsDataRapportJpaRepository.save(rapport);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<FondsDataRapport> listFondsDataRapport() {
		try {
			return fondsDataRapportJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataRapportJpaRepository items !");
		}
		return null;
	}

	
	
	@Override
	public boolean addFondsDataRapport(
			MultipartFile rapportFile, 
			String  datePublication,
			String dateExpiration, 
			String dateRapport, 
			String anneeRapport 
			) { 
		         System.out.println("========HI I AM HERE HEEEE=========="); 
		         
		   if(rapportFile.isEmpty() || rapportFile == null){  
			   LOG.info("Null or empty rapport file to upload");  
				return false;
		   }  
		   
			Date datePub = fondsDataRapportService.parseStringDate(datePublication);
			Date dateExp = fondsDataRapportService.parseStringDate(dateExpiration); 
			Date dateR = fondsDataRapportService.parseStringDate(dateRapport);  
			int anneeR = Integer.parseInt(anneeRapport); 

		   try {
			// get rapport file name
			String name = rapportFile.getOriginalFilename();   
			
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile; 
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime()); 
			
			File repertoireFonds = new File(tmpDirectory + "/" + "documentation");
			File repertoire = new File(tmpDirectory + "/" + "documentation/rapport");
			File repertoireRapport = new File(tmpDirectory + "/" + "documentation/rapport" + "/" + dateString);
						
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireRapport.mkdirs(); 
			
			// copier le ficheir rapport reçu en paramètres dans le chemin construit
			Path rootLocation = Paths.get(tmpDirectory + "/" + "documentation" + "/rapport");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("Chemin de téléchargement du fichier : %s:" + path); 
			Files.copy(rapportFile.getInputStream(), path);  

			FondsDataRapport fondsDataRapport = new FondsDataRapport() ; 
		  
			//	Insertion des données 	
		   fondsDataRapport.setDateExpiration(dateExp); 
		   fondsDataRapport.setDatePublication(datePub); 
		   fondsDataRapport.setFicheRapport(tmpFolder);  
		   fondsDataRapport.setDateRapport(dateR);  
		   fondsDataRapport.setAnneeRapport(anneeR);
		   TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
		   fondsDataRapport.setTypeCredilog(fonds);  
			
		   fondsDataRapportJpaRepository.save(fondsDataRapport) ; 
		   LOG.trace("TypeDataRapportService : file uploaded successfully");
			
		} catch (IOException e) {
			LOG.info("RapportFile : Error uploading file");
			return false;
		} 
		   
		return true;
			
	}

	@Override
	public boolean deleteFondsDataRapport(Long idFondsDataRapport) {
		try {
			fondsDataRapportJpaRepository.delete(idFondsDataRapport);
			LOG.trace("Element has deleted successfully!");
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't deleted successfully !" + e.getMessage());
			return false;
		}
	}

	@Override
	public Date parseStringDate(String datePublication) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			return df.parse(datePublication);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Integer> vecAnnee() { 
		
		List<FondsDataRapport> vecRapports = listFondsDataRapport();   
				
		List<Integer> vecAnne = new ArrayList<>() ; 
		Set<Integer> hs = new HashSet<>();
         	
		for(int i=vecRapports.size()-1 ; i>=0; i--) {
			vecAnne.add(vecRapports.get(i).getAnneeRapport()) ;  
			System.out.println(i +"===> "+vecRapports.get(i).getAnneeRapport());
		}
				
		hs.addAll(vecAnne);
		vecAnne.clear();
		vecAnne.addAll(hs);      
		
		return vecAnne;
	}

}
