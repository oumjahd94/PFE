package ma.mt.service.interfaces;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
 void store(MultipartFile file);
 Resource loadFile(String fileName);
 void deleteAll();
 void init();
}
