package com.dicetcg.xvnm.dicetcg.xvnm_implements;

import com.dicetcg.xvnm.dicetcg.render.GLRenderer;
import com.dicetcg.xvnm.dicetcg.render.Renderable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by xvnm on 8/18/17.
 */

public class Field extends Renderable {

    private ArrayList<Card> mUserCard;
    private ArrayList<Card> mEnemyCard;
    private float mW, mH;
    private float mX, mY;
    private GameUI.GameUIController mController;
    private Control mUserController, mEnemyController;

    public Field(GameUI.GameUIController controller) {
        mUserCard = new ArrayList<>(3);
        mUserCard.add(null);
        mUserCard.add(null);
        mUserCard.add(null);

        mEnemyCard = new ArrayList<>(3);
        mEnemyCard.add(null);
        mEnemyCard.add(null);
        mEnemyCard.add(null);

        mW = controller.getRenderer().getScreenWidth() / 6 * 5;
        mH = mW / 9 * 8;
        mX = (controller.getRenderer().getScreenWidth() - mW) / 2;
        mY = (controller.getRenderer().getScreenHeight() - mH) / 2;
        mController = controller;

        mUserController = new Control(mUserCard);
        mEnemyController = new Control(mEnemyCard);
    }

    @Override
    public void render(GLRenderer renderer) {
        for (int i = 0; i < 3; i++) {
            if (mUserCard.get(i) != null)
                mUserCard.get(i).render(renderer);
            if (mEnemyCard.get(i) != null)
                mEnemyCard.get(i).render(renderer);
        }
        super.render(renderer);
    }

    protected class Control {

        public Control(ArrayList<Card> slots) {
            mSlots = slots;
            mSlotHitboxes = new ArrayList<>();
            mSlotHitboxes.add(
                    new Touchable() {

                        @Override
                        public float getX() {
                            return Field.this.getX();
                        }

                        @Override
                        public float getY() {
                            return Field.this.getY();
                        }

                        @Override
                        public float getW() {
                            return Field.this.getW()/3;
                        }

                        @Override
                        public float getH() {
                            return Field.this.getH()/2;
                        }
                    }
            );
            mSlotHitboxes.add(
                    new Touchable() {

                        @Override
                        public float getX() {
                            return Field.this.getX() + Field.this.getW()/3;
                        }

                        @Override
                        public float getY() {
                            return Field.this.getY();
                        }

                        @Override
                        public float getW() {
                            return Field.this.getW()/3;
                        }

                        @Override
                        public float getH() {
                            return Field.this.getH()/2;
                        }
                    }
            );
            mSlotHitboxes.add(
                    new Touchable() {

                        @Override
                        public float getX() {
                            return Field.this.getX() + Field.this.getW()/3 * 2;
                        }

                        @Override
                        public float getY() {
                            return Field.this.getY();
                        }

                        @Override
                        public float getW() {
                            return Field.this.getW()/3;
                        }

                        @Override
                        public float getH() {
                            return Field.this.getH()/2;
                        }
                    }
            );
        }

        public boolean slotAvailable(int index) {
            if (mSlots.get(index) == null)
                return true;
            return false;
        }

        public void playCard(int slotNumber, MetaCard metaCard, boolean user) {
            mSlots.set(slotNumber, new Card(metaCard, mController.getRenderer().getTexture(metaCard.getName())));
            Card ref = mSlots.get(slotNumber);
            ref.setW(Field.this.getW()/3);
            ref.setH(Field.this.getH()/2);
            ref.setX(Field.this.getX() + Field.this.getW()/3 * slotNumber);
            if (user)
                ref.setY(Field.this.getY());
            else
                ref.setY(Field.this.getY() + Field.this.getH() / 2);
        }


        public int evalSlot(float x, float y) {
            int target = -1;
            for (int i = 0; i < mSlotHitboxes.size(); i++)
                if (mSlotHitboxes.get(i).checkTouch(x, y))
                    target = i;
            return target;
        }

        public float getX() {
            return Field.this.getX();
        }

        public float getY() {
            return Field.this.getY();
        }

        public float getW() {
            return Field.this.getW();
        }

        public float getH() {
            return Field.this.getH();
        }

        private ArrayList<Card> mSlots;
        private ArrayList<Touchable> mSlotHitboxes;

    }

    public void combat(boolean attack) {
        ArrayList<Card> attackerCard, defenderCard;
        if (attack) {
            attackerCard = mUserCard;
            defenderCard = mEnemyCard;
        } else {
            attackerCard = mEnemyCard;
            defenderCard = mUserCard;
        }

        // TODO Show dice animation

        int dice = (new Random()).nextInt(5) + 1;

        for (int i = 0; i < 3; i++) {
            if (attackerCard.get(i) != null) {
                Card attacker = attackerCard.get(i);
                Card oppo = defenderCard.get(i);
                int damage = attacker.getMetaCard().evalDamage(dice);
                if (oppo == null) {
                    attacker.attack(true, attack, mController.getRenderer().getScreenHeight());
                    while (attacker.isAttacking()) {
                    }
                    mController.deal(attack, damage);
                } else {
                    attacker.attack(false, attack, 0);
                    while (attacker.isAttacking()) {
                    }
                    oppo.setHP(oppo.getHP() - damage);
                    if (oppo.getHP() <= 0)
                        defenderCard.set(i, null);
                }
            }
        }
    }

    public Control getUserFieldController() {
        return mUserController;
    }

    public Control getEnemyFieldController() {
        return mEnemyController;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public float getZ() {
        return 0.1f;
    }

    @Override
    public float getW() {
        return mW;
    }

    @Override
    public float getH() {
        return mH;
    }

    @Override
    public float getB() {
        return 0.5f;
    }

}
