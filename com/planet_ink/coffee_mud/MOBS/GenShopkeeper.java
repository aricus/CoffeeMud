package com.planet_ink.coffee_mud.MOBS;

import java.util.*;
import com.planet_ink.coffee_mud.utils.*;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
public class GenShopkeeper extends StdShopKeeper
{
	public String ID(){return "GenShopkeeper";}
	private String PrejudiceFactors="";

	public GenShopkeeper()
	{
		super();
		Username="a generic shopkeeper";
		setDescription("He looks like he wants to sell something to you.");
		setDisplayText("A generic shopkeeper stands here.");
		baseEnvStats().setAbility(11); // his only off-default
	}
	public Environmental newInstance()
	{
		return new GenShopkeeper();
	}
	public boolean isGeneric(){return true;}

	public String prejudiceFactors(){return PrejudiceFactors;}
	public void setPrejudiceFactors(String factors){PrejudiceFactors=factors;}
	public String text()
	{
		if(CommonStrings.getBoolVar(CommonStrings.SYSTEMB_MOBCOMPRESS))
			miscText=Util.compressString(Generic.getPropertiesStr(this,false));
		else
			miscText=Generic.getPropertiesStr(this,false).getBytes();
		return super.text();
	}

	public void setMiscText(String newText)
	{
		super.setMiscText(newText);
		if((newText!=null)&&(newText.length()>0))
			Generic.setPropertiesStr(this,newText,false);
		baseState().setHitPoints((10*baseEnvStats().level())+Dice.roll(baseEnvStats().level(),baseEnvStats().ability(),1));
		recoverEnvStats();
		recoverCharStats();
		recoverMaxState();
		resetToMaxState();
		if(getWimpHitPoint()>0) setWimpHitPoint((int)Math.round(Util.mul(curState().getHitPoints(),.10)));
	}
	private static String[] MYCODES={"WHATISELL","PREJUDICE"};
	public String getStat(String code)
	{
		if(Generic.getGenMobCodeNum(code)>=0)
			return Generic.getGenMobStat(this,code);
		else
		switch(getCodeNum(code))
		{
		case 0: return ""+whatIsSold();
		case 1: return prejudiceFactors();
		}
		return "";
	}
	public void setStat(String code, String val)
	{ 
		if(Generic.getGenMobCodeNum(code)>=0)
			Generic.setGenMobStat(this,code,val);
		else
		switch(getCodeNum(code))
		{
		case 0: setWhatIsSold(Util.s_int(val)); break;
		case 1: setPrejudiceFactors(val); break;
		}
	}
	protected int getCodeNum(String code){
		for(int i=0;i<MYCODES.length;i++)
			if(code.equalsIgnoreCase(MYCODES[i])) return i;
		return -1;
	}
	private static String[] codes=null;
	public String[] getStatCodes()
	{
		if(codes!=null) return codes;
		String[] superCodes=Generic.GENMOBCODES;
		codes=new String[superCodes.length+MYCODES.length];
		int i=0;
		for(;i<superCodes.length;i++)
			codes[i]=superCodes[i];
		for(int x=0;x<MYCODES.length;i++,x++)
			codes[i]=MYCODES[x];
		return codes;
	}
	public boolean sameAs(Environmental E)
	{
		if(!(E instanceof GenShopkeeper)) return false;
		String[] codes=getStatCodes();
		for(int i=0;i<codes.length;i++)
			if(!E.getStat(codes[i]).equals(getStat(codes[i])))
				return false;
		return true;
	}
}
