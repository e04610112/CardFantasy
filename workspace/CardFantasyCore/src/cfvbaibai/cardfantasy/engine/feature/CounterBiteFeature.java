package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class CounterBiteFeature {
    public static void apply(SkillUseInfo skillUseInfo, FeatureResolver resolver, CardInfo card) throws HeroDieSignal {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card is null or dead!");
        }
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        Skill cardFeature = skillUseInfo.getFeature();
        int damage = cardFeature.getImpact();
        resolver.attackHero(card, card.getOwner(), cardFeature, damage);
        card.setUsed(skillUseInfo);
    }
}