package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;

public class HolyAvenger extends TwoHandedSword
{
	public String ID(){	return "HolyAvenger";}
	public HolyAvenger()
	{
		super();

		setName("the holy avenger");
		setDisplayText("a beautiful two-handed sword has been left here");
		setDescription("A two-handed sword crafted with a careful hand, and inscribed with several holy symbols.");
		secretIdentity="The Holy Avenger!  A good-only Paladin sword that casts dispel evil on its victims";
		baseEnvStats().setAbility(0);
		baseEnvStats().setLevel(20);
		baseEnvStats().setWeight(25);
		baseEnvStats().setAttackAdjustment(25);
		baseEnvStats().setDamage(18);
		baseEnvStats().setDisposition(baseEnvStats().disposition()|EnvStats.IS_GOOD|EnvStats.IS_BONUS);
		baseGoldValue=15500;
		recoverEnvStats();
		material=EnvResource.RESOURCE_MITHRIL;
		weaponType=TYPE_SLASHING;
		setRawLogicalAnd(true);
	}

	public Environmental newInstance()
	{
		return new HolyAvenger();
	}

	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;

		MOB mob=msg.source();
		if(mob.location()==null)
			return true;

		if(msg.amITarget(this))
		switch(msg.targetMinor())
		{
		case CMMsg.TYP_HOLD:
		case CMMsg.TYP_WEAR:
		case CMMsg.TYP_WIELD:
		case CMMsg.TYP_GET:
			if((!msg.source().charStats().getCurrentClass().ID().equals("Paladin"))
			||(msg.source().getAlignment()<650))
			{
				unWear();
				mob.location().show(mob,null,CMMsg.MSG_OK_ACTION,name()+" flashes and flies out of <S-HIS-HER> hands!");
				if(msg.source().isMine(this))
					ExternalPlay.drop(msg.source(),this,true,false);
				return false;
			}
			break;
		case CMMsg.TYP_DROP:
		case CMMsg.TYP_THROW:
			break;
		default:
			break;
		}
		return true;
	}

	public void executeMsg(Environmental myHost, CMMsg msg)
	{
		super.executeMsg(myHost,msg);
		if((msg.source().location()!=null)
		&&(msg.targetMinor()==CMMsg.TYP_DAMAGE)
		&&((msg.value())>0)
		&&(msg.tool()==this)
		&&(msg.target() instanceof MOB)
		&&(!((MOB)msg.target()).amDead())
		&&(((MOB)msg.target()).getAlignment()<350))
		{
			FullMsg msg2=new FullMsg(msg.source(),(MOB)msg.target(),new HolyAvenger(),CMMsg.MSG_OK_ACTION,CMMsg.MSK_MALICIOUS_MOVE|CMMsg.TYP_UNDEAD,CMMsg.MSG_NOISYMOVEMENT,null);
			if(msg.source().location().okMessage(msg.source(),msg2))
			{
				msg.source().location().send(msg.source(), msg2);
				int damage=Dice.roll(1,15,0);
				if(msg.value()>0)
					damage=damage/2;
				msg.addTrailerMsg(new FullMsg(msg.source(),(MOB)msg.target(),CMMsg.MSG_OK_ACTION,name()+" dispels evil within <T-NAME> and "+CommonStrings.standardHitWord(Weapon.TYPE_BURSTING,damage)+" <T-HIM-HER>>!"));
				FullMsg msg3=new FullMsg(msg.source(),(MOB)msg.target(),null,CMMsg.MSG_OK_VISUAL,CMMsg.MSG_DAMAGE,CMMsg.NO_EFFECT,null);
				msg3.setValue(damage);
				msg.addTrailerMsg(msg3);
			}
		}
	}

}
