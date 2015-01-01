package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class BloodThirstyFeature {
    public static void apply(FeatureResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, int normalAttackDamage) {
        if (attacker == null || normalAttackDamage <= 0) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        int adjAT = skill.getImpact();
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
    }
}
