package com.evacipated.cardcrawl.mod.bard.actions.unique;

import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.UUID;

public class WishAction extends AbstractGameAction
{
    private UUID uuid;
    private int deckPosition = -1;

    public WishAction(UUID targetUUID)
    {
        uuid = targetUUID;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update()
    {
        if (duration == Settings.ACTION_DUR_XFAST) {
            for (int i=0; i<AbstractDungeon.player.masterDeck.group.size(); ++i) {
                if (AbstractDungeon.player.masterDeck.group.get(i).uuid == uuid) {
                    deckPosition = i;
                    break;
                }
            }
            AbstractDungeon.player.masterDeck.group.removeIf(card -> card.uuid == uuid);

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            // TODO: Any card color if player has Prismatic Shard
            tmpGroup.group = CardLibrary.getCardList(Bard.Enums.LIBRARY_COLOR);
            tmpGroup.group.removeIf(card -> card.rarity == AbstractCard.CardRarity.RARE);

            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : tmpGroup.group) {
                AbstractCard tmp = c.makeCopy();
                tmp.upgrade();
                group.addToTop(tmp);
            }
            group.sortAlphabetically(true);
            group.sortByRarity(true);
            group.sortByStatus(true);

            AbstractDungeon.gridSelectScreen.open(group, 1, false, "TODO");
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if (deckPosition >= 0) {
                AbstractDungeon.player.masterDeck.group.add(deckPosition, card.makeStatEquivalentCopy());
            } else {
                AbstractDungeon.player.masterDeck.group.add(card.makeStatEquivalentCopy());
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        }
        tickDuration();
    }
}
