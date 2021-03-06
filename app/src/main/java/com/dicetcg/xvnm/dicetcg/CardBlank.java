package com.dicetcg.xvnm.dicetcg;

import com.dicetcg.xvnm.dicetcg.render.GLRenderer;
import com.dicetcg.xvnm.dicetcg.render.Renderable;

/**
 * Created by Knock on 2017-08-16.
 */

public class CardBlank extends Renderable {

    private float mR, mG, mB;
    private float mW, mH, W, H;
    private float mX, mY;

    CardBlank(float x, float y) {
        mX = x;        mY = y;
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
    public float getW() {
        return mW;
    }

    @Override
    public float getH() {
        return mH;
    }

    @Override
    public float getR() {
        return 1;
    }

    @Override
    public void init(GLRenderer renderer) {
        mW = renderer.getScreenWidth();
        mH = renderer.getScreenHeight();
    }
}