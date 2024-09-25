package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private JSONArray store;
    private Map<String,JSONObject> map_three_code;
    private Map<String,JSONObject> map_two_code;
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
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            store = new JSONArray(jsonString);
            // store as an array, where we could use it to access the value through
            // getJSONObject -- a function that is already been defined
            map_three_code = new HashMap<>();
            map_two_code = new HashMap<>();
            // the key will be determined by alpha3, as the later function is using 3-letter code to
            // access
            country_code = new ArrayList<>();
            // this is for other function, getCountries() below, which is easier to access
            // if we store as an instance variable
            for(int i = 0; i < store.length(); i++) {
                String key = store.getJSONObject(i).getString("alpha3");
                String key_two_code = store.getJSONObject(i).getString("alpha2");
                map_three_code.put(key.toUpperCase(),store.getJSONObject(i));
                map_two_code.put(key_two_code.toUpperCase(),store.getJSONObject(i));
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
        Map<String, JSONObject> set;
        country = country.toUpperCase();
        if (!map_three_code.containsKey(country) && !map_two_code.containsKey(country)) {
            System.out.println("Not found");
            return null;
        }
        else if (map_two_code.containsKey(country)) {
            set = map_two_code;
        }
        else {
            set = map_three_code;
        }
        Set<String> obj_l = set.get(country).keySet();
        // keyset is similar to dictionary get keys
        obj_l.remove("id");
        obj_l.remove("alpha2");
        obj_l.remove("alpha3");
        List<String> return_list = new ArrayList<>(obj_l);
        // cast to match the return type
        // remove the unrelated key (check the json document)
        return return_list;

    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return country_code;
    }

    @Override
    public String translate(String country, String language) {
        // directly go through the key with provided function
        Map<String, JSONObject> set;
        if (!map_three_code.containsKey(country.toUpperCase()) && !map_two_code.containsKey(country.toUpperCase())) {
            System.out.println(country);
            System.out.println(language);
            return "Country not found";
        }
        else if (map_three_code.containsKey(country.toUpperCase())){
            set  = map_three_code;
        }
        else{
            set = map_two_code;
        }
        return set.get(country.toUpperCase()).getString(language);
    }
}
