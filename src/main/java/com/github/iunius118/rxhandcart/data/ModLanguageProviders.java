package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviders {
    public static void addTo(DataGenerator generator) {
        generator.addProvider(new ENLanguageProviders(generator));
        generator.addProvider(new JPLanguageProviders(generator));
    }

    private static class ENLanguageProviders extends LanguageProvider {
        public ENLanguageProviders(DataGenerator generator) {
            super(generator, RxHandcart.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            add(ModItems.HANDCART, "Handcart");
        }
    }

    private static class JPLanguageProviders extends LanguageProvider {
        public JPLanguageProviders(DataGenerator generator) {
            super(generator, RxHandcart.MOD_ID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            add(ModItems.HANDCART, "荷車");
        }
    }
}
