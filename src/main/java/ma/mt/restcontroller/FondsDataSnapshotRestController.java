package ma.mt.restcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataSnapshot;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataSnapshotService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
import ma.mt.util.FileProcess;
/*
 * Hassan
 */
@RestController
@RequestMapping(value = "/api/fondsDataSnapshots")
public class FondsDataSnapshotRestController {
	
	
	@Autowired
	private IFondsDataSnapshotService fondsDataSnapshotService;
	@Autowired
	private ITypeCredilogService typeCredilogService;

	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	List<FondsDataSnapshot> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return fondsDataSnapshotService.getByTypeCredilog(typeCredilog);
	}

	@CrossOrigin
	@RequestMapping(value = "/{idFondDataSnapshot}", method = RequestMethod.DELETE)
	public boolean deleteFondsDataSnapshot(@PathVariable(name = "idFondDataSnapshot") Long idFondsDataSnapshot) {
		return fondsDataSnapshotService.deleteFondsDataSnapshot(idFondsDataSnapshot);
	}

	@CrossOrigin
	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addFondsDataSnapshot(@RequestParam("snapshotFile") MultipartFile snapshotFile,
			@RequestParam("datePublication") Date datePublication, @RequestParam("dateExpiration") Date dateExpiration,
			@RequestParam("arrete") Date arrete, @RequestParam("idTypeCredilog") Long idTypeCredilog) { 
		
		return fondsDataSnapshotService.addFondsDataSnapshot(snapshotFile,
				datePublication, 
				dateExpiration, 
				arrete,
				idTypeCredilog);
	}

	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataSnapshot> listFondsDataSnapshot() {
		return fondsDataSnapshotService.listFondsDataSnapshot();
	}

	@CrossOrigin
	@RequestMapping(value = "/download/{documentName}", method = RequestMethod.GET)
	public Response downloadDocument(@PathVariable(name = "documentName") String documentName) {
		String uploadPathFile = Constants.uploadPathFile + File.separator + "fonds" 
	   + File.separator + "snapshot"
				+ File.separator + documentName;
		if (FileProcess.exist(uploadPathFile)) {
			File file = new File(uploadPathFile); 
			
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + documentName);
			return response.build();
		}
		return null;

	} 

	@CrossOrigin
	@RequestMapping(value = "/uploadFile", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("dateArrete") String dateArrete, 
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration) { 
		
		String message;
		Date dateArr = fondsDataSnapshotService.parseStringDate(dateArrete);
		Date datePubltion = fondsDataSnapshotService.parseStringDate(datePublication);
		Date dateExp = fondsDataSnapshotService.parseStringDate(dateExpiration); 
		
		try {
			String name = file.getOriginalFilename();
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
			Files.copy(file.getInputStream(), path);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			FondsDataSnapshot fondsDataSnapshot = new FondsDataSnapshot();
			fondsDataSnapshot.setDateArrete(dateArr);
			fondsDataSnapshot.setDateExpiration(dateExp);
			fondsDataSnapshot.setDatePublication(datePubltion);
			fondsDataSnapshot
					.setFicheSnapshot(dateString + File.separator + name);
			TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
			fondsDataSnapshot.setTypeCredilog(fonds);
			fondsDataSnapshotService.addFondsDataSnapshot(fondsDataSnapshot);
			return true;
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return false;
		}
	}

}
