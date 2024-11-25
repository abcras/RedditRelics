package redditrelics.relics;



/*
public class UnstableDevice extends BaseRelic {

    private static final String NAME = "UnstableDevice"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    private static boolean usedAttackThisCombat = false;
    private static boolean usedSkillThisCombat = false;


    public UnstableDevice() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void atPreBattle() {
        usedAttackThisCombat = false;
        usedSkillThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }


    @Override
    public void atTurnStart() {
        if (!usedSkillThisCombat || !usedAttackThisCombat) {
            this.pulse = true;
        }

        if (!usedAttackThisCombat) {
            this.flash();

            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }

        if (!usedSkillThisCombat) {
            this.flash();
            //give dex
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        }
    }


    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        //super.onUseCard(targetCard, useCardAction);

        if (targetCard.type == AbstractCard.CardType.ATTACK && !usedAttackThisCombat) {
            usedAttackThisCombat = true;
            this.pulse = false;
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

        if(targetCard.type == AbstractCard.CardType.SKILL && !usedSkillThisCombat) {
            usedSkillThisCombat = true;
            this.pulse = false;
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

        if (usedSkillThisCombat && usedAttackThisCombat) {
            this.pulse = false;
            this.grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
    @Override
    public void onVictory() {
        this.pulse = false;
        usedAttackThisCombat = false;
        usedSkillThisCombat = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}
*/