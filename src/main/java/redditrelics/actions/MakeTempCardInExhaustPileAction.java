package redditrelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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

public class MakeTempCardInExhaustPileAction extends AbstractGameAction {

    public static final String[] TEXT;
    private AbstractPlayer player;
    //private int numberOfCards;
    private AbstractCard card;
    private boolean optional;

    public MakeTempCardInExhaustPileAction(AbstractCard card, boolean optional) {
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.card = card;
        this.optional = optional;
        //this.amount = 1;
        //this.duration = this.startingDuration;
    }

    public MakeTempCardInExhaustPileAction(AbstractCard card) {
        this(card, false);
    }


    public void update() {
        if (this.duration == this.startDuration) {
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(card);
            group.moveToExhaustPile(this.card);
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        this.isDone = true;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
