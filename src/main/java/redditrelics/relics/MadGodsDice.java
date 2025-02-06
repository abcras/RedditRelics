package redditrelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import redditrelics.actions.MakeTempCardInExhaustPileAction;
import redditrelics.config.ConfigPanel;

import java.util.ArrayList;

import static redditrelics.RedditRelicsMod.makeID;

public class MadGodsDice extends BaseRelic {

    private static final String NAME = "MadGodsDice"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int MagicNumber = 3;
    private static final int EnergyNumber = 2;


    public MadGodsDice() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        return ConfigPanel.enableMadDice;
    }

    @Override
    public void atPreBattle() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, MagicNumber, false), MagicNumber));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, MagicNumber, false), MagicNumber));
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if(p.type == AbstractPower.PowerType.DEBUFF)
            {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new GainEnergyAction(EnergyNumber));
                return;
            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EnergyNumber + DESCRIPTIONS[1] + MagicNumber + DESCRIPTIONS[2];
    }
}
