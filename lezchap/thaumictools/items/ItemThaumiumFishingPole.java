package lezchap.thaumictools.items;

import java.util.logging.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import lezchap.thaumictools.Utils.LoggerHelper;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;

public class ItemThaumiumFishingPole extends ItemTool implements IRepairable
{
	@SideOnly(Side.CLIENT)
    private Icon theIcon;
	
    public static final Block[] blocksEffectiveAgainst = new Block[] {};

    public ItemThaumiumFishingPole(int par1, EnumToolMaterial enumtoolmaterial)
    {
    	super(par1, 1.0F, enumtoolmaterial, blocksEffectiveAgainst);
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
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	///LoggerHelper.log(Level.INFO, "Right Clicked");
        if (par3EntityPlayer.fishEntity != null)
        {
            int i = par3EntityPlayer.fishEntity.catchFish();
            par1ItemStack.damageItem(i, par3EntityPlayer);
            par3EntityPlayer.swingItem();
            //LoggerHelper.log(Level.INFO, "Entity!=null");
        }
        else
        {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
           // LoggerHelper.log(Level.INFO, "Entity=null");
            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
            }

            par3EntityPlayer.swingItem();
        }

        return par1ItemStack;
    }
    
    @SideOnly(Side.CLIENT)
    public Icon func_94597_g()
    {
        return this.theIcon;
    }
    
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName() + "_uncast");
        this.theIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName() + "_cast");
    }
}