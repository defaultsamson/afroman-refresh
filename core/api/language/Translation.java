package api.language;

import java.util.Arrays;
import java.util.List;

public class Translation {
    private List<Language> languages;
    private String text;

    /**
     * A translation of text for a specified language.
     *
     * @param text      the text
     * @param languages the languages that the text applies to
     */
    public Translation(String text, Language... languages) {
        this.languages = Arrays.asList(languages);
        this.text = text;
    }

    /**
     * Gets a list of all the languages that the text in this applies to.
     *
     * @return the list of languages.
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * @return the text translated for the provided languages.
     */
    public String getText() {
        return text;
    }
}
