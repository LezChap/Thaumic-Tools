package lezchap.thaumictools.setup;

import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class TTConfig {
	public static Property thaumiumShearsItemId;
    public static Property thaumiumFishingPoleItemId;
    public static Property thaumiumRodItemId;
    public static Property thaumiumPoleandCarrotItemId;
	public static Property infusedFishingPoleItemId;

    
	public static void loadConfig(File configFile)
    {
            Configuration c = new Configuration(configFile);
            
            thaumiumShearsItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.ThaumiumShears", 26430);
            thaumiumFishingPoleItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.ThaumiumFishingPole", 26431);
            thaumiumRodItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.ThaumiumRod", 26432);
            thaumiumPoleandCarrotItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.ThaumiumPoleandCarrot", 26433);
            
            infusedFishingPoleItemId = c.getItem(Configuration.CATEGORY_ITEM,  "ID.InfusedFishingPole", 26434);
            c.save();
    }
}
