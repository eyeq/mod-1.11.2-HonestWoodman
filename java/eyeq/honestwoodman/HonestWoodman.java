package eyeq.honestwoodman;

import eyeq.honestwoodman.block.BlockStaticLiquidGold;
import eyeq.honestwoodman.event.HonestWoodmanEventHandler;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.Collections;

import static eyeq.honestwoodman.HonestWoodman.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class HonestWoodman {
    public static final String MOD_ID = "eyeq_honestwoodman";

    @Mod.Instance(MOD_ID)
    public static HonestWoodman instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Block waterGold;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HonestWoodmanEventHandler());
        if(event.getSide().isServer()) {
            return;
        }
        renderBlockModels();
        createFiles();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(event.getSide().isServer()) {
            return;
        }
        registerBlockColors();
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        waterGold = new BlockStaticLiquidGold(Material.WATER).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("goldWater");

        GameRegistry.register(waterGold, resource.createResourceLocation("water_gold"));
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlockModels() {
        ModelLoader.setCustomStateMapper(waterGold, block -> Collections.emptyMap());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColors() {
        BlockColors blockColors = FMLClientHandler.instance().getClient().getBlockColors();
        blockColors.registerBlockColorHandler((state, world, pos, tintIndex) -> 0xFFFF00, waterGold);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-HonestWoodman");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, waterGold, "Golden Water");
        language.register(LanguageResourceManager.JA_JP, waterGold, "金の水");

        ULanguageCreator.createLanguage(project, MOD_ID, language);
    }
}
