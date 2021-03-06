package com.evacipated.cardcrawl.mod.bard.relics;

import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.List;

public class BagPipes extends AbstractBardRelic
{
    public static final String ID = BardMod.makeID("BagPipes");
    public static String NAME = "BagPipes";

    public BagPipes()
    {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, Bard.Enums.COLOR);

        NAME = name;
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        List<AbstractNote> notes = AbstractBardCard.determineNoteTypes(card);
        for (AbstractNote note : notes) {
            addToBottom(new QueueNoteAction(note));
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new BagPipes();
    }
}
