package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ma.mt.entity.FondsDataRapport;
import ma.mt.entity.TypeCredilog;

public interface IFondsDataRapportService { 
	
	
	   
	// tous les rapports 
	List<FondsDataRapport> listFondsDataRapport();   
	
	// la liste des rapports par type de crédilog 
	List<FondsDataRapport> listFondsDataRapport(TypeCredilog typeCredilog);   
    
	// ajouter un rapport 
	boolean addDataFondsRapport(FondsDataRapport rapport);   
	
	// ajouter un rapport 2ème fonction 
	boolean addFondsDataRapport(MultipartFile rapportFile, String datePublication, String dateExpiration, String dateRapport, String anneeRaport); 
	
	// supprimer un rapport 
	boolean deleteFondsDataRapport(Long idFondsDataSnapshot);

	Date parseStringDate(String datePublication);
	
	
	List<Integer> vecAnnee() ;    

}      
