package lezchap.thaumictools.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.item.ItemFishingRod;
import thaumcraft.api.IRepairable;

public class ItemThaumiumFishingPole extends ItemFishingRod implements IRepairable
{
	@SideOnly(Side.CLIENT)
    private Icon theIcon;

    public ItemThaumiumFishingPole(int par1)
    {
        super(par1);
        this.setMaxDamage(256);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public Item setUnlocalizedName(String name)
    {
            super.setUnlocalizedName(name);
            GameRegistry.registerItem(this, getUnlocalizedName());
            return this;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName() + "_uncast");
        this.theIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName() + "_cast");
    }
}