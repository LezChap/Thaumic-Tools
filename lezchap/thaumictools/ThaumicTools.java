package lezchap.thaumictools;

import java.io.File;
import java.util.HashMap;

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
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.ShapedOreRecipe;
import lezchap.thaumictools.setup.*;
import lezchap.thaumictools.client.RenderTickHandler;
import lezchap.thaumictools.client.RenderTimer;
import lezchap.thaumictools.items.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;

@Mod(modid="ThaumicTools", name="ThaumicTools", version="0.0.9",
dependencies = "required-after:Thaumcraft")
@NetworkMod(clientSideRequired=true)

public class ThaumicTools {
        
            public static Item thaumiumShearsItem;
            public static Item thaumiumFishingPoleItem;
            public static Item thaumiumRodItem;
            public static Item thaumiumPoleandCarrotItem;
            
            public static Item infusedFishingPoleItem;
            
            public static HashMap<String, Object> recipes = new HashMap();
    
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
                LanguageRegistry.instance().loadLocalization("/assets/thaumictools/lang/en_US.lang", "en_US", false);
        }
        
        @EventHandler // used in 1.6.2
        //@Init       // used in 1.5.2
        public void load(FMLInitializationEvent event) {
                thaumiumShearsItem = (new ItemThaumiumShears(TTConfig.thaumiumShearsItemId.getInt(), ThaumcraftApi.toolMatThaumium).setUnlocalizedName("tt.shears"));
            thaumiumFishingPoleItem = (new ItemThaumiumFishingPole(TTConfig.thaumiumFishingPoleItemId.getInt(), ThaumcraftApi.toolMatThaumium).setUnlocalizedName("tt.fishing.pole"));
            thaumiumRodItem = (new ItemThaumiumRod(TTConfig.thaumiumRodItemId.getInt())).setUnlocalizedName("tt.rod").setMaxStackSize(64);
            thaumiumPoleandCarrotItem = (new ItemThaumiumPoleandCarrot(TTConfig.thaumiumPoleandCarrotItemId.getInt())).setUnlocalizedName("tt.pole.and.carrot");
            
            infusedFishingPoleItem = (new ItemInfusedFishingPole(TTConfig.infusedFishingPoleItemId.getInt(), ThaumcraftApi.toolMatElemental).setUnlocalizedName("tt.infused.fishing.pole"));
            LanguageRegistry.addName(thaumiumShearsItem, "Thaumium Shears");
            LanguageRegistry.addName(thaumiumFishingPoleItem, "Thaumium Fishing Pole");
            LanguageRegistry.addName(thaumiumRodItem, "Thaumium Rod");
            LanguageRegistry.addName(thaumiumPoleandCarrotItem, "Carrot on a Thaumium Stick");
            LanguageRegistry.addName(infusedFishingPoleItem, "Fishing Pole of the Augur");
            
                proxy.registerRenderers();

                MinecraftForge.EVENT_BUS.register(RenderTickHandler.instance);
        }
        
        @EventHandler // used in 1.6.2
        //@PostInit   // used in 1.5.2
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
                recipes.put("ThaumiumShears", GameRegistry.addShapedRecipe(new ItemStack(thaumiumShearsItem, 1), " S", "S ", Character.valueOf('S'), ItemApi.getItem("itemResource", 2)));
                /*GameRegistry.addRecipe(new ItemStack(thaumiumRodItem, 2), "S", "S", Character.valueOf('S'), ItemApi.getItem("itemResource", 2)); */
                recipes.put("ThaumiumFishingPole", oreDictRecipe(new ItemStack(thaumiumFishingPoleItem, 1), new Object[] {  "  T", " TS", "K S", Character.valueOf('T'), ItemApi.getItem("itemResource", 2), Character.valueOf('S'), new ItemStack(Item.silk), Character.valueOf('K'), "stickWood"}));
                recipes.put("ThaumiumPoleandCarrot", GameRegistry.addShapedRecipe(new ItemStack(thaumiumPoleandCarrotItem, 1), "F", "C", Character.valueOf('F'), new ItemStack(thaumiumFishingPoleItem), Character.valueOf('C'), new ItemStack(Item.carrot)));
                
                registerResearchItemInfused("InfusedPole", new ItemStack(infusedFishingPoleItem, 1), 3, new AspectList().add(Aspect.WATER, 15).add(Aspect.SENSES, 15).add(Aspect.LIFE,15).add(Aspect.BEAST,8), new ItemStack(thaumiumFishingPoleItem, 1), new ItemStack(Item.ingotGold), new ItemStack(Item.enderPearl), new ItemStack(Item.fishRaw), new ItemStack(Item.compass));
                MinecraftForge.EVENT_BUS.register(new RenderTimer());
                
                
                TTResearchItem ri = new TTResearchItem("MORETHAUMIUM", "ALCHEMY",
        				(new AspectList()),
        				-2, 2, 2,
        				new ItemStack(thaumiumShearsItem, 1));
        		ri.setTitle("More Thaumium Tools");
        		ri.setInfo("Shears, Poles, Hammers, Oh My!");
        		ri.setParentsHidden("THAUMIUM");
        		ri.setConcealed();
        		ri.setPages(new ResearchPage("You've discovered Thaumium can make all sorts of tools, " +
        				"and here's a few more.  Most Thaumium tools are enchantable, though some enchants " +
        				"may act differently depending on the tool.\n\n" +
        				"For example, Efficiency on a Thaumium Fishing Pole will draw fish to the hook " +
        				"faster, and Silk Touch will ensure the fish keeps the hook in its mouth longer."),
					new ResearchPage((IRecipe)recipes.get("ThaumiumShears")),
					new ResearchPage((IRecipe)recipes.get("ThaumiumFishingPole")),
					new ResearchPage((IRecipe)recipes.get("ThaumiumPoleandCarrot")));
        		
        		ri.setConcealed();
        		
        		ri.registerResearchItem();
                //new ResearchItem("MORETHAUMIUM", "ALCHEMY", new AspectList(), -2, 2, 2, new ItemStack(thaumiumShearsItem, 1)).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.MORETHAUMIUM.1"), new ResearchPage((IRecipe)recipes.get("ThaumiumShears")), new ResearchPage((IRecipe)recipes.get("ThaumiumFishingPole")), new ResearchPage((IRecipe)recipes.get("ThaumiumPoleandCarrot"))}).setParents(new String[] { "THAUMIUM" }).registerResearchItem();
        		TTResearchItem ri2 = new TTResearchItem("INFUSEDPOLE", "ARTIFICE",
        				(new AspectList().add(Aspect.WATER,2).add(Aspect.SENSES, 2).add(Aspect.LIFE,2)),
        				-7, 2, 2,
        				new ItemStack(infusedFishingPoleItem, 1));
        		ri2.setTitle("Fishing Pole of the Augur");
        		ri2.setInfo("Fishing with senses!");
        		ri2.setParentsHidden("MORETHAUMIUM", "INFUSION");
        		ri2.setConcealed();
        		ri2.setPages(new ResearchPage("You've discovered how to enchance a Thaumium Fishing Pole so that " +
        				"you can sense a fish approaching it."),
					new ResearchPage((InfusionRecipe)recipes.get("InfusedPole")));
        		
        		ri2.registerResearchItem();
        }
        
        static IRecipe oreDictRecipe(ItemStack res, Object[] params)
        {
          IRecipe rec = new ShapedOreRecipe(res, params);
          CraftingManager.getInstance().getRecipeList().add(rec);
          return rec;
        }
        
        private static void registerResearchItemInfused(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
            InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
            recipes.put(name, recipe);
    }
}