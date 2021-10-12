package tfar.customtnt.data.assets;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.customtnt.CustomTNT;
import tfar.customtnt.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator,  ExistingFileHelper existingFileHelper) {
        super(generator, CustomTNT.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        makeOneLayerItem(ModItems.REMOTE);
        makeOneLayerItem(ModItems.TNT_DRONE);
        makeSimpleBlockItem(ModItems.MIRACLE_GROW_TNT);
        makeSimpleBlockItem(ModItems.VACUUM_TNT);
    }


    protected void makeSimpleBlockItem(Item item, ResourceLocation loc) {
        getBuilder(item.getRegistryName().toString())
                .parent(getExistingFile(loc));
    }

    protected void makeSimpleBlockItem(Item item) {
        makeSimpleBlockItem(item,new ResourceLocation(CustomTNT.MODID,"block/" + item.getRegistryName().getPath()));
    }


    protected void makeOneLayerItem(Item item, ResourceLocation texture) {
        String path = item.getRegistryName().getPath();
        if (existingFileHelper.exists(new ResourceLocation(texture.getNamespace(),"item/" + texture.getPath())
                , ResourcePackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(path).parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0",new ResourceLocation(texture.getNamespace(),"item/" + texture.getPath()));
        } else {
            System.out.println("no texture for " + item + " found, skipping");
        }
    }

    protected void makeOneLayerItem(Item item) {
        ResourceLocation texture = item.getRegistryName();
        makeOneLayerItem(item,texture);
    }
}
