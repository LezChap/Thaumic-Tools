package lezchap.thaumictools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;

public class TTResearchItem extends ResearchItem {
	
	public String title;
	public String text;

	public TTResearchItem(String par1, String par2, AspectList tags, int par3,
			int par4, int par5, ItemStack icon) {
		super(par1, par2, tags, par3, par4, par5, icon);
		// TODO Auto-generated constructor stub
	}
	
	 @SideOnly(Side.CLIENT)
	 @Override
    public String getName()
    {
    	return this.title;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public String getText()
    {
    	return this.text;
    }

    public void setInfo(String par1)
    {
    	this.text = par1;
    }
    
    public void setTitle(String par1)
    {
    	this.title = par1;
    }
}
