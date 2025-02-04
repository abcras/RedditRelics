package redditrelics.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.GashAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import redditrelics.config.ConfigPanel;

import static redditrelics.RedditRelicsMod.makeID;

public class ClawMold extends BaseRelic {

    private static final String NAME = "ClawMold"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    private static final AbstractCard ClawCardRef = new Claw();
    private static int magicNumber = 1;

    public ClawMold() {
        super(ID, NAME, AbstractCard.CardColor.BLUE, RARITY, SOUND);
        magicNumber = ConfigPanel.setClawMoldMagicNumber;
        setDescriptionAfterLoading();
    }
    @Override
    public boolean canSpawn() {
        return ConfigPanel.enableClawMold && CardHelper.hasCardWithID(Claw.ID);
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + magicNumber + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

        //maybe do 0 cost attacks instead of not claw (more balanced?)

        if(card.type == AbstractCard.CardType.ATTACK && !card.name.contains("Claw") ){
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //increment claw damage by 1.
            this.addToBot(new GashAction(ClawCardRef, this.magicNumber));

            //this.addToBot(new MakeTempCardInExhaustPileAction(card));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1] + magicNumber + DESCRIPTIONS[2];
    }
}