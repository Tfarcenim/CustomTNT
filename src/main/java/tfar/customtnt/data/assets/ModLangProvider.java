package tfar.customtnt.data.assets;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.customtnt.CustomTNT;
import tfar.customtnt.init.ModBlocks;
import tfar.customtnt.init.ModItems;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, CustomTNT.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlock(() -> ModBlocks.MIRACLE_GROW_TNT,"Miracle Grow TNT");
        addBlock(() -> ModBlocks.VACUUM_TNT,"Vacuum TNT");

        addItem(() -> ModItems.REMOTE,"Remote");
        addItem(() -> ModItems.TNT_DRONE,"TNT Drone");
    }
}
