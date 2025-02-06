package redditrelics.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import redditrelics.config.ConfigPanel;

import java.util.ArrayList;

import static redditrelics.RedditRelicsMod.makeID;

public class NeowsScripture extends BaseRelic {
    public static final String NAME = "NeowsScripture";
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private boolean all3Relics = true;
    private boolean cardsReceived = true;


    public NeowsScripture() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        return ConfigPanel.enableNeowsScripture && bookCheck().size() >= 2;
    }

    public void onEquip() {
        //this.not3Relics = false;
        /* */
        CardCrawlGame.sound.play("EVENT_TOME");
        //CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));

        ArrayList<AbstractRelic> books = bookCheck();
        all3Relics = books.size() == 3;
        if (all3Relics) {
            cardsReceived = true;
            AbstractDungeon.bossRelicScreen.open(books);
        }
    }

    public void update() {
        super.update();
        if (!cardsReceived && !all3Relics && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            ArrayList<RewardItem> books = createReward();
            RewardItem prevReward = null;


            for (RewardItem r : books) {

                /*RewardItem item = new RewardItem(r);
                if (prevReward == null) {
                    prevReward = item;
                } else {

                    item.relicLink = prevReward;
                    prevReward = item;
                }
                item.type = RewardItem.RewardType.RELIC;*/
                AbstractDungeon.combatRewardScreen.rewards.add(r);

            }
            //AbstractDungeon.combatRewardScreen.
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[1]);
            this.cardsReceived = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }

        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("EVENT_TOME");
            //CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            this.flash();
        }
    }


    private ArrayList<AbstractRelic> bookCheck() {
        ArrayList<AbstractRelic> possibleBooks = new ArrayList();


        if (!AbstractDungeon.player.hasRelic("Necronomicon")) {
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Enchiridion")) {
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Nilry's Codex")) {
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        }
        if (possibleBooks.size() <= 0) {
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        }

        return possibleBooks;
    }

    private ArrayList<RewardItem> createReward() {
        ArrayList<AbstractRelic> possibleBooks = new ArrayList();


        if (!AbstractDungeon.player.hasRelic("Necronomicon")) {
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Enchiridion")) {
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Nilry's Codex")) {
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        }
        if (possibleBooks.size() <= 1) {
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        }
        ArrayList<RewardItem> rewards = new ArrayList();
        int choice = AbstractDungeon.miscRng.random(possibleBooks.size() - 1);
        AbstractRelic r1 = (AbstractRelic) possibleBooks.get(choice);
        possibleBooks.remove(choice);
        if (possibleBooks.size() == 0) {
            RewardItem reward1 = new RewardItem(r1);
            rewards.add(reward1);
            return rewards;
        }
        AbstractRelic r2 = (AbstractRelic) possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));

        RewardItem reward1 = new RewardItem(r1);
        RewardItem reward2 = new RewardItem(r2);

        reward1.relicLink = reward2;
        reward2.relicLink = reward1;

        rewards.add(reward1);
        rewards.add(reward2);

        return rewards;

        //return possibleBooks;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
