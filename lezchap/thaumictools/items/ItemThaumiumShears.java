package lezchap.thaumictools.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.item.ItemShears;
import thaumcraft.api.IRepairable;

public class ItemThaumiumShears extends ItemShears implements IRepairable
{
	@SideOnly(Side.CLIENT)
    private Icon theIcon;

    public ItemThaumiumShears(int par1)
    {
        super(par1);
        this.setMaxDamage(425);
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
    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block.blockID != Block.web.blockID && par2Block.blockID != Block.leaves.blockID ? (par2Block.blockID == Block.cloth.blockID ? 2.5F : super.getStrVsBlock(par1ItemStack, par2Block)) : 7.5F;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
            this.itemIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName());
    }
}