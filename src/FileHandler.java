import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class FileHandler{
    private static int BUFFER = 4096;
    public File file;
    public File unzipFolder;
    public List<BufferedImage> images;

    public FileHandler(String pathToFile){
        file = new File(pathToFile);
        unzipFolder = new File("unzipped/"+file.getName().substring(0, file.getName().length()-4));
        unzipFolder.mkdir();

        try {
            getZipFiles(file.getPath(), unzipFolder.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void getZipFiles(String zipFile, String destFolder) throws IOException {
        BufferedOutputStream dest = null;
        
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        
        ZipEntry entry;
        
        while (( entry = zis.getNextEntry() ) != null) {
            System.out.println( "Extracting: " + entry.getName() );
            int count;
            byte data[] = new byte[BUFFER];
    
            /*if (entry.isDirectory()) {
                new File( destFolder + "/" + entry.getName() ).mkdirs();
                continue;
            } else {*/
                int di = entry.getName().lastIndexOf( '/' );
                if (di != -1) {
                    new File( destFolder + "/" + entry.getName().substring( 0, di ) ).mkdirs();
                //}
            }
            FileOutputStream fos = new FileOutputStream( destFolder + "/" + entry.getName() );
            dest = new BufferedOutputStream( fos );
            while (( count = zis.read( data ) ) != -1) 
                dest.write( data, 0, count );
            
            
            dest.flush();
            dest.close();
            //zis.close();
        }
    }

    public void ReadImages(){}
}