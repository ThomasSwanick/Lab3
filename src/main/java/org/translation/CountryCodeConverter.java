package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private HashMap<String, String[]> map_three;
    private HashMap<String, String[]> map_country_name;

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    @SuppressWarnings("checkstyle:WhitespaceAfter")
    public CountryCodeConverter(String filename) {
        this.map_three = new HashMap<>();
        this.map_country_name = new HashMap<>();
        try {
            // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // TODO Task: use lines to populate the instance variable(s)
            for (String line : lines) {
                String[] store = line.split("\t");
                String[] country_name_for_key;
                String[] countru_code_for_key;
                country_name_for_key = new String[]{store[1],store[2],store[3]};
                countru_code_for_key = new String[]{store[0],store[1],store[3]};
                map_country_name.put(store[0], country_name_for_key);
                map_three.put(store[2], countru_code_for_key);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        String key = code.toUpperCase();
        return map_three.get(key)[0];
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return map_country_name.get(country)[1];
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return map_country_name.size() - 1;
    }
}
