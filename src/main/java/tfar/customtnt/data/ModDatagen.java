package tfar.customtnt.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import tfar.customtnt.data.assets.ModBlockstateProvider;
import tfar.customtnt.data.assets.ModItemModelProvider;
import tfar.customtnt.data.assets.ModLangProvider;

public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();
        if (e.includeClient()) {
            generator.addProvider(new ModBlockstateProvider(generator,helper));
            generator.addProvider(new ModItemModelProvider(generator,helper));
            generator.addProvider(new ModLangProvider(generator));
        }
    }

}
