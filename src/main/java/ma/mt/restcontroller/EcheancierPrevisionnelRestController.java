package ma.mt.restcontroller;

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

import ma.mt.entity.EcheancierPrevisionnel;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IEcheancierPrevisionnelService;
import ma.mt.service.interfaces.ITypeCredilogService;

/*
 * Hassan
 */
/**
 * The Class EcheancierPrevisionnelRestController.
 */
@RequestMapping(value = "/api/EcheanciersPrevisionnels")
@RestController
public class EcheancierPrevisionnelRestController {

	/** The echeancier previsionnel service. */
	@Autowired
	private IEcheancierPrevisionnelService echeancierPrevisionnelService;
	@Autowired
	private ITypeCredilogService typeCredilogService;  

	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	List<EcheancierPrevisionnel> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return echeancierPrevisionnelService.getByTypeCredilog(typeCredilog);
	}

	/**
	 * Delete echeancier previsionnel.
	 *
	 * @param idEcheancierPrevisionnel
	 *            the id echeancier previsionnel
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idEcheancierPrevisionnel}", method = RequestMethod.DELETE)
	public boolean deleteEcheancierPrevisionnel(
			@PathVariable(name = "idEcheancierPrevisionnel") Long idEcheancierPrevisionnel) {
		return echeancierPrevisionnelService.deleteEcheancierPrevisionnel(idEcheancierPrevisionnel);
	}

	/**
	 * Adds the echeancier previsionnel.
	 *
	 * @param echeancierPrevisionnel
	 *            the echeancier previsionnel
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean addEcheancierPrevisionnel(EcheancierPrevisionnel echeancierPrevisionnel) {
		return echeancierPrevisionnelService.addEcheancierPrevisionnel(echeancierPrevisionnel);
	}

	/**
	 * List echeancier previsionnel.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<EcheancierPrevisionnel> listEcheancierPrevisionnel() {
		return echeancierPrevisionnelService.listEcheancierPrevisionnel();
	}

	/**
	 * Handle upload echeancier previsionnel files.
	 *
	 * @param partP1
	 *            the part P 1
	 * @param partP2
	 *            the part P 2
	 * @param partS
	 *            the part S
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param idScenariosFondsDDTs
	 *            the id scenarios fonds DD ts
	 * @param idScenariosFondsRa
	 *            the id scenarios fonds ra
	 * @param idTypeCredilogType
	 *            the id type credilog type
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/EcheancierPrevisionnel", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleUploadEcheancierPrevisionnelFiles(
			@RequestParam("part_p1") MultipartFile partP1,
			@RequestParam("part_p2") MultipartFile partP2, 
			@RequestParam("part_s") MultipartFile partS,
			@RequestParam("datepublication") Date datePublication,
			@RequestParam("dateExpiration") Date dateExpiration,
			@RequestParam("idScenariosFondsDDTs") Long idScenariosFondsDDTs,
			@RequestParam("idScenariosFondsRa") Long idScenariosFondsRa,
			@RequestParam("idTypeCredilogType") Long idTypeCredilogType) {
		return echeancierPrevisionnelService.createNewEcheancierPrevisionnel(partP1, partP2, partS, datePublication,
				dateExpiration, idScenariosFondsDDTs, idScenariosFondsRa, idTypeCredilogType);
	}

	@CrossOrigin
	@RequestMapping(value = "/echeancer", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addNewEcheancerPrevisionnel(
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3,
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration,
			@RequestParam("scenariora") String scenariora,
			@RequestParam("scenarioddt") String scenarioddt) {
		
		return echeancierPrevisionnelService
				.addNewEcheancerPrevisionnel(file1, file2, file3,datePublication, dateExpiration, scenariora, scenarioddt);
	}
}
