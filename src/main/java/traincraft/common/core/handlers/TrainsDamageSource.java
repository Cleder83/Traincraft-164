package traincraft.common.core.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

public class TrainsDamageSource extends DamageSource {

	public static final List<TrainsDamageSource> damageSources = new ArrayList();

	public static final TrainsDamageSource ranOver = (TrainsDamageSource) new TrainsDamageSource("ranOver", " was rolled over by a train!").setDamageBypassesArmor();
	public String deathMessage;

	public TrainsDamageSource(String damageType) {
		super(damageType);
		damageSources.add(this);
	}

	public TrainsDamageSource(String damageType, String deathMessage) {
		this(damageType);
		setDeathMessage(deathMessage);
	}

	public TrainsDamageSource setDeathMessage(String deathMessage) {
		this.deathMessage = deathMessage;
		return this;
	}
	@Override
	public DamageSource setDamageAllowedInCreativeMode() {
		return super.setDamageAllowedInCreativeMode();
	}

	/*
	 * public void registerDeathMessage() { LanguageRegistry.instance().addName("death.ranOver", "was rolled over!");//.addStringLocalization("death." + this.damageType, this.deathMessage); } */
	/**
	 * Returns the message to be displayed on player death.
	 */
	@Override
	public ChatMessageComponent getDeathMessage(EntityLivingBase living) {
		String name="";
		if(living instanceof EntityPlayer)name=((EntityPlayer)living).username;
			return ChatMessageComponent.createFromText(name + deathMessage);
	}
}