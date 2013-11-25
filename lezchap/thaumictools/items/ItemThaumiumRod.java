package lezchap.thaumictools.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemThaumiumRod extends Item{
    public ItemThaumiumRod(int par1)
    {
        super(par1);
        this.setMaxDamage(256);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("tt.thaumium.rod");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setTextureName("thaumictools:" + getUnlocalizedName());
    }
}
