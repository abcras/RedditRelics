package redditrelics.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import redditrelics.config.ConfigPanel;

import java.util.ArrayList;

import static redditrelics.RedditRelicsMod.makeID;

public class Tuner extends BaseRelic {
    public static final String NAME = "Tuner";
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.


    public Tuner() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        return ConfigPanel.enableNeowsScripture /*&& bookCheck().size() >= 2*/;
    }

    public void onEquip() {
        //this.not3Relics = false;
        /* */
        /*CardCrawlGame.sound.play("EVENT_TOME");*/
        //CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));

        /*ArrayList<AbstractRelic> books = bookCheck();
        all3Relics = books.size() == 3;
        if (all3Relics) {
            cardsReceived = true;
            AbstractDungeon.bossRelicScreen.open(books);
        }*/
    }

    /*
                //I don't think this works: targetCard.setCostForTurn(2);
            // because the action gets the value of the current energy use instead of the cost
            // which makes sense since enlightenment nor bullet time works on these cards
     */
    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.cost == -1)
        {
            //# if it is X cost

            //todo as simple as possible
            // capture the card used, and the action then spawn the card choices
            // make the player choose from up to their energy (or until 10 options have been generated) of incremented energy costs for the card
            // this would be: X, 0, 1, 2, 3, 4, 5, 6, 7, 8 (see miracle code for inspiration)
            // the choice screen should have the second description index [1] as text (not possible it seems)
            // then after their choice calculate the difference between their choice and their current energy
            // that difference is stored as a variable
            // then the energy is set to their chosen amount
            // then after the use restore the energy
            // visually I think the numbers should be 2 higher when Chemical X is in play

            //this.ene
            int curEnergy = AbstractDungeon.player.energy.energy;

            ArrayList<AbstractCard> choices = GenerateCardOptions(curEnergy, targetCard);




            //create custom action here that wraps around ChooseOneAction?
            this.addToTop(new ChooseOneAction(choices));

            int targetEnergyFromChoice = AbstractDungeon.player.energy.energy;

            //targetCard.oncho

        }

        super.onUseCard(targetCard, useCardAction);
    }

    // for ChooseOneAction to work the cards have to implement the following function
    // I have to inject a ChooseOneAction, that modifies a shared object that I can then read??????????
    // because otherwise I will have to inject choose this option that calls on use but that then modifies the energy cost to be the chose value yikes

    // alternatively we generate cards that have an on choose method with the choice of cost that then set the energy as curEnergy - choice
    //that way we fucking ball we just read new energy level and cal difference and set accordingly
    /*
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK, true)));
        this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    }
     */


    public ArrayList<AbstractCard> GenerateCardOptions(int currentEnergy, AbstractCard card){
        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>(Math.min(currentEnergy, 10));;

        for (int i = 0; i < cards.size(); ++i) {
            AbstractCard choiceCard = card.makeSameInstanceOf();
            choiceCard.cost = i-1;
            cards.add(choiceCard);
        }

        return cards;
    }

    public void update() {
        super.update();

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
