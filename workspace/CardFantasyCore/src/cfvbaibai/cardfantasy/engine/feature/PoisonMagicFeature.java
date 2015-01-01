package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class PoisonMagicFeature {
    public static void apply(SkillUseInfo skillUseInfo, FeatureResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        Skill skill = skillUseInfo.getFeature();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact();
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, skill, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackFeature((CardInfo)attacker, victim, skill, result, null);
            }
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, skill);
            } else {
                CardStatusItem status = CardStatusItem.poisoned(damage, skillUseInfo);
                ui.addCardStatus(attacker, victim, skill, status);
                victim.addStatus(status);
            }
        }
    }
}
