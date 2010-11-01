package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public interface BattleActionVisitor<R, E extends Throwable> {
    R visitMove(ActionGo action) throws E;

    R visitPosition(ActionPosition action) throws E;

    R visitReload(ActionReload action) throws E;

    R visitFire(ActionFire action) throws E;

    R visitPickup(ActionPickup action) throws E;
}
