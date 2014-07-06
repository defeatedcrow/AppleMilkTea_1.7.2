package mods.defeatedcrow.plugin;

import mods.defeatedcrow.common.DCsAppleMilk;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

/*TE3の連携機能にはIMCを利用するため、他のMODと異なりInitクラスで呼び出す。*/
public class LoadTE3Plugin {
	
	public void load() {
		
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 800);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("primaryOutput", new NBTTagCompound());
		toSend.setTag("secondaryOutput", new NBTTagCompound());

		new ItemStack(DCsAppleMilk.leafTea, 1, 0).writeToNBT(toSend.getCompoundTag("input"));
		new ItemStack(DCsAppleMilk.EXItems, 2, 2).writeToNBT(toSend.getCompoundTag("primaryOutput"));
		new ItemStack(DCsAppleMilk.leafTea, 1, 1).writeToNBT(toSend.getCompoundTag("secondaryOutput"));
		toSend.setInteger("secondaryChance", 10);
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
		
		NBTTagCompound toSend2 = new NBTTagCompound();
		toSend2.setInteger("energy", 1600);
		toSend2.setTag("input", new NBTTagCompound());
		toSend2.setTag("primaryOutput", new NBTTagCompound());
		toSend2.setTag("secondaryOutput", new NBTTagCompound());

		new ItemStack(DCsAppleMilk.itemLargeBottle, 1, 0).writeToNBT(toSend2.getCompoundTag("input"));
		new ItemStack(DCsAppleMilk.EXItems, 2, 11).writeToNBT(toSend2.getCompoundTag("primaryOutput"));
		new ItemStack(DCsAppleMilk.EXItems, 1, 11).writeToNBT(toSend2.getCompoundTag("secondaryOutput"));
		toSend2.setInteger("secondaryChance", 50);
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend2);
		
		NBTTagCompound toSend3 = new NBTTagCompound();
		toSend3.setInteger("energy", 400);
		toSend3.setTag("input", new NBTTagCompound());
		toSend3.setTag("output", new NBTTagCompound());

		new ItemStack(Items.apple, 1, 0).writeToNBT(toSend3.getCompoundTag("input"));
		new ItemStack(DCsAppleMilk.bakedApple, 1, 0).writeToNBT(toSend3.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "FurnaceRecipe", toSend3);
		
		NBTTagCompound toSend4 = new NBTTagCompound();
		toSend4.setInteger("energy", 800);
		toSend4.setTag("input", new NBTTagCompound());
		toSend4.setTag("output", new NBTTagCompound());

		new ItemStack(Items.rotten_flesh, 1, 0).writeToNBT(toSend4.getCompoundTag("input"));
		new ItemStack(Items.leather, 1, 0).writeToNBT(toSend4.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "FurnaceRecipe", toSend4);
		
	}

}
