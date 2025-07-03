package redditrelics.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import redditrelics.actions.ExhaustRandomCardInCombatAction;
import redditrelics.actions.MakeTempCardInExhaustPileAction;

import static redditrelics.RedditRelicsMod.makeID;

public class ExhaustingDefence extends BasePower {

    public static final String POWER_ID = makeID("ExhaustingDefence");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ExhaustingDefence(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }


    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {

        if (owner.isPlayer && damageAmount > 0) {
            Integer Count = AbstractDungeon.player.hand.size() + AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size();

            if (Count > 0) {
                this.addToBot(new ExhaustRandomCardInCombatAction());
                return super.onAttackedToChangeDamage(info, 0);
            }
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
