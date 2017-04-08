package api.language;

import net.qwertysam.api.resource.Holder;

public class TranslationManager extends Holder<TranslationHolder> {
    public TranslationManager() {
        super();
    }

    /**
     * Gets the translation of a key for a specified language.
     *
     * @param key       the key
     * @param lanaguage the language to translate to
     * @return the translation of the key.
     */
    public String getTranslation(TranslationKey key, Language language) {
        if (key == null) return "Null Key";
        if (language == null) return "Null Language";

        for (TranslationHolder trans : getEntries()) {
            if (trans.getKey() != null && trans.getKey().equals(key)) return trans.getTranslation(language);
        }

        return "Invalid Key";
    }
}
