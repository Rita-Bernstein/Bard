package com.evacipated.cardcrawl.mod.bard.powers;

import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CovetousAuraPower extends AbstractPower
{
    public static final String POWER_ID = BardMod.makeID("CovetousAura");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CovetousAuraPower(AbstractCreature owner)
    {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        type = PowerType.BUFF;
        isTurnBased = true;
        updateDescription();
        // TODO
        loadRegion("rupture");
        //region48 = BardMod.powerAtlas.findRegion("48/reverseGravity");
        //region128 = BardMod.powerAtlas.findRegion("128/reverseGravity");
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }
}
