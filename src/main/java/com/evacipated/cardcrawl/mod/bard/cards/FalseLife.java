package com.evacipated.cardcrawl.mod.bard.cards;

import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.evacipated.cardcrawl.mod.bard.notes.BlockNote;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.List;

public class FalseLife extends AbstractBardCard
{
    public static final String ID = BardMod.makeID("FalseLife");
    public static final String IMG = null;
    private static final int COST = 2;
    private static final int TEMP_HP = 10;
    private static final int BONUS_TEMP_HP = 2;
    private static final int UPGRADE_BONUS_TEMP_HP = 1;

    public FalseLife()
    {
        super(ID, IMG, COST, CardType.SKILL, Bard.Enums.COLOR, CardRarity.UNCOMMON, CardTarget.SELF);

        magicNumber = baseMagicNumber = TEMP_HP;
        magicNumber2 = baseMagicNumber2 = BONUS_TEMP_HP;
    }

    @Override
    public List<AbstractNote> getNotes()
    {
        return Collections.singletonList(new BlockNote());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBottom(new AbstractGameAction()
        {
            @Override
            public void update()
            {
                int bonusTempHP = 0;
                if (p instanceof Bard) {
                    int count = ((Bard) p).noteQueue.count(BlockNote.class);
                    bonusTempHP = count * magicNumber2;
                }
                addToTop(new AddTemporaryHPAction(p, p, magicNumber + bonusTempHP));
                isDone = true;
            }
        });

        rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();

        int count = 0;
        if (AbstractDungeon.player instanceof Bard) {
            count = ((Bard) AbstractDungeon.player).noteQueue.count(BlockNote.class);
        }
        rawDescription = DESCRIPTION;
        rawDescription += EXTENDED_DESCRIPTION[0] + count + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard()
    {
        rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber2(UPGRADE_BONUS_TEMP_HP);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new FalseLife();
    }
}
