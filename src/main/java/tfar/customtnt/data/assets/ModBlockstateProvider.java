package tfar.customtnt.data.assets;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.customtnt.CustomTNT;
import tfar.customtnt.init.ModBlocks;

public class ModBlockstateProvider extends BlockStateProvider {
    public ModBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CustomTNT.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.MIRACLE_GROW_TNT).forAllStates(state -> {
            ModelFile modelFile = models().cubeBottomTop("miracle_grow_tnt",modLoc("block/miracle_grow_tnt_side"),modLoc("block/miracle_grow_tnt_bottom"),modLoc("block/miracle_grow_tnt_top"));
            return ConfiguredModel.builder().modelFile(modelFile).build();
        });

        getVariantBuilder(ModBlocks.VACUUM_TNT).forAllStates(state -> {
            ModelFile modelFile = models().cubeBottomTop("vacuum_tnt",modLoc("block/vacuum_tnt_side"),modLoc("block/vacuum_tnt_bottom"),modLoc("block/vacuum_tnt_top"));
            return ConfiguredModel.builder().modelFile(modelFile).build();
        });
    }

}
