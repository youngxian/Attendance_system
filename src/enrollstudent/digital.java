
package enrollstudent;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.image.Image;


public class digital {
   
    static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    public digital(){
        
    }
     public static final EnumMap<DPFPFingerIndex, String> fingerNames;

    static {
        fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
        fingerNames.put(DPFPFingerIndex.LEFT_THUMB, "left thumb");
        fingerNames.put(DPFPFingerIndex.RIGHT_THUMB, "right thumb");
    }

    public DPFPTemplate getTemplate(String activeReader, int nFinger) throws FileNotFoundException {
        System.out.printf("Performing fingerprint enrollment...\n");
        //  messagelabel.setText("Place your Left thumb");

        DPFPTemplate template = null;
        try {
            DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
            while (enrollment.getFeaturesNeeded() > 0) {
               
                DPFPSample sample = getSample(activeReader,
                        String.format("Scan your %s finger (%d remaining)\n", fingerName(finger), enrollment.getFeaturesNeeded()));

                //this.tet = String.format("%s", enrollment.getFeaturesNeeded());

                if (sample == null) {
                    continue;
                }
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

        } catch (DPFPImageQualityException e) {
            System.out.printf("Failed to enroll the finger.\n");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return template;
    }

    public DPFPSample getSample(String activeReader, String prompt)
            throws InterruptedException {
        final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
        DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
        capture.setReaderSerialNumber(activeReader);
        capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
        capture.addDataListener(new DPFPDataListener() {
            public void dataAcquired(DPFPDataEvent e) {
                if (e != null && e.getSample() != null) {
                    try {
                        samples.put(e.getSample());
                        //messagelabel.setText(String.format("Scan your %s finger", fingerName(finger), enrollment.getFeaturesNeeded()));
                        //String nn = String.format("%d", fingerName(finger), enrollment.getFeaturesNeeded());

                        //enrrolewwe");btnn.setDisable(true);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        capture.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;

            public void readerConnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    System.out.println("Reader is connected");
                }
                lastStatus = e.getReaderStatus();
            }

            public void readerDisconnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    System.out.println("Reader is disconnected");
                }
                lastStatus = e.getReaderStatus();
            }
        });

        try {
            capture.startCapture();
            System.out.print(prompt);

            return samples.take();
        } catch (RuntimeException e) {
            System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
            throw e;
        } finally {
            capture.stopCapture();
        }
    }

    public String fingerName(DPFPFingerIndex finger) {

        return fingerNames.get(finger);
    }

    public String fingerprintName(DPFPFingerIndex finger) {
        return fingerNames.get(finger) + " fingerprint";
    }

}
