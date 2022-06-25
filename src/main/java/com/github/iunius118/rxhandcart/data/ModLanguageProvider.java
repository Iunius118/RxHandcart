package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.world.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public final TranslatedNameProvider translatedNameProvider;

    public ModLanguageProvider(DataGenerator gen, String locale, TranslatedNameProvider translatedNameProvider) {
        super(gen, RxHandcart.MOD_ID, locale);
        this.translatedNameProvider = translatedNameProvider;
    }

    public static void addProviders(boolean needsRun, DataGenerator gen) {
        gen.addProvider(needsRun, new ModLanguageProvider(gen, "en_us", new TranslatedNameProvider()));
        gen.addProvider(needsRun, new ModLanguageProvider(gen, "ja_jp", new JaJpNameProvider()));
    }

    // en_us
    public static class TranslatedNameProvider {
        public String handcartName = "Handcart";
        public String handcartSettingName = "Switching Visibility of Handcart";

        public String handcartContainerName = "Handcart";
    }

    // ja_jp
    public static class JaJpNameProvider extends TranslatedNameProvider {
        private JaJpNameProvider() {
            handcartName = "荷車";
            handcartSettingName = "荷車の表示切替";

            handcartContainerName = "荷車";
        }
    }

    @Override
    protected void addTranslations() {
        add(ModItems.HANDCART, translatedNameProvider.handcartName);
        add(ModItems.HANDCART_SETTING, translatedNameProvider.handcartSettingName);

        add("container.rxhandcart.handcart", translatedNameProvider.handcartContainerName);
    }
}
