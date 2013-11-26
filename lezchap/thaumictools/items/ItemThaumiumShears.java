package lezchap.thaumictools.items;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.item.ItemShears;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;

public class ItemThaumiumShears extends ItemTool implements IRepairable
{
	@SideOnly(Side.CLIENT)
    private Icon theIcon;
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cloth};

    public ItemThaumiumShears(int par1, EnumToolMaterial enumtoolmaterial)
    {
    	super(par1, 1.0F, enumtoolmaterial, blocksEffectiveAgainst);
        this.setMaxDamage(425);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public Item setUnlocalizedName(String name)
    {
            super.setUnlocalizedName(name);
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
        return par2Block.blockID != Block.web.blockID && par2Block.blockID != Block.leaves.blockID ? (par2Block.blockID == Block.cloth.blockID ? 1.0F : super.getStrVsBlock(par1ItemStack, par2Block)) : 2.5F;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
            this.itemIcon = par1IconRegister.registerIcon("thaumictools:" + getUnlocalizedName());
    }
    
    public int getItemEnchantability()
    {
        return 5;
    }
    
    public String getToolMaterialName()
    {
        return "Thaumium";
    }
    
    public EnumRarity getRarity(ItemStack itemstack)
    {
      return EnumRarity.uncommon;
    }
    
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
      return par2ItemStack.isItemEqual((ItemApi.getItem("itemResource",2))) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
        if (par3 != Block.leaves.blockID && par3 != Block.web.blockID && par3 != Block.tallGrass.blockID && par3 != Block.vine.blockID && par3 != Block.tripWire.blockID && !(Block.blocksList[par3] instanceof IShearable))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID || par1Block.blockID == Block.redstoneWire.blockID || par1Block.blockID == Block.tripWire.blockID;
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity.worldObj.isRemote)
        {
            return false;
        }
        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable)entity;
            if (target.isShearable(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ,
                        EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

                Random rand = new Random();
                for(ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                itemstack.damageItem(1, entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        if (player.worldObj.isRemote)
        {
            return false;
        }
        int id = player.worldObj.getBlockId(x, y, z);
        if (Block.blocksList[id] instanceof IShearable)
        {
            IShearable target = (IShearable)Block.blocksList[id];
            if (target.isShearable(itemstack, player.worldObj, x, y, z))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z,
                        EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                Random rand = new Random();

                for(ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.worldObj, (double)x + d, (double)y + d1, (double)z + d2, stack);
                    entityitem.delayBeforeCanPickup = 10;
                    player.worldObj.spawnEntityInWorld(entityitem);
                }

                itemstack.damageItem(1, player);
                player.addStat(StatList.mineBlockStatArray[id], 1);
            }
        }
        return false;
    }
}