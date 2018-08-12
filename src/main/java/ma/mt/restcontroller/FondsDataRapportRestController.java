package ma.mt.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ma.mt.entity.FondsDataRapport;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataRapportService;
import ma.mt.service.interfaces.ITypeCredilogService;

@RequestMapping(value = "/api/FondsDataRapport")
@RestController
public class FondsDataRapportRestController { 
	
	
	@Autowired  
	private ITypeCredilogService typeCredilogService; 
	
	@Autowired 
	private IFondsDataRapportService fondsDataRapportService ; 
	
	// supprimer un rapport 
	@CrossOrigin
	@RequestMapping(value = "/{idFondDataRapport}", method = RequestMethod.DELETE)
	public boolean deleteFondsDataSnapshot(@PathVariable(name = "idFondDataRapport") Long idFondsDataRapport) {
		return fondsDataRapportService.deleteFondsDataRapport(idFondsDataRapport) ; 
	}  
	
	// récupérer la liste des années 
	@CrossOrigin
	@RequestMapping(value = "/vecAnnee", method = RequestMethod.GET)
	public List<Integer> VecAnnee(){		
		    return fondsDataRapportService.vecAnnee(); 
	}  
	
	
	// récupérer la liste des rapports d'un fond 
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	List<FondsDataRapport> getRapportsByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog); 
		return fondsDataRapportService.listFondsDataRapport(typeCredilog) ; 
	
	} 
	
	// l'ajout d'un document rapport 
	@CrossOrigin
	@RequestMapping(value = "/addRapports", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addFondsDataRapport(
			@RequestParam("file") MultipartFile file,
			@RequestParam("datePublication") String datePublication, 
			@RequestParam("dateExpiration") String dateExpiration, 
			@RequestParam("dateRapport") String dateRapport, 
			@RequestParam("anneeRapport") String anneeRapport
		) { 

		return fondsDataRapportService.addFondsDataRapport(
				file,
				datePublication, 
				dateExpiration, 
				dateRapport, 
				anneeRapport
				);	
	} 
	
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataRapport> listFondsDataRapport() {
		return fondsDataRapportService.listFondsDataRapport() ; 
	}
		
}
