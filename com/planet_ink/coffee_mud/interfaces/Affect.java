package com.planet_ink.coffee_mud.interfaces;
import java.util.Vector;

public interface Affect extends Cloneable
{
	public int targetMajor();
	public int targetMinor();
	public int targetCode();
	public String targetMessage();

	public int sourceMajor();
	public int sourceMinor();
	public int sourceCode();
	public String sourceMessage();

	public int othersMajor();
	public int othersMinor();
	public int othersCode();
	public String othersMessage();

	public Environmental target();
	public Environmental tool();
	public MOB source();
	
	public Affect copyOf();
			

	public boolean amITarget(Environmental thisOne);
	public boolean amISource(MOB thisOne);

	public boolean wasModified();
	public void tagModified(boolean newStatus);

	public void modify(MOB source,
						Environmental target,
						Environmental tool,
						int newSourceCode,
						String sourceMessage,
						int newTargetCode,
						String targetMessage,
						int newOthersCode,
						String othersMessage);

	public Vector trailerMsgs();
	public void addTrailerMsg(Affect msg);
	
	// helpful seperator masks
	public static final int MINOR_MASK=1023;
	public static final int MAJOR_MASK=2096128;////1046528
	
	// masks for all messages
	public static final int MASK_HURT=512+1024;
	public static final int MASK_MAGIC=131072;    // the magic mask!
	public static final int MASK_DELICATE=262144; // for thief skills!
	public static final int MASK_MALICIOUS=524288;// for attacking
	public static final int MASK_CHANNEL=1048576; // for channel messages

	// action types
	public static final int ACT_GENERAL=1024;     // anything!
	public static final int ACT_HANDS=2048;       // small hand movements
	public static final int ACT_MOVE=4096;        // large body movements (travel)
	public static final int ACT_EYES=8192;        // looking and seeing
	public static final int ACT_MOUTH=16384;      // speaking and eating
	public static final int ACT_EARS=32768;       // listening to
	public static final int ACT_SOUND=65536;      // general body noises 
	
	// action target affect types
	public static final int AFF_GENERAL=1024;
	public static final int AFF_TOUCHED=2048;
	public static final int AFF_MOVEDON=4096;
	public static final int AFF_SEEN=8192;
	public static final int AFF_CONSUMED=16384;
	public static final int AFF_HEARD=32768;
	public static final int AFF_SOUNDEDAT=65536;

	// action observation types
	public static final int OTH_GENERAL=1024;
	public static final int OTH_SENSE_TOUCHING=2048;
	public static final int OTH_SENSE_MOVEMENT=4096;
	public static final int OTH_SEE_SEEING=8192;
	public static final int OTH_SENSE_CONSUMPTION=16384;
	public static final int OTH_SENSE_LISTENING=32768;
	public static final int OTH_HEAR_SOUNDS=65536;

	// minor messages
	public static final int TYP_AREAAFFECT=1;
	public static final int TYP_PUSH=2;
	public static final int TYP_PULL=3;
	public static final int TYP_RECALL=4;
	public static final int TYP_OPEN=5;
	public static final int TYP_CLOSE=6;
	public static final int TYP_PUT=7;
	public static final int TYP_GET=8;
	public static final int TYP_UNLOCK=9;
	public static final int TYP_LOCK=10;
	public static final int TYP_WIELD=11;
	public static final int TYP_GIVE=12;
	public static final int TYP_BUY=13;
	public static final int TYP_SELL=14;
	public static final int TYP_DROP=15;
	public static final int TYP_WEAR=16;
	public static final int TYP_FILL=17;
	public static final int TYP_DELICATE_HANDS_ACT=18;
	public static final int TYP_VALUE=19;
	public static final int TYP_HOLD=20;
	public static final int TYP_NOISYMOVEMENT=21;
	public static final int TYP_QUIETMOVEMENT=22;
	public static final int TYP_WEAPONATTACK=23;
	public static final int TYP_EXAMINESOMETHING=24;
	public static final int TYP_READSOMETHING=25;
	public static final int TYP_NOISE=26;
	public static final int TYP_SPEAK=27;
	public static final int TYP_CAST_SPELL=28;
	public static final int TYP_LIST=29;
	public static final int TYP_EAT=30;
	public static final int TYP_ENTER=31;
	public static final int TYP_FOLLOW=32;
	public static final int TYP_LEAVE=33;
	public static final int TYP_SLEEP=34;
	public static final int TYP_SIT=35;
	public static final int TYP_STAND=36;
	public static final int TYP_FLEE=37;
	public static final int TYP_NOFOLLOW=38;
	public static final int TYP_WRITE=39;
	public static final int TYP_FIRE=40;
	public static final int TYP_COLD=41;
	public static final int TYP_WATER=42;
	public static final int TYP_GAS=43;
	public static final int TYP_MIND=44;
	public static final int TYP_GENERAL=45;
	public static final int TYP_JUSTICE=46;
	public static final int TYP_ACID=47;
	public static final int TYP_ELECTRIC=48;
	public static final int TYP_POISON=49;
	public static final int TYP_UNDEAD=50;
	public static final int TYP_MOUNT=51;
	public static final int TYP_DISMOUNT=52;
	public static final int TYP_OK_ACTION=53;
	public static final int TYP_OK_VISUAL=54;
	public static final int TYP_DRINK=55;
	public static final int TYP_HANDS=56;
	public static final int TYP_PARALYZE=57;
	public static final int TYP_WAND_USE=58;
	public static final int TYP_SERVE=59;
	public static final int TYP_REBUKE=60;
	public static final int TYP_ADVANCE=61;
	public static final int TYP_DISEASE=62;
	public static final int TYP_DEATH=63;
	public static final int TYP_DEPOSIT=64;
	public static final int TYP_WITHDRAW=65;

	// helpful message groupings
	public static final int MSK_CAST_VERBAL=ACT_SOUND|ACT_MOUTH|MASK_MAGIC;
	public static final int MSK_CAST_MALICIOUS_VERBAL=ACT_SOUND|ACT_MOUTH|MASK_MAGIC|MASK_MALICIOUS;
	public static final int MSK_CAST_SOMANTIC=ACT_HANDS|MASK_MAGIC;
	public static final int MSK_CAST_MALICIOUS_SOMANTIC=ACT_HANDS|MASK_MAGIC|MASK_MALICIOUS;
	public static final int MSK_HAGGLE=ACT_HANDS|ACT_SOUND|ACT_MOUTH;
	public static final int MSK_CAST=MSK_CAST_VERBAL|MSK_CAST_SOMANTIC;
	public static final int MSK_CAST_MALICIOUS=MSK_CAST_MALICIOUS_VERBAL|MSK_CAST_MALICIOUS_SOMANTIC;
	public static final int MSK_MALICIOUS_MOVE=MASK_MALICIOUS|ACT_MOVE|ACT_SOUND;
	
	// all major messages
	public static final int NO_EFFECT=0;
	public static final int MSG_AREAAFFECT=ACT_GENERAL|TYP_AREAAFFECT;
	public static final int MSG_PUSH=ACT_HANDS|TYP_AREAAFFECT;
	public static final int MSG_PULL=ACT_HANDS|TYP_PULL;
	public static final int MSG_RECALL=ACT_MOUTH|ACT_SOUND|TYP_RECALL;
	public static final int MSG_OPEN=ACT_HANDS|TYP_OPEN;
	public static final int MSG_CLOSE=ACT_HANDS|TYP_CLOSE;
	public static final int MSG_PUT=ACT_HANDS|TYP_PUT;
	public static final int MSG_GET=ACT_HANDS|TYP_GET;
	public static final int MSG_UNLOCK=ACT_HANDS|TYP_UNLOCK;
	public static final int MSG_LOCK=ACT_HANDS|TYP_LOCK;
	public static final int MSG_WIELD=ACT_HANDS|TYP_WIELD;
	public static final int MSG_GIVE=ACT_HANDS|TYP_GIVE;
	public static final int MSG_BUY=MSK_HAGGLE|TYP_BUY;
	public static final int MSG_SELL=MSK_HAGGLE|TYP_SELL;
	public static final int MSG_DROP=ACT_HANDS|TYP_DROP;
	public static final int MSG_WEAR=ACT_HANDS|TYP_WEAR;
	public static final int MSG_FILL=ACT_HANDS|ACT_MOVE|ACT_SOUND|TYP_FILL;
	public static final int MSG_DELICATE_HANDS_ACT=ACT_HANDS|ACT_MOVE|MASK_DELICATE|TYP_DELICATE_HANDS_ACT;
	public static final int MSG_THIEF_ACT=ACT_HANDS|ACT_MOVE|MASK_DELICATE|TYP_JUSTICE;
	public static final int MSG_VALUE=MSK_HAGGLE|TYP_VALUE;
	public static final int MSG_HOLD=ACT_HANDS|TYP_HOLD;
	public static final int MSG_NOISYMOVEMENT=ACT_HANDS|ACT_SOUND|ACT_MOVE|TYP_NOISYMOVEMENT;
	public static final int MSG_QUIETMOVEMENT=ACT_HANDS|ACT_MOVE|TYP_QUIETMOVEMENT;
	public static final int MSG_WEAPONATTACK=ACT_HANDS|ACT_MOVE|ACT_SOUND|MASK_MALICIOUS|TYP_WEAPONATTACK;
	public static final int MSG_EXAMINESOMETHING=ACT_EYES|TYP_EXAMINESOMETHING;
	public static final int MSG_READSOMETHING=ACT_EYES|TYP_READSOMETHING;
	public static final int MSG_NOISE=ACT_SOUND|TYP_NOISE;
	public static final int MSG_SPEAK=ACT_SOUND|ACT_MOUTH|TYP_SPEAK;
	public static final int MSG_CAST_VERBAL_SPELL=MSK_CAST_VERBAL|TYP_CAST_SPELL;
	public static final int MSG_LIST=ACT_SOUND|ACT_MOUTH|TYP_LIST;
	public static final int MSG_EAT=ACT_HANDS|ACT_MOUTH|TYP_EAT;
	public static final int MSG_ENTER=ACT_MOVE|ACT_SOUND|TYP_ENTER;
	public static final int MSG_CAST_ATTACK_VERBAL_SPELL=MSK_CAST_MALICIOUS_VERBAL|TYP_CAST_SPELL;
	public static final int MSG_LEAVE=ACT_MOVE|ACT_SOUND|TYP_LEAVE;
	public static final int MSG_SLEEP=ACT_MOVE|TYP_SLEEP;
	public static final int MSG_SIT=ACT_MOVE|TYP_SIT;
	public static final int MSG_STAND=ACT_MOVE|TYP_STAND;
	public static final int MSG_FLEE=ACT_MOVE|ACT_SOUND|TYP_FLEE;
	public static final int MSG_CAST_SOMANTIC_SPELL=MSK_CAST_SOMANTIC|TYP_CAST_SPELL;
	public static final int MSG_CAST_ATTACK_SOMANTIC_SPELL=MSK_CAST_MALICIOUS_SOMANTIC|TYP_CAST_SPELL;
	public static final int MSG_CAST=MSK_CAST|TYP_CAST_SPELL;
	public static final int MSG_CAST_MALICIOUS=MSK_CAST_MALICIOUS|TYP_CAST_SPELL;
	public static final int MSG_OK_ACTION=ACT_SOUND|ACT_GENERAL|TYP_OK_ACTION;
	public static final int MSG_OK_VISUAL=ACT_GENERAL|TYP_OK_VISUAL;
	public static final int MSG_DRINK=ACT_HANDS|ACT_MOUTH|TYP_DRINK;
	public static final int MSG_HANDS=ACT_HANDS|TYP_HANDS;
	public static final int MSG_FOLLOW=ACT_GENERAL|TYP_FOLLOW;
	public static final int MSG_NOFOLLOW=ACT_GENERAL|TYP_NOFOLLOW;
	public static final int MSG_WRITE=ACT_HANDS|TYP_WRITE;
	public static final int MSG_MOUNT=ACT_MOVE|ACT_SOUND|TYP_MOUNT;
	public static final int MSG_DISMOUNT=ACT_MOVE|ACT_SOUND|TYP_DISMOUNT;
	public static final int MSG_SERVE=ACT_MOUTH|ACT_SOUND|TYP_SERVE;
	public static final int MSG_REBUKE=ACT_MOUTH|ACT_SOUND|TYP_REBUKE;
	public static final int MSG_ADVANCE=ACT_MOVE|ACT_SOUND|MASK_MALICIOUS|TYP_ADVANCE;
	public static final int MSG_DEATH=ACT_SOUND|ACT_GENERAL|TYP_DEATH;
	public static final int MSG_WITHDRAW=ACT_HANDS|TYP_WITHDRAW;
	public static final int MSG_DEPOSIT=ACT_HANDS|TYP_DEPOSIT;
}

