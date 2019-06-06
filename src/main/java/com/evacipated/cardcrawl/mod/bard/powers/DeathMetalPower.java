package com.evacipated.cardcrawl.mod.bard.powers;

import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.evacipated.cardcrawl.mod.bard.hooks.OnNoteQueuedHook;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.evacipated.cardcrawl.mod.bard.vfx.combat.ThrowNoteEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DeathMetalPower extends AbstractBardPower implements OnNoteQueuedHook
{
    public static final String POWER_ID = BardMod.makeID("DeathMetal");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DeathMetalPower(AbstractCreature owner, int damage)
    {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        type = PowerType.BUFF;
        amount = damage;
        updateDescription();
        priority = -99;
        loadRegion("deathMetal");
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractNote onNoteQueued(AbstractNote note)
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        owner,
                        DamageInfo.createDamageMatrix(amount),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );

        float stagger = 0;
        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
            if (effect instanceof ThrowNoteEffect) {
                float f = ((ThrowNoteEffect) effect).getStaggerTimer();
                if (f >= stagger) {
                    stagger = f + (Settings.FAST_MODE ? 0.05f : 0.15f);
                }
            }
        }
        AbstractDungeon.effectList.add(new ThrowNoteEffect(note, owner.hb.cX, owner.hb.cY, Settings.WIDTH, owner.hb.cY, stagger));


        return null;
    }
}
