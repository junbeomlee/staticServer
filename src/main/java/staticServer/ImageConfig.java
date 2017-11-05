package staticServer;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;

@Configuration
public class ImageConfig {

	public String imageHomePath = "";
	/*
	 * issue static resource server로 video file 옮기기
	 */
	@Bean
	public ImageManager ImageManager(){

		/*
		 * url or filebased
		 */
		return new ImageManager();
	}
	
	public class ImageManager{
		
		private String imageHomePath;
		
		public ImageManager(){
			this.imageHomePath = "/home/expirit/nginx-www-html/static/image";
//			this.imageHomePath = "/Users/jun/nginx-www-html/static/image";
//			System.out.println(imageHomePath);
		}
		
		public ImageManager(String path){
			this.imageHomePath=path;
		}
		
		public byte[] loadImage(String imageName) throws IOException{
			File f = new File(this.imageHomePath+File.separator+imageName);
			InputStream targetStream = new FileInputStream(f);
			byte[] imageBytes=IOUtils.toByteArray(targetStream);
			return imageBytes;
		}
		
		public void writeImage(byte[] imageFile,String fileName){
			System.out.println(imageHomePath);
			try {
				String fileNm = fileName;
			    String filePath = this.imageHomePath + File.separator + fileNm;
			    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			    stream.write(imageFile);
			    stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static public boolean isValidURL(String urlStr) {
	    try {
	      URI uri = new URI(urlStr);
	      return uri.getScheme().equals("http") || uri.getScheme().equals("https");
	    }
	    catch (Exception e) {
	        return false;
	    }
	}
}

