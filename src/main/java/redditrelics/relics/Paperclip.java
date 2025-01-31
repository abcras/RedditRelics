package redditrelics.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import redditrelics.actions.*;

import static redditrelics.RedditRelicsMod.makeID;


public class Paperclip extends BaseRelic {

    private boolean cardSelected = true;
    public AbstractCard card = null;
    public AbstractCard card2 = null;

    public boolean hasBeenUsed = false;

    private static final String NAME = "Paperclip"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    public Paperclip() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        if (AbstractDungeon.player.masterDeck.getPurgeableCards().getAttacks().size() > 0) {
            this.cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }

    }

    @Override
    public void onUnequip() {
        if (this.card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null) {
                cardInDeck.inBottleFlame = false;
            }
        }

    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            this.cardSelected = true;
            this.card = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            this.card2 = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1);
            /*this.card.inBottleFlame = true;
            this.card2.inBottleFlame = true;*/
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[3] + FontHelper.colorString(this.card.name, "y")
                + this.DESCRIPTIONS[4] + FontHelper.colorString(this.card2.name, "y")
                + this.DESCRIPTIONS[5];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    /*@Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }*/

    @Override
    public void atBattleStartPreDraw() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if(c == card2)
            {
                AbstractDungeon.player.drawPile.group.remove(c);
                return;
            }
        }
        super.atBattleStartPreDraw();
    }

    /*@Override
    public void onEnterRoom(AbstractRoom room) {
        room.combatEvent
        super.onEnterRoom(room);
    }*/

    @Override
    public void onCardDraw(AbstractCard c) {

        /*AbstractCard cardToDraw = c == card ? this.card2 : c == card2 ? this.card : null;

        if(cardToDraw != null && !hasBeenUsed) {
            this.hasBeenUsed = true;
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            this.stopPulse();
            this.grayscale = true;

            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                AbstractDungeon.player.createHandIsFullDialog();
            } else {
                AbstractDungeon.player.drawPile.moveToHand(cardToDraw, AbstractDungeon.player.drawPile);
            }
        }*/
        if (this.card == c && !this.hasBeenUsed) {

            this.hasBeenUsed = true;
            this.flash();
            /*this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new MakeTempCardInHandAction(this.card2.makeSameInstanceOf(), 1));*/
            /*this.addToTop(new ExhaustSpecificCardAction(this.card2, AbstractDungeon.player.hand));
            this.addToTop(new ExhaustSpecificCardAction(this.card2, AbstractDungeon.player.drawPile));
            this.addToTop(new ExhaustSpecificCardAction(this.card2, AbstractDungeon.player.discardPile));*/


            this.addToBot(new FetchCardFromDrawPileAction(card2));
            this.grayscale = true;

            /*if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                AbstractDungeon.player.createHandIsFullDialog();
            } else {
                AbstractDungeon.player.drawPile.moveToHand(card2, AbstractDungeon.player.drawPile);
            }*/
        }
        if (this.card2 != c && !this.hasBeenUsed) {
            this.hasBeenUsed = true;
            this.flash();
            /*this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new MakeTempCardInHandAction(this.card.makeSameInstanceOf(), 1));*/
            /*this.addToTop(new ExhaustSpecificCardAction(this.card, AbstractDungeon.player.hand));
            this.addToTop(new ExhaustSpecificCardAction(this.card, AbstractDungeon.player.drawPile));
            this.addToTop(new ExhaustSpecificCardAction(this.card, AbstractDungeon.player.discardPile));*/

            this.addToBot(new FetchCardFromDrawPileAction(card));
            this.grayscale = true;


            /*
            //this.addToTop(new );
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                AbstractDungeon.player.createHandIsFullDialog();
            } else {
                AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
            }*/
        }
    }

    @Override
    public boolean canSpawn() {
        int i = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity != AbstractCard.CardRarity.BASIC) {
                i++;
                if (i >= 2)
                    return true;
            }
        }

        return false;
    }
@Override
    public void onVictory() {
        this.hasBeenUsed = false;
    }
    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }
}
