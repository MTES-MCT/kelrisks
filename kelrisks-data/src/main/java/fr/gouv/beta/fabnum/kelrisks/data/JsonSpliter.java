package fr.gouv.beta.fabnum.kelrisks.data;

import lombok.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Data
public class JsonSpliter {
    
    private final static String[] BAN_JSON = {"C:\\Users\\thomas.bouchardon\\Documents\\Projets\\Kelrisks\\Datas\\Cadastre\\",
                                              "{\"type\":\"FeatureCollection\",\"features\":[\n",
                                              "\n]}"};
    private final        File     file;
    int maxLines = 10000;
    
    public JsonSpliter(File file) {
        
        this.file = file;
    }
    
    public static void main(String[] args) {
        
        String[] jsonProperties   = JsonSpliter.BAN_JSON;
        File     dir              = new File(jsonProperties[0]);
        File[]   directoryListing = dir.listFiles();
        
        if (directoryListing != null) {
            
            for (File child : directoryListing) {
                
                if (child.isDirectory()) { continue; }
                
                JsonSpliter spliter = new JsonSpliter(child);
                spliter.setMaxLines(25000);
                spliter.split(jsonProperties[1], jsonProperties[2]);
            }
        }
    }
    
    public void split(String jsonStart, String jsonEnd) {
        
        try {
            
            Path         path          = Paths.get(file.getPath());
            String       fileName      = file.getName().replaceAll("(.*)\\..*", "$1");
            String       fileExtension = file.getName().replaceAll(".*\\.(.*)", "$1");
            List<String> strings       = Files.readAllLines(path);
            
            int          fileIndex     = 1;
            int          linesNumberOf = 0;
            StringBuffer stringBuffer  = new StringBuffer();
            
            for (int index = 1; index < strings.size() - 1; index++) {
                
                if (linesNumberOf == 0) {
                    stringBuffer = new StringBuffer();
                    stringBuffer.append(jsonStart);
                }
                
                stringBuffer.append(strings.get(index)).append("\n");
                
                if (linesNumberOf > maxLines) {
                    
                    stringBuffer.setLength(stringBuffer.length() - 2);
                    stringBuffer.append(jsonEnd);
                    linesNumberOf = -1;
                    
                    BufferedWriter bwr =
                            new BufferedWriter(new FileWriter(new File(file.getParent() + "\\splited\\" + fileName + "_" + String.format("%04d", fileIndex++) + "." + fileExtension)));
                    
                    bwr.write(stringBuffer.toString());
                    bwr.flush();
                    bwr.close();
                }
                
                linesNumberOf++;
            }
        }
        catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
