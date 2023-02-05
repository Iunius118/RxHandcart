package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.world.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public final TranslatedNameProvider translatedNameProvider;

    public ModLanguageProvider(PackOutput packOutput, String locale, TranslatedNameProvider translatedNameProvider) {
        super(packOutput, RxHandcart.MOD_ID, locale);
        this.translatedNameProvider = translatedNameProvider;
    }

    public static void addProviders(boolean needsRun, DataGenerator dataGenerator, PackOutput packOutput) {
        dataGenerator.addProvider(needsRun, new ModLanguageProvider(packOutput, "en_us", new TranslatedNameProvider()));
        dataGenerator.addProvider(needsRun, new ModLanguageProvider(packOutput, "ja_jp", new JaJpNameProvider()));
    }

    // en_us
    public static class TranslatedNameProvider {
        // Items
        public String handcartName = "Handcart";
        public String handcartSettingName = "Switching Visibility of Handcart";
        // Container
        public String handcartContainerName = "Handcart";
    }

    // ja_jp
    public static class JaJpNameProvider extends TranslatedNameProvider {
        private JaJpNameProvider() {
            // Items
            handcartName = "荷車";
            handcartSettingName = "荷車の表示切替";
            // Container
            handcartContainerName = "荷車";
        }
    }

    @Override
    protected void addTranslations() {
        // Item groups
        add("itemGroup." + RxHandcart.MOD_ID, RxHandcart.MOD_NAME);

        // Items
        add(ModItems.HANDCART, translatedNameProvider.handcartName);
        add(ModItems.HANDCART_SETTING, translatedNameProvider.handcartSettingName);

        // Container
        add("container.rxhandcart.handcart", translatedNameProvider.handcartContainerName);
    }
}
