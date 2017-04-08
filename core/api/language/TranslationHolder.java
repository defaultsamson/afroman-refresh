package api.language;

import net.qwertysam.api.resource.Holder;

public class TranslationHolder extends Holder<Translation> {
    private TranslationKey key;
    // private List<Translation> translations;

    /**
     * Holds translations in different languages for a specified key.
     *
     * @param key          the identifier of this.
     * @param translations the translations for the key in different languages
     */
    public TranslationHolder(TranslationKey key, Translation... translations) {
        this.key = key;

        // this.translations = Arrays.asList(translations);
        for (Translation trans : translations) {
            registerEntry(trans);
        }
    }

    /**
     * The identifier of this.
     *
     * @return the key.
     */
    public TranslationKey getKey() {
        return key;
    }

    /**
     * Gets the translation for this in the given language.
     *
     * @param language the language to get the translation for
     * @return the translation.
     */
    public String getTranslation(Language language) {
        if (getEntries() == null || getEntries().isEmpty()) return key.name();

        for (Translation translated : getEntries()) {
            if (translated.getLanguages().contains(language)) return translated.getText();
        }

        // If the translation doesn't exist, get the English one
        // If no English translation exists, use the key
        return language == Language.ENGLISH ? key.name() : getTranslation(Language.ENGLISH);
    }
}
