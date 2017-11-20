package staticServer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import staticServer.ImageConfig.ImageManager;

@CrossOrigin
@Slf4j
@RestController
public class StaticResourceController {
	
	@Autowired
	private ImageManager imageManager;

//	@RequestMapping(value = "/images/**/{name:.+}", method = RequestMethod.POST)
//    public ResponseEntity<String> ImageUpload(@RequestParam("imageFile") MultipartFile uploadfile,
//    										  @RequestParam("fileName") String fileName,
//    										  HttpServletRequest request) throws IOException {
//		
//		if (request.getRequestURI().trim().split("/images")[1] != null){
//			String fullPath = request.getRequestURI().trim().split("/images")[1];
//			String folderPath = fullPath.split(fileName)[0];
//			System.out.println(fullPath);
//			System.out.println(folderPath);
//		}
//		
//        imageManager.writeImage(uploadfile.getBytes(), fileName);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
	
	@RequestMapping(value = "/images/**/{name:.+}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> getArticleImage(@PathVariable("name") String name,
			  HttpServletRequest request) throws IOException {
		
		if (request.getRequestURI().trim().split("/images")[1] != null){
			String fullPath = request.getRequestURI().trim().split("/images")[1];
			 byte[] image = imageManager.loadImage(fullPath);
			    ByteArrayResource byteArrayResourceImage = new ByteArrayResource(image);
			    
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.IMAGE_JPEG);
			    headers.setContentLength(image.length);

			    return ResponseEntity.ok().headers(headers).contentLength(byteArrayResourceImage.contentLength())
						.contentType(MediaType.parseMediaType("application/octet-stream")).body(byteArrayResourceImage);
		}
		
		return null;
	}

//	@RequestMapping(value = "/images/{name:.+}", method = RequestMethod.GET)
//	public ResponseEntity<ByteArrayResource> getArticleImage(@PathVariable("name") String name) throws IOException {
// 
//	    byte[] image = imageManager.loadImage(name);
//	    ByteArrayResource byteArrayResourceImage = new ByteArrayResource(image);
//	    
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.IMAGE_JPEG);
//	    headers.setContentLength(image.length);
//
//	    return ResponseEntity.ok().headers(headers).contentLength(byteArrayResourceImage.contentLength())
//				.contentType(MediaType.parseMediaType("application/octet-stream")).body(byteArrayResourceImage);
//	}
}
