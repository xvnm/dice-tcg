package com.dicetcg.xvnm.dicetcg.xvnm_implements;

import com.dicetcg.xvnm.dicetcg.render.Renderable;

/**
 * Created by xvnm on 8/17/17.
 */

public class Touchable extends Renderable {

    boolean checkTouch(float x, float y) {
        if ((x >= getX() && x <= getX() + getW())
                && (y >= getY() && y <= getY() + getH()))
            return true;
        return false;
    }

}
