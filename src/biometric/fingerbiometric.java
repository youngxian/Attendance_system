package biometric;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.readers.DPFPReaderDescription;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;

public class fingerbiometric {
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
    
        
}
