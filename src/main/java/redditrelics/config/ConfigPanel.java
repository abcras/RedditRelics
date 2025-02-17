package redditrelics.config;

import basemod.EasyConfigPanel;
import redditrelics.RedditRelicsMod;

public class ConfigPanel extends EasyConfigPanel {
    //see this for guidance https://github.com/daviscook477/BaseMod/wiki/Mod-Config-and-Panel
    public static boolean enablePAC = true;
    public static boolean enablePaperclip = true;

    public static boolean enableCleansing = true;

    public static boolean enableClawMold = true;
    public static int setClawMoldMagicNumber = 1;

    public static boolean enableMadDice = true;

    public static boolean enableNeowsScripture = true;

    public ConfigPanel() {

        super(RedditRelicsMod.modID, RedditRelicsMod.makeID("ConfigPanel"));

        setNumberRange("setClawMoldMagicNumber", 1, 50);
    }
}
