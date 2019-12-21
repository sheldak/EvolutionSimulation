package utilities;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    private String path;

    public JSONWriter(String path) {
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    public void writeToJSON(int animals, int grasses, int[] genome, double energy, double lifetime, double children) throws IOException {
        JSONObject jsonObject = new JSONObject();

        StringBuilder genomeString = new StringBuilder();
        for(int i=0; i<32; i++)
            genomeString.append(genome[i]).append(" ");

        jsonObject.put("average number of animals", animals);
        jsonObject.put("average amount of grass", grasses);
        jsonObject.put("average dominant genome", genomeString.toString());
        jsonObject.put("average energy", energy);
        jsonObject.put("average lifetime", lifetime);
        jsonObject.put("average number of children", children);

        try (FileWriter file = new FileWriter(this.path)) {
            file.write(jsonObject.toJSONString());
        }

    }
}
