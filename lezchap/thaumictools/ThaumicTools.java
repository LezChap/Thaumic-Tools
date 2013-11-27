package lezchap.thaumictools;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.ShapedOreRecipe;
import lezchap.thaumictools.setup.*;
import lezchap.thaumictools.items.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;

@Mod(modid="ThaumicTools", name="ThaumicTools", version="0.0.8",
dependencies = "required-after:Thaumcraft")
@NetworkMod(clientSideRequired=true)

public class ThaumicTools {
        
            public static Item thaumiumShearsItem;
            public static Item thaumiumFishingPoleItem;
            public static Item thaumiumRodItem;
            public static Item thaumiumPoleandCarrotItem;
    
        // The instance of your mod that Forge uses.
        @Instance(value = "ThaumicTools")
        public static ThaumicTools instance;
        
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="lezchap.thaumictools.client.ClientProxy", serverSide="lezchap.thaumictools.CommonProxy")
        public static CommonProxy proxy;
        
        @EventHandler // used in 1.6.2
        //@PreInit    // used in 1.5.2
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
                File configFile = event.getSuggestedConfigurationFile();
                TTConfig.loadConfig(configFile);
        }
        
        @EventHandler // used in 1.6.2
        //@Init       // used in 1.5.2
        public void load(FMLInitializationEvent event) {
                thaumiumShearsItem = (new ItemThaumiumShears(TTConfig.thaumiumShearsItemId.getInt(), ThaumcraftApi.toolMatThaumium).setUnlocalizedName("tt.shears"));
            thaumiumFishingPoleItem = (new ItemThaumiumFishingPole(TTConfig.thaumiumFishingPoleItemId.getInt(), ThaumcraftApi.toolMatThaumium).setUnlocalizedName("tt.fishing.pole"));
            thaumiumRodItem = (new ItemThaumiumRod(TTConfig.thaumiumRodItemId.getInt())).setUnlocalizedName("tt.rod").setMaxStackSize(64);
            thaumiumPoleandCarrotItem = (new ItemThaumiumPoleandCarrot(TTConfig.thaumiumPoleandCarrotItemId.getInt())).setUnlocalizedName("tt.pole.and.carrot");
            
            LanguageRegistry.addName(thaumiumShearsItem, "Thaumium Shears");
            LanguageRegistry.addName(thaumiumFishingPoleItem, "Thaumium Fishing Pole");
            LanguageRegistry.addName(thaumiumRodItem, "Thaumium Rod");
            LanguageRegistry.addName(thaumiumPoleandCarrotItem, "Carrot on a Thaumium Stick");
            
                proxy.registerRenderers();
        }
        
        @EventHandler // used in 1.6.2
        //@PostInit   // used in 1.5.2
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
                GameRegistry.addRecipe(new ItemStack(thaumiumShearsItem, 1), " S", "S ", Character.valueOf('S'), ItemApi.getItem("itemResource", 2));
                /*GameRegistry.addRecipe(new ItemStack(thaumiumRodItem, 2), "S", "S", Character.valueOf('S'), ItemApi.getItem("itemResource", 2)); */
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thaumiumFishingPoleItem, 1), new Object[] {  "  T", " TS", "K S", Character.valueOf('T'), ItemApi.getItem("itemResource", 2), Character.valueOf('S'), new ItemStack(Item.silk), Character.valueOf('K'), "stickWood"}));
                GameRegistry.addRecipe(new ItemStack(thaumiumPoleandCarrotItem, 1), "F", "C", Character.valueOf('F'), new ItemStack(thaumiumFishingPoleItem), Character.valueOf('C'), new ItemStack(Item.carrot));
        }
}