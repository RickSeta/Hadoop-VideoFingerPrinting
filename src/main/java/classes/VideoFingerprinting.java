package classes;

import java.util.List;

public class VideoFingerprinting {
    public static Double[] fingerprint(List<Double[]> imageFingerprints) {
        
        
        Double[] videoFingerprint = aggregateFingerprints(imageFingerprints);
        
        return videoFingerprint;
    }
    
    
    private static Double[] aggregateFingerprints(List<Double[]> imageFingerprints) {
        
        int numImages = imageFingerprints.size();
        int fingerprintSize = imageFingerprints.get(0).length;
        
        Double[] videoFingerprint = new Double[fingerprintSize];
        
        for (int i = 0; i < fingerprintSize; i++) {
            Double sum = 0.0;
            
            for (int j = 0; j < numImages; j++) {
                sum += imageFingerprints.get(j)[i];
            }
            
            videoFingerprint[i] = sum / numImages;
        }
        
        return videoFingerprint;
    }
}
