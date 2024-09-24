package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private JSONArray store;
    private HashMap<String,JSONObject> map;
    private List<String> country_code;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            store = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            store = new JSONArray(jsonString);
            map = new HashMap<>();
            country_code = new ArrayList<>();
            for(int i = 0; i < store.length(); i++) {
                String key = store.getJSONObject(i).getString("alpha3");
                map.put(key,store.getJSONObject(i));
                country_code.add(key);

            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        if(!map.containsKey(country)) {
            return null;
        }
        Set<String> obj_l = map.get(country).keySet();
        List<String> the_result = new ArrayList<>(obj_l);
        the_result.remove("id");
        the_result.remove("alpha2");
        the_result.remove("alpha3");
        return the_result;

    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return country_code;
    }

    @Override
    public String translate(String country, String language) {
        if (!map.containsKey(country)){
            return "Country not found";
        }
        return map.get(country).getString(language);
    }
}
