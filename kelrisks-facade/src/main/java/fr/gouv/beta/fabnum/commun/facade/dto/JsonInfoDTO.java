package fr.gouv.beta.fabnum.commun.facade.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JsonInfoDTO {
    
    private boolean      hasError  = false;
    private List<String> errorList = new ArrayList<>();
    
    private boolean      hasInfo  = false;
    private List<String> infoList = new ArrayList<>();
    
    private boolean      hasSuccess  = false;
    private List<String> successList = new ArrayList<>();
    
    private boolean      hasWarning  = false;
    private List<String> warningList = new ArrayList<>();
    
    public void addError(String error) {
        
        hasError = true;
        errorList.add(error);
    }
    
    public void addWarning(String warning) {
        
        hasWarning = true;
        warningList.add(warning);
    }
    
    public void addInfo(String info) {
        
        hasInfo = true;
        infoList.add(info);
    }
    
    public void addSuccess(String success) {
        
        hasSuccess = true;
        successList.add(success);
    }
}
