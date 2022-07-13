package com.huskytacodile.alternacraft.entities;

import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.huskytacodile.alternacraft.config.AlternacraftConfig;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.util.ModSoundEvents;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class AlioramusEntity extends TamableAnimal implements IAnimatable, PlayerRideable {
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
			SynchedEntityData.defineId(AlioramusEntity.class, EntityDataSerializers.INT);
	private AnimationFactory factory = new AnimationFactory(this);
	private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BOOLEAN);
	@SuppressWarnings("unused")
	private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME,DATA_SADDLE_ID);
	private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.INT);
	public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
		EntityType<?> entitytype = p_30437_.getType();
		return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT
				|| entitytype == EntityType.COW || entitytype == EntityType.CHICKEN || entitytype == EntityType.PIG ||
				entitytype == ModEntityTypes.JPSPINO.get()||
				entitytype == ModEntityTypes.OXALAIA.get()||
				entitytype == ModEntityTypes.TYRANNOSAURUS.get()||
				entitytype == ModEntityTypes.YUTYRANNUS.get()||
				entitytype == ModEntityTypes.CARCHA.get()||
				entitytype == ModEntityTypes.BARYONYX.get()||
				entitytype == ModEntityTypes.BARYONYX_GEN2.get()||
				entitytype == ModEntityTypes.TYLOSAURUS.get()||
				entitytype == ModEntityTypes.MOSASAURUS.get()||
				entitytype == ModEntityTypes.CERATOSUCHOPS.get()||
				entitytype == ModEntityTypes.INDOMINUS.get()||
				entitytype == ModEntityTypes.INDORAPTOR.get()||
				entitytype == ModEntityTypes.ALLOSAURUS.get()||
				entitytype == ModEntityTypes.ACRO.get()||
				entitytype == ModEntityTypes.SCORPIUS.get()||
				entitytype == ModEntityTypes.GIGA.get()||
				entitytype == ModEntityTypes.ALTERNASAURUS.get();
	};
	private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(AlioramusEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NATURAL_SITTING = SynchedEntityData.defineId(AlioramusEntity.class, EntityDataSerializers.BOOLEAN);
	
	@SuppressWarnings("deprecation")
	protected AlioramusEntity(EntityType<? extends TamableAnimal> p_i48575_1_, Level p_i48575_2_) {
		super(p_i48575_1_, p_i48575_2_);
		this.maxUpStep = 1.0F;
		this.setTame(false);
	}
	@SuppressWarnings("deprecation")
	public boolean isFood(ItemStack p_70877_1_) {
		Item item = p_70877_1_.getItem();
		return item.isEdible() && item.getFoodProperties().isMeat();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
		this.entityData.define(ASLEEP, false);
		this.entityData.define(NATURAL_SITTING, false);
	}
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
		GenderVariant variant = Util.getRandom(GenderVariant.values(), this.random);
		setVariant(variant);
		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
	}
	private void setVariant(GenderVariant variant) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putBoolean("IsAsleep", this.isAsleep());
		tag.putBoolean("NaturallySitting", this.isNaturallySitting());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_21815_) {
		super.readAdditionalSaveData(p_21815_);
		this.entityData.set(DATA_ID_TYPE_VARIANT, p_21815_.getInt("Variant"));
		this.entityData.set(ASLEEP, p_21815_.getBoolean("IsAsleep"));
		this.entityData.set(NATURAL_SITTING, p_21815_.getBoolean("NaturallySitting"));
	}
	public IVariant getVariant() {
		return GenderVariant.byId(this.getTypeVariant() & 255);
	}

	private int getTypeVariant() {
		return this.entityData.get(DATA_ID_TYPE_VARIANT);
	}

	public boolean isAsleep() {
		return this.entityData.get(ASLEEP);
	}

	private void setAsleep(boolean isAsleep) {
		this.entityData.set(ASLEEP, isAsleep);
	}
	
	public boolean isNaturallySitting() {
		return this.entityData.get(NATURAL_SITTING);
	}
	
	private void setNaturallySitting(boolean isSitting) {
		this.entityData.set(NATURAL_SITTING, isSitting);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return this.isAsleep() ? null : ModSoundEvents.ALIO_AMBIENT.get();
	}


	@Override
	protected SoundEvent getDeathSound()
	{
		return ModSoundEvents.ALIO_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return ModSoundEvents.ALIO_HURT.get();
	}
	public boolean doHurtTarget(Entity p_70652_1_) {
		boolean flag = super.doHurtTarget(p_70652_1_);
		if (flag) {
			this.doEnchantDamageEffects(this, p_70652_1_);
		}

		return flag;
	}


	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alio.walk", true));
			return PlayState.CONTINUE;
		}

		if (this.isAggressive() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alio.attack", true));
			return PlayState.CONTINUE;
		}

		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alio.idle", true));

		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<AlioramusEntity>
		(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 72.00D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.FOLLOW_RANGE, 15.0D)
				.add(Attributes.ATTACK_DAMAGE, 8.0D);
	}
	
	public void aiStep() {
		super.aiStep();
		if (this.isAsleep() || this.isNaturallySitting()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		}
	}
	
	@SuppressWarnings("deprecation")
	public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		Item item = itemstack.getItem();
		if (this.level.isClientSide) {
			boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item == ModItems.TOTEM_OF_HUGO.get() && !this.isTame();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;

		} else {
			if (this.isTame()) {
				if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
					if (!p_230254_1_.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					this.heal((float)item.getFoodProperties().getNutrition());
					return InteractionResult.SUCCESS;
				}
				p_230254_1_.startRiding(this);
			} else if (item == Items.NETHERITE_SWORD && !this.isOnFire()) {
				if (!p_230254_1_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
					this.tame(p_230254_1_);
					this.navigation.stop();

					this.setTarget((LivingEntity)null);
					this.setOrderedToSit(true);
					this.level.broadcastEntityEvent(this, (byte)7);
				} else {
					this.level.broadcastEntityEvent(this, (byte)6);
				}

				return InteractionResult.SUCCESS;
			}

			return super.mobInteract(p_230254_1_, p_230254_2_);

		}

	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	}

	@Nullable
	public Entity getControllingPassenger() {
		return this.getFirstPassenger();
	}

	public boolean canBeControlledByRider() {
		Entity entity = this.getControllingPassenger();
		if (!(entity instanceof Player)) {
			return false;
		} else {
			Player playerentity = (Player)entity;
			return playerentity.getMainHandItem().getItem() == Items.NETHERITE_SWORD || playerentity.getOffhandItem().getItem() == Items.NETHERITE_SWORD;
		}
	}

	public float getSteeringSpeed() {
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.75F;
	}

	public void travel(Vec3 pTravelVector) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider()) {
				LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
				this.setYRot(livingentity.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot(livingentity.getXRot() * 0.5F);
				this.setRot(this.getYRot(), this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}

				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vec3((double)f, pTravelVector.y, (double)f1));
				} else if (livingentity instanceof Player) {
					this.setDeltaMovement(Vec3.ZERO);
				}

				this.calculateEntityAnimation(this, false);
				this.tryCheckInsideBlocks();
			} else {
				super.travel(pTravelVector);
			}
		}
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return super.getDismountLocationForPassenger(pLivingEntity);
		} else {
			int[][] aint = DismountHelper.offsetsForDirection(direction);
			BlockPos blockpos = this.blockPosition();
			BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

			for(Pose pose : pLivingEntity.getDismountPoses()) {
				AABB axisalignedbb = pLivingEntity.getLocalBoundsForPose(pose);

				for(int[] aint1 : aint) {
					blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
					double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
					if (DismountHelper.isBlockFloorValid(d0)) {
						Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutable, d0);
						if (DismountHelper.canDismountTo(this.level, pLivingEntity, axisalignedbb.move(vec3))) {
							pLivingEntity.setPose(pose);
							return vec3;
						}
					}
				}
			}

			return super.getDismountLocationForPassenger(pLivingEntity);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new AlioramusEntity.DinoSittingGoal(this));
		this.goalSelector.addGoal(4, new AlioramusEntity.SleepingRandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new AlioramusEntity.NocturnalSleepGoal(this));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.NETHERITE_SWORD), false));
		this.goalSelector.addGoal(0,new RandomSwimmingGoal(this,0,1));
		this.goalSelector.addGoal(2, new FloatGoal(this));
		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
	}
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return null;
	}

	public class NocturnalSleepGoal extends Goal {
		public AlioramusEntity entity;

		public NocturnalSleepGoal(AlioramusEntity sleeper) {
			super();
			this.entity = sleeper;
		}

		@Override
		public boolean canUse() {
			Level world = entity.level;
			if (AlternacraftConfig.sleepingAi = true && world.getDayTime() >= 0 && world.getDayTime() <= 12000 && entity.getLastHurtByMob() == null && entity.getTarget() == null && !entity.isTame() && !entity.isInPowderSnow && !entity.isInWater()) {
				return true;
			} else return false;
		}

		@Override
		public boolean canContinueToUse() {
			Level world = entity.level;
			if (world.getDayTime() >= 12000 && world.getDayTime() < 24000 || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.isTame() || entity.isInPowderSnow || entity.isInWater()) {
				stop();
				return false;
			} else return true;
		}

		@Override
		public void start() {
			entity.setAsleep(true);
			entity.setNaturallySitting(false);
			entity.getNavigation().stop();
		}

		public void tick() {
			super.tick();
			Level world = entity.level;
			if (world.getDayTime() >= 12000 && world.getDayTime() < 24000 || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.isTame() || entity.isInPowderSnow || entity.isInWater()) {
				stop();
			}
		}

		@Override
		public void stop() {
			entity.setAsleep(false);
		}
	}

	public class SleepingRandomLookAroundGoal extends RandomLookAroundGoal {

		AlioramusEntity entity;

		public SleepingRandomLookAroundGoal(AlioramusEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public boolean canUse() {
			return super.canUse() && !entity.isAsleep();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && !entity.isAsleep();
		}

	}
	
	public class DinoSittingGoal extends Goal {
		AlioramusEntity entity;
		
		public DinoSittingGoal(AlioramusEntity sitter) {
			super();
			this.entity = sitter;
		}
		
		@Override
		public boolean canUse() {
			if (!entity.isAsleep() && !entity.isInWater() && !entity.isInPowderSnow && !entity.isTame() && entity.getLastHurtByMob() == null && entity.getTarget() == null && entity.getRandom().nextInt(750) == 0) {
				return true;
			} else return false;
		}
		
		@Override
		public boolean canContinueToUse() {
			if (entity.isAsleep() || entity.isInWater() || entity.isInPowderSnow || entity.isTame() || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.getRandom().nextInt(750) == 0) {
				stop();
				return false;
			} else return true;
		}
		
		public void tick() {
			super.tick();
			if (entity.isAsleep() || entity.isInWater() || entity.isInPowderSnow || entity.isTame() || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.getRandom().nextInt(750) == 0) {
				stop();
			}
		}
		
		@Override
		public void start() {
			entity.setNaturallySitting(true);
			entity.getNavigation().stop();
		}
		
		@Override
		public void stop() {
			entity.setNaturallySitting(false);
		}
			
	}

}