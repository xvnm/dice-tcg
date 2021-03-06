package com.dicetcg.xvnm.dicetcg.xvnm_implements;

import android.view.MotionEvent;

import com.dicetcg.xvnm.dicetcg.MainActivity;
import com.dicetcg.xvnm.dicetcg.render.GLRenderer;
import com.dicetcg.xvnm.dicetcg.render.Renderable;

/**
 * Created by xvnm on 8/17/17.
 */

public class MainUI extends Renderable implements UI {

    public MainUI(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void start() {
        mActivity.getRenderer().clearRenderables();
        mActivity.getRenderer().registerRenderable(this);

        button = new Touchable() {
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
                return 0;
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
            public void init(GLRenderer renderer) {
                float scale = renderer.getScreenWidth() / 100;
                mW = scale * 50;
                mH = mW / 3;

                mX = renderer.getScreenWidth()/2 - mW/2;
                mY = renderer.getScreenHeight()/2 - mH/2;
            }

            private float mX, mY;
            private float mW, mH;

        };
        button.renderTexture(true);
        mFader = null;
    }

    private int mStartTex = -1;

    @Override
    public void stop() {
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (mFader == null && button.checkTouch(event.getX(), mH - event.getY())) {
            mFader = new Fader();
            mFader.start(500, true);
        }
        return false;
    }

    @Override
    public float getZ() {
        return -0.1f;
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
        return 0.1f;
    }

    @Override
    public float getG() {
        return 0.1f;
    }

    @Override
    public float getB() {
        return 0.4f;
    }

    @Override
    public void init(GLRenderer renderer) {
        mW = renderer.getScreenWidth();
        mH = renderer.getScreenHeight();
        renderTexture(true);
        setTexture(renderer.getTexture("title"));

        button.init(renderer);
    }

    @Override
    public void render(GLRenderer renderer) {
        if (mFader != null && !mFader.isActive()) {
            mActivity.setCurrentUI(1);
            return;
        } else if (mFader == null) {
            renderer.setFade(1.0f);
        }

        super.render(renderer);
        mStartTex = renderer.getTexture("start");
        button.setTexture(mStartTex);
        button.render(renderer);
        if (mFader != null)
            mFader.render(renderer);
    }

    private MainActivity mActivity;
    private float mW, mH;
    private Touchable button;
    private Fader mFader;

}
