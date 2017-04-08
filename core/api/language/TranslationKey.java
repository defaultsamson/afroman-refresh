package api.language;

import net.qwertysam.main.MyGdxGame;

public enum TranslationKey {
    GUI_BUTTON_PLAY,
    GUI_BUTTON_OPTIONS,
    GUI_BUTTON_HELP,
    GUI_BUTTON_BACK,
    GUI_BUTTON_VIDEO,
    GUI_BUTTON_LANGUAGE;

    public String translate(MyGdxGame game) {
        return translate(game.getTranslator(), game.getLanguage());
    }

    public String translate(MyGdxGame game, Language lang) {
        return translate(game.getTranslator(), lang);
    }

    public String translate(TranslationManager trans, Language lang) {
        return trans.getTranslation(this, lang);
    }
}
