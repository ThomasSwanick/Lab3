package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator();
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {

        while (true) {
            String country = promptForCountry(translator);
            String quit = "quit";
            if (quit.equals(country)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            CountryCodeConverter converter = new CountryCodeConverter();
            String countryCode = converter.fromCountry(country);
            if (countryCode.isBlank()) {
                System.out.println();
                System.out.println("Please enter a valid country code.");;
            }
            else {
                String language = promptForLanguage(translator, countryCode);
                if (quit.equals(language)) {
                    break;
                }
                // TODO Task: Once you switch promptForLanguage so that it returns the language
                //            name rather than the 2-letter language code, you will need to
                //            convert it back to its 2-letter language code when calling translate.
                //            Note: you should use the actual names in the message printed below though,
                //            since the user will see the displayed message.
                LanguageCodeConverter languageConverter = new LanguageCodeConverter();
                String languageCode = languageConverter.fromLanguage(language);
                System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
                System.out.println("Press enter to continue or quit to exit.");
                Scanner s = new Scanner(System.in);
                String textTyped = s.nextLine();

                if (quit.equals(textTyped)) {
                    break;
                }
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        // TODO Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // TODO Task: convert the country codes to the actual country names before sorting
        CountryCodeConverter converter = new CountryCodeConverter();
        List<String> names = new ArrayList<>();
        for (String country : countries) {
            names.add(converter.fromCountryCode(country));
        }
        Collections.sort(names);
        for (String country : names) {
            System.out.println(country);
        }

        System.out.println("select a country from above or press quit to exit:");
        Scanner s = new Scanner(System.in);
        String country = s.nextLine();
        while(country.isEmpty()||!names.contains(country)) {
            if (country.equals("quit")){
                break;
            }
            System.out.println("select a country from above or press quit to exit:");
            s = new Scanner(System.in);
            country = s.nextLine();
        }
        return country;

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        // TODO Task: replace the line below so that we sort the languages alphabetically and print them out;
        //  one per line
        // TODO Task: convert the language codes to the actual language names before sorting
        List<String>languages = translator.getCountryLanguages(country);
        LanguageCodeConverter convert = new LanguageCodeConverter();
        List<String> languageNames = new ArrayList<>();
        for (String language : languages) {
            languageNames.add(convert.fromLanguageCode(language));
        }
        for (String language : languageNames) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");
        Scanner s = new Scanner(System.in);
        String word = s.nextLine();
        while (!languageNames.contains(word)) {
            System.out.println("select a language from above appropriately:");
            s = new Scanner(System.in);
            word = s.nextLine();
        }
        return word;
    }
}
