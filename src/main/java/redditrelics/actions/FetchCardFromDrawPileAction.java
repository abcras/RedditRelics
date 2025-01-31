package redditrelics.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class FetchCardFromDrawPileAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    //private int numberOfCards;
    private AbstractCard card;
    private boolean optional;

    public FetchCardFromDrawPileAction(AbstractCard card, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.card = card;
        this.optional = optional;
        this.amount = 1;
        //this.duration = this.startingDuration;
    }

    public FetchCardFromDrawPileAction(AbstractCard card) {
        this(card, false);
    }


    public void update() {
        if (this.duration == this.startDuration) {
            AbstractCard theRealCard = this.card;
            for (AbstractCard c : this.player.drawPile.group) {
                if (c.uuid == this.card.uuid) {
                    theRealCard = c;
                    if (this.player.hand.size() == 10) {
                        this.player.drawPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                        return;
                    } else {
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                        return;
                        //AbstractDungeon.player.hand.refreshHandLayout();
                    }
                }
            }
            //if()


            this.isDone = true;
        }

        /*if (!AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard theRealCard = this.card;

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c == this.card) {
                    theRealCard = c;
                }
            }
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.drawPile.moveToDiscardPile(theRealCard);
                AbstractDungeon.player.createHandIsFullDialog();
            } else {
                AbstractDungeon.player.drawPile.moveToHand(theRealCard, AbstractDungeon.player.drawPile);
                AbstractDungeon.player.hand.refreshHandLayout();

                AbstractDungeon.player.drawPile.moveToHand(this.card, AbstractDungeon.player.drawPile);
                AbstractDungeon.player.hand.refreshHandLayout();

            }
        }*/
        this.isDone = true;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }

}
