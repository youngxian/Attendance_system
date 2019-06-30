package biometric;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.readers.DPFPReaderDescription;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import java.util.EnumMap;

public class fingerbiometric {
    static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    public fingerbiometric() {

    }
/// check if the fingerprint device is connected
    public Boolean listReaders() {
        Boolean connected = false;
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();
        if (readers == null || readers.size() == 0) {
            // System.out.printf("There are no readers available.\n");
            // this.device = "DigitalPersona is Connected";
            connected = false;
            return connected;
        } else {

            //System.out.printf("Available readers:\n");
            for (DPFPReaderDescription readerDescription : readers) {
                System.out.println(readerDescription.getSerialNumber());
            }
            connected = true;
            return connected;
        }
    }
    
	public static final EnumMap<DPFPFingerIndex, String> fingerNames;
    static { 
    	fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
    	fingerNames.put(DPFPFingerIndex.LEFT_THUMB,   "left thumb");
    	fingerNames.put(DPFPFingerIndex.RIGHT_THUMB,  "right thumb");
    } 
    /////
    //Enrol finger and get template;
    ////
    
    
	public DPFPTemplate getTemplate(String activeReader, int nFinger) {
        System.out.printf("Performing fingerprint enrollment...\n");
        DPFPTemplate template = null;
        try { 
            DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();     
            while (enrollment.getFeaturesNeeded() > 0)
            { 
                DPFPSample sample = getSample(activeReader, 
                	String.format("Scan your %s finger (%d remaining)\n", fingerName(finger), enrollment.getFeaturesNeeded()));
                if (sample == null)
                    continue; 
                DPFPFeatureSet featureSet;
                try { 
                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
                } catch (DPFPImageQualityException e) {
                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());
                    continue; 

                }  
                enrollment.addFeatures(featureSet);
            } 
            template = enrollment.getTemplate();
            System.out.printf("The %s was enrolled.\n", fingerprintName(finger));
        } catch (DPFPImageQualityException e) {
            System.out.printf("Failed to enroll the finger.\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return template;
    }
        
    
    // finger name    
    public String fingerName(DPFPFingerIndex finger) {

    	return fingerNames.get(finger); 

    } 

    public String fingerprintName(DPFPFingerIndex finger) {

    	return fingerNames.get(finger) + " fingerprint"; 

    } 
        
}
