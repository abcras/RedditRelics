package redditrelics.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
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
import redditrelics.cards.PaperclipField;
import redditrelics.config.ConfigPanel;

import java.lang.reflect.Type;
import java.util.function.Predicate;

import static redditrelics.RedditRelicsMod.makeID;

public class Paperclip extends BaseRelic implements CustomBottleRelic, CustomSavable<Pair<Integer,Integer>> {

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
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(),
                    2, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD,
                    false, false, false, false);
        }

    }

    @Override
    public void onUnequip() {
        if (this.card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            AbstractCard cardInDeck2 = AbstractDungeon.player.masterDeck.getSpecificCard(this.card2);
            if (cardInDeck != null && cardInDeck2 != null) {
                //cardInDeck.inBottleFlame = false;
                PaperclipField.inPaperClip.set(card, false);
                PaperclipField.inPaperClip.set(card2, false);
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

            PaperclipField.inPaperClip.set(card, true);
            PaperclipField.inPaperClip.set(card2, true);

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

    @Override
    public void onCardDraw(AbstractCard c) {

        AbstractCard cardToDraw = c.uuid == card.uuid ? this.card2 : c.uuid == card2.uuid ? this.card : null;

        if(cardToDraw != null && !hasBeenUsed) {
            this.hasBeenUsed = true;
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new FetchCardFromDrawPileAction(cardToDraw));
            this.stopPulse();
            this.grayscale = true;
        }
    }

    @Override
    public boolean canSpawn() {
        if(!ConfigPanel.enablePaperclip)
        {
            return false;
        }
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

    @Override
    public Pair<Integer,Integer> onSave() {
        return new Pair<>(AbstractDungeon.player.masterDeck.group.indexOf(card), AbstractDungeon.player.masterDeck.group.indexOf(card2));
    }

    @Override
    public Type savedType() {
        return CustomSavable.super.savedType();
    }

    @Override
    public void onLoad(Pair<Integer,Integer> flo) {

        if (flo == null) {
            return;
        }
        if (flo.getFirst() >= 0 && flo.getFirst() < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(flo.getFirst());
            if (card != null) {
                PaperclipField.inPaperClip.set(card, true);
                if (flo.getSecond() >= 0 && flo.getSecond() < AbstractDungeon.player.masterDeck.group.size()) {
                    card2 = AbstractDungeon.player.masterDeck.group.get(flo.getSecond());
                    if (card2 != null) {
                        PaperclipField.inPaperClip.set(card2, true);
                        //BottleRainField.inBottleRain.set(card, true);
                        setDescriptionAfterLoading();
                    }
                }
            }
        }

    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return PaperclipField.inPaperClip::get;
    }
}
