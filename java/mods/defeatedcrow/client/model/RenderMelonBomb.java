package mods.defeatedcrow.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.defeatedcrow.common.entity.EntityMelonBomb;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMelonBomb extends Render
{
    private static final ResourceLocation melonTextures = new ResourceLocation("defeatedcrow:textures/entity/compressedmelon.png");

    /** instance of ModelBoat for rendering */
    protected ModelMelonBomb modelMelonBomb;

    public RenderMelonBomb()
    {
        this.shadowSize = 0.5F;
        this.modelMelonBomb = new ModelMelonBomb();
    }

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderMelonBomb(EntityMelonBomb par1EntityMelon, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        float f2 = (float)par1EntityMelon.getTimeSinceHit() - par9;
        float f3 = par1EntityMelon.getDamageTaken() - par9;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f2 > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float)par1EntityMelon.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindEntityTexture(par1EntityMelon);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelMelonBomb.render(par1EntityMelon, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getMelonTextures(EntityMelonBomb par1Entity)
    {
        return melonTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getMelonTextures((EntityMelonBomb)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMelonBomb((EntityMelonBomb)par1Entity, par2, par4, par6, par8, par9);
    }
}
