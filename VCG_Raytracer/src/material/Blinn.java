package material;

import utils.Log;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Blinn extends Material {

    private float n;
    private static float maxAngle = (float) Math.toRadians(90);

    public Blinn (RgbColor _color, float _materialCoff, float _n) {
        super(_color, _materialCoff);
        this.n = _n;
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV, Vec3 _dir) {

        _dir = _dir.multScalar(-1);
        Lambert lam = new Lambert(this.color, 1f);

        Vec3 h = (_lightV.add(_dir)).multScalar(0.5f).normalize();                   // Berechnung H Vektor

        RgbColor lRGB = lam.getColor(_iP, _normal, _lightV);
        float alpha = (float) _normal.angle(_lightV);
        float beta = (float) _dir.angle(h);

        if (beta > maxAngle || alpha > maxAngle)
        {
            return lRGB;
        }
        RgbColor bRGB = _iP.multScalar(  (super.materialCoff * (float) Math.pow(_normal.scalar(h), n))  );

        RgbColor rgbcolor =  bRGB.add(lRGB);
        return  rgbcolor;
    }
}
