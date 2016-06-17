package material;

import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Blinn extends Material {

    private float n;

    public Blinn (RgbColor _color, float _materialCoff, float _n) {
        super(_color, _materialCoff);
        this.n = _n;
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV, Vec3 _dir) {

        _dir = _dir.multScalar(-1);
        Lambert lam = new Lambert(this.color, 1f);

        Vec3 h = (_lightV.add(_dir)).normalize();                   // Berechnung H Vektor

        RgbColor lRGB = lam.getColor(_iP, _normal, _lightV);
        float nlScalar =_normal.scalar(_lightV);                    //Normalenvektor mal Lichtvektor
        Vec3 reflectionV = _normal.multScalar(nlScalar*2);
        reflectionV = reflectionV.sub(_lightV);
        float alpha = (float) _normal.angle(_lightV);
        float beta = (float) _normal.angle(h);




        if (beta > Math.PI/2 || alpha > Math.PI/2)
        {
            return lRGB;
        }
        RgbColor pRGB = _iP.multScalar(  (super.materialCoff * (float) Math.pow(_normal.scalar(h), n))  );

        return   pRGB.add(lRGB);
    }
}
