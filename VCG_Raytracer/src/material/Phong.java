package material;

import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Phong extends Material {
    private float n;

    public Phong(RgbColor _color, float _materialCoff, float _n) {
        super(_color, _materialCoff);
        this.n = _n;
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV, Vec3 _dir) {
        Lambert lam = new Lambert(this.color, 0.8f);
        RgbColor lRGB = lam.getColor(_iP, _normal, _lightV);
        float nlScalar =_normal.scalar(_lightV);
        Vec3 reflectionV = _normal.multScalar(nlScalar*2);
        reflectionV = reflectionV.sub(_lightV);
        RgbColor pRGB = _iP.multScalar(  (float) (materialCoff*Math.pow(_dir.scalar(reflectionV), n))   );

        return   pRGB.add(lRGB);
    }
}
