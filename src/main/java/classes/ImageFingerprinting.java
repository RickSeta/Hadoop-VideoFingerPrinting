package classes;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.plugin.filter.GaussianBlur;
import ij.plugin.filter.FFTFilter ;

public class ImageFingerprinting {
    public static Double[] convert(String imageFilename) {
 
        ImagePlus image = new ImagePlus(imageFilename);
        ImageProcessor processor = image.getProcessor();
        
        processor = preprocessImage(processor);
        Double[] fingerprint = calculateFingerprint(processor);

        image.close();

        return fingerprint;
        
    }
    
    private static ImageProcessor preprocessImage(ImageProcessor processor) {
        processor = processor.resize(256, 256);
        GaussianBlur gaus = new GaussianBlur();
        gaus.blurGaussian(processor, 0, 0, 0);
        
        return processor;
    }
    
    private static Double[] calculateFingerprint(ImageProcessor processor) {
        ImageProcessor floatProcessor = processor.convertToFloat();
        // FFTFilter fft = new FFTFilter();
        // fft.run(processor);
        
        // Extract the relevant features from the FFT result
        Double[] features = extractFeatures(floatProcessor);
        
        return features;
    }
    
    private static Double[] extractFeatures(ImageProcessor processor) {
        // Extract relevant features from the image (e.g., statistical measures, frequency components, etc.)
        // Here, we'll simply return the pixel values as features
        int width = processor.getWidth();
        int height = processor.getHeight();
        Double[] features = new Double[width * height];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                features[y * width + x] = Double.valueOf(processor.getPixelValue(x, y));
            }
        }
        
        return features;
    }
}

