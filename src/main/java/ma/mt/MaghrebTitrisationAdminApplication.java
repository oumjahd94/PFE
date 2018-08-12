package ma.mt;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ma.mt.entity.FondsDataRapport;
import ma.mt.service.FondsDataRapportService;
import ma.mt.service.interfaces.IFondsDataRapportService;
import ma.mt.service.interfaces.IStorageService;
/*
 * Hassan
 */
/**
 * The Class MaghrebTitrisationAdminApplication.
 */
@SpringBootApplication
public class MaghrebTitrisationAdminApplication implements CommandLineRunner{
	@Resource
	private IStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(MaghrebTitrisationAdminApplication.class, args);  
		System.out.println("----- Your application Back Office has started----- !!!");

	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();	 		
    }
}
