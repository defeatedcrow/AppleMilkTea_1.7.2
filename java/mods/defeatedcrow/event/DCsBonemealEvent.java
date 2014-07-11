package mods.defeatedcrow.event;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.defeatedcrow.common.DCsAppleMilk;
import mods.defeatedcrow.common.DCsConfig;
import mods.defeatedcrow.common.block.*;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class DCsBonemealEvent
{
	@SubscribeEvent
	public void useBoneMeal(BonemealEvent event)
	{
		Block id = event.world.getBlock(event.x, event.y, event.z);
		if(id == DCsAppleMilk.cropMint)
		{
			if(((BlockMintCrop)DCsAppleMilk.cropMint).fertilize(event.world, event.x, event.y, event.z))
			{
				event.setResult(Result.ALLOW);
			}
		}
		else if (id == DCsAppleMilk.cassisTree)
		{
			if(((BlockCassisTree)DCsAppleMilk.cassisTree).fertilize(event.world, event.x, event.y, event.z))
			{
				event.setResult(Result.ALLOW);
			}
		}
		else if (id == DCsAppleMilk.teaTree)
		{
			if(((BlockTeaTree)DCsAppleMilk.teaTree).fertilize(event.world, event.x, event.y, event.z))
			{
				event.setResult(Result.ALLOW);
			}
		}
		else if (id == DCsAppleMilk.saplingTea)
		{
			if(((BlockSaplingTea)DCsAppleMilk.saplingTea).fertilize(event.world, event.x, event.y, event.z))
			{
				event.setResult(Result.ALLOW);
			}
		}
	}
 
}
