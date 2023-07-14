package classes;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.DoubleWritable;

public class VideoReducer extends Reducer<Text, DoubleArrayWritable, Text, DoubleArrayWritable> {
   
  private final DoubleArrayWritable outputValue = new DoubleArrayWritable();
  
  public void reduce(Text key, Iterable<DoubleArrayWritable> values, Context context) throws IOException, InterruptedException {

    List<Double[]> imageFingerprints = new ArrayList<>();
    for (DoubleArrayWritable value : values) {
      Writable[] writables = value.get();
      DoubleWritable[] dbWritables = Arrays.copyOf(writables, writables.length, DoubleWritable[].class);
      Double[] doubles = Arrays.stream(dbWritables)
          .map(dblWritable -> dblWritable != null ? dblWritable.get() : 0.0)
          .toArray(Double[]::new);
      imageFingerprints.add(doubles);
      
    }
    
    Double[] videoFingerprint = VideoFingerprinting.fingerprint(imageFingerprints);

    DoubleWritable[] doubleWritables = Arrays.stream(videoFingerprint)
        .map(d -> new DoubleWritable(d != null ? d : 1.0))
        .toArray(DoubleWritable[]::new);

    outputValue.set(doubleWritables);
    context.write(key, outputValue);
    
    
  }
}
