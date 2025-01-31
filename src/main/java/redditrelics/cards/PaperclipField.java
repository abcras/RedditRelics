package redditrelics.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        cls="com.megacrit.cardcrawl.cards.AbstractCard",
        method=SpirePatch.CLASS
)
public class PaperclipField
{
    public static SpireField<Boolean> inPaperClip = new SpireField<>(() -> false);

    @SpirePatch(
            cls="com.megacrit.cardcrawl.cards.AbstractCard",
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            inPaperClip.set(__result, inPaperClip.get(__instance));
            return __result;
        }
    }
}
