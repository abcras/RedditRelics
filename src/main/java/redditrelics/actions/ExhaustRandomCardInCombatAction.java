package redditrelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class ExhaustRandomCardInCombatAction extends AbstractGameAction {

    public static final String[] TEXT;
    private AbstractPlayer player;
    private AbstractCard card;

    private boolean hasExhaustedCard = false;

    public ExhaustRandomCardInCombatAction() {
        this.actionType = ActionType.EXHAUST;
        this.duration = this.startDuration = Settings.ACTION_DUR_MED;
        this.player = AbstractDungeon.player;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            ArrayList<AbstractCard> list = new ArrayList<>();

            list.addAll(player.hand.group);

            list.addAll(player.discardPile.group);

            list.addAll(player.drawPile.group);

            card = list.get(cardRandomRng.random(list.size() - 1));

            if (player.hand.contains(card)) {
                player.hand.removeCard(card);
            } else if (player.discardPile.contains(card)) {
                player.discardPile.removeCard(card);
            } else if (player.drawPile.contains(card)) {
                player.drawPile.removeCard(card);
            }


            AbstractDungeon.player.limbo.addToBottom(card);

            card.target_x = (float)Settings.WIDTH / 2.0F;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.setAngle(0, false);

            for(AbstractRelic r : AbstractDungeon.player.relics) {
                r.onExhaust(card);
            }

            for(AbstractPower p : AbstractDungeon.player.powers) {
                p.onExhaust(card);
            }


            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (this.duration <= this.startDuration/2 && !hasExhaustedCard) {
            hasExhaustedCard = true;
            AbstractDungeon.player.limbo.moveToExhaustPile(card);
        }
        this.tickDuration();
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
