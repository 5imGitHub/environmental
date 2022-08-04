package com.teamabnormals.environmental.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CherryBlossomParticle extends TextureSheetParticle {
	private final float rotSpeed;

	private CherryBlossomParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double particleRedIn, double particleGreenIn, double particleBlueIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.quadSize *= 1.2F;
		int i = (int) (32.0D / (Math.random() * 0.8D + 0.2D));
		this.lifetime = (int) Math.max((float) i * 1.8F, 2.0F);
		this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
		this.roll = (float) Math.random() * ((float) Math.PI * 2F);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			this.yd -= 0.002F;
			this.yd = Math.max(this.yd, -0.1F);

			this.oRoll = this.roll;
			if (!this.onGround) {
				this.roll += (float) Math.PI * this.rotSpeed * 1.6F;
			} else {
				this.yd = 0.0D;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			CherryBlossomParticle particle = new CherryBlossomParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.pickSprite(this.spriteSet);
			return particle;
		}
	}
}
