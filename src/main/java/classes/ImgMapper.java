package classes;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.mapreduce.Mapper;

public class ImgMapper extends Mapper<Object, Text, Text, DoubleArrayWritable> {
    private final DoubleArrayWritable outputValue = new DoubleArrayWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        
        String videoFolderName = fileSplit.getPath().getParent().getName();
        String fn = ((FileSplit) context.getInputSplit()).getPath().toString();
        String fileName;
        try {
            fileName = new URI(fn).getPath();

            Double[] imageText =  ImageFingerprinting.convert(fileName);
            DoubleWritable[] doubleWritables = Arrays.stream(imageText).
                map(d -> new DoubleWritable(d != null ? d : 0.0))
                .toArray(DoubleWritable[]::new);
            outputValue.set(doubleWritables);
            context.write(new Text(videoFolderName), outputValue);
        } catch (URISyntaxException e) {
            
        }

   
    }
}


