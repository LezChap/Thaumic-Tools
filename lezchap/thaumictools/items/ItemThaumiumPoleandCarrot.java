package lezchap.thaumictools.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.item.ItemCarrotOnAStick;
import thaumcraft.api.IRepairable;
import lezchap.thaumictools.*;

public class ItemThaumiumPoleandCarrot extends ItemCarrotOnAStick implements IRepairable
{
	@SideOnly(Side.CLIENT)
    private Icon theIcon;

    public ItemThaumiumPoleandCarrot(int par1)
    {
        super(par1);
        this.setMaxDamage(45);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTransport);
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
            this.itemIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName());
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.isRiding() && par3EntityPlayer.ridingEntity instanceof EntityPig)
        {
            EntityPig entitypig = (EntityPig)par3EntityPlayer.ridingEntity;

            if (entitypig.getAIControlledByPlayer().isControlledByPlayer() && par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() >= 7)
            {
                entitypig.getAIControlledByPlayer().boostSpeed();
                par1ItemStack.damageItem(7, par3EntityPlayer);

                if (par1ItemStack.stackSize == 0)
                {
                    ItemStack itemstack1 = new ItemStack(ThaumicTools.thaumiumFishingPoleItem);
                    itemstack1.setTagCompound(par1ItemStack.stackTagCompound);
                    return itemstack1;
                }
            }
        }

        return par1ItemStack;
    }
}