package com.planet_ink.coffee_mud.Abilities.Spells;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;



import java.util.*;

/*
   Copyright 2000-2014 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
@SuppressWarnings({"unchecked","rawtypes"})
public class Spell_Scatter extends Spell
{
	@Override public String ID() { return "Spell_Scatter"; }
	@Override public String name(){return "Scatter";}
	@Override protected int canTargetCode(){return CAN_MOBS|CAN_ITEMS;}
	@Override public int abstractQuality(){return Ability.QUALITY_MALICIOUS;}
	@Override public int classificationCode(){return Ability.ACODE_SPELL|Ability.DOMAIN_CONJURATION;}

	private Item getItem(MOB mobTarget)
	{
		final Vector goodPossibilities=new Vector();
		final Vector possibilities=new Vector();
		for(int i=0;i<mobTarget.numItems();i++)
		{
			final Item item=mobTarget.getItem(i);
			if(item!=null)
			{
				if(item.amWearingAt(Wearable.IN_INVENTORY))
					possibilities.addElement(item);
				else
					goodPossibilities.addElement(item);
			}
		}
		if(goodPossibilities.size()>0)
			return (Item)goodPossibilities.elementAt(CMLib.dice().roll(1,goodPossibilities.size(),-1));
		else
		if(possibilities.size()>0)
			return (Item)possibilities.elementAt(CMLib.dice().roll(1,possibilities.size(),-1));
		return null;
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			if((target instanceof MOB)&&(target!=mob))
			{
				if(getItem((MOB)target)==null)
					return Ability.QUALITY_INDIFFERENT;
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, Vector commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final Vector areas=new Vector();
		if(commands.size()==0)
			areas.addElement(mob.location().getArea());
		else
		if(((String)commands.lastElement()).equalsIgnoreCase("far"))
		{
			commands.removeElementAt(commands.size()-1);
			for(final Enumeration e=CMLib.map().areas();e.hasMoreElements();)
				areas.addElement(e.nextElement());
		}
		else
		if(((String)commands.lastElement()).equalsIgnoreCase("near"))
		{
			commands.removeElementAt(commands.size()-1);
			areas.addElement(mob.location().getArea());
		}
		else
			areas.addElement(mob.location().getArea());
		final MOB mobTarget=getTarget(mob,commands,givenTarget,true,false);
		Item target=null;
		if(mobTarget!=null)
		{
			target=getItem(mobTarget);
			if(target==null)
				return maliciousFizzle(mob,mobTarget,_("<S-NAME> attempt(s) a scattering spell at <T-NAMESELF>, but nothing happens."));
		}

		List<Item> targets=new Vector();
		if(givenTarget instanceof Item)
			targets.add((Item)givenTarget);
		else
		if(target!=null)
			targets.add(target);
		else
		{
			targets=CMLib.english().fetchItemList(mob,mob,null,commands,Wearable.FILTER_ANY,true);
			if(targets.size()==0)
				mob.tell(_("You don't seem to be carrying that."));
		}

		if(targets.size()==0) return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			// it worked, so build a copy of this ability,
			// and add it to the affects list of the
			// affected MOB.  Then tell everyone else
			// what happened.
			String str=null;
			if(mobTarget==null)
				str=auto?"<S-NAME> <S-IS-ARE> enveloped in a scattering field!":"^S<S-NAME> utter(s) a scattering spell!^?";
			else
				str=auto?"<T-NAME> <T-IS-ARE> enveloped in a scattering field!":"^S<S-NAME> utter(s) a scattering spell, causing <T-NAMESELF> to resonate.^?";
			CMMsg msg=CMClass.getMsg(mob,mobTarget,this,verbalCastCode(mob,target,auto),str);
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				if(msg.value()<=0)
				{
					for(int i=0;i<targets.size();i++)
					{
						target=targets.get(i);
						msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),null);
						Room room = null;
						for(int x = 0; (x < 10) && (room == null); x++)
							room=((Area)areas.elementAt(CMLib.dice().roll(1,areas.size(),-1))).getRandomMetroRoom();
						if(mob.location().okMessage(mob,msg) && (room != null))
						{
							mob.location().send(mob,msg);
							if(msg.value()<=0)
							{
								target.unWear();
								if(target.owner() instanceof MOB)
								{
									final MOB owner=(MOB)target.owner();
									mob.location().show(owner,room,target,CMMsg.MASK_ALWAYS|CMMsg.MSG_THROW,_("<O-NAME> vanishes from <S-YOUPOSS> inventory!"));
									room.showOthers(owner,room,target,CMMsg.MASK_ALWAYS|CMMsg.MSG_THROW,_("<O-NAME> appears from out of nowhere!"));
								}
								else
								{
									mob.location().show(mob,room,target,CMMsg.MASK_ALWAYS|CMMsg.MSG_THROW,_("<O-NAME> vanishes!"));
									room.showOthers(mob,room,target,CMMsg.MASK_ALWAYS|CMMsg.MSG_THROW,_("<O-NAME> appears from out of nowhere!"));
								}
								if(!room.isContent(target))
									room.moveItemTo(target,ItemPossessor.Expire.Player_Drop,ItemPossessor.Move.Followers);
								room.recoverRoomStats();
							}
						}
					}
				}
			}
		}
		else
			return maliciousFizzle(mob,mobTarget,_("<S-NAME> attempt(s) a scattering spell, but nothing happens."));


		// return whether it worked
		return success;
	}
}


