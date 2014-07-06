package mods.defeatedcrow.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldgenTeaTree implements IWorldGenerator {
	
	private int genDim1 = 0;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		genDim1 = world.provider.dimensionId;
		
		int chunk2X = chunkX << 4;
        int chunk2Z = chunkZ << 4;         
        int count = DCsConfig.teaTreeGenValue;
        
    	if ((genDim1 == 0))
    	{
    		for(int i = 0; i < count; i++)//tea
			{
				int PosX = chunk2X + random.nextInt(16);
				int PosY = 70 + random.nextInt(30);
				int PosZ = chunk2Z + random.nextInt(16);
				
				if (world.getBlockLightValue(PosX, PosY, PosZ) > 11 && world.isAirBlock(PosX, PosY, PosZ) && world.getBlock(PosX, PosY - 1, PosZ) == Blocks.grass)
				{
					world.setBlock(PosX, PosY, PosZ, DCsAppleMilk.teaTree, 0, 2);
				}
			}
    		
    		for(int i = 0; i < count; i++)//cassis&camellia
			{
				int PosX = chunk2X + random.nextInt(16);
				int PosY = 60 + random.nextInt(30);
				int PosZ = chunk2Z + random.nextInt(16);
				
				BiomeGenBase biome = world.getBiomeGenForCoords(PosX, PosZ);
				
				if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST) && world.getBlock(PosX, PosY - 1, PosZ) == Blocks.grass
						&& !world.isBlockNormalCubeDefault(PosX, PosY, PosZ, false))
				{
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FROZEN))
					{
						world.setBlock(PosX, PosY, PosZ, DCsAppleMilk.saplingTea, 2, 2);
					}
					else
					{
						world.setBlock(PosX, PosY, PosZ, DCsAppleMilk.saplingTea, 1, 2);
					}
				}
			}
    	}

	}

}