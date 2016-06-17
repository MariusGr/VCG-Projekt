package material;

import utils.Log;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by christopherendres on 17.06.16.
 */
public class Reflection extends Material {

    private float n;

    public Reflection(RgbColor _color, float _materialCoff) {
        super(_color, _materialCoff);
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV, Vec3 _dir) {

        _dir = _dir.multScalar(-1);
        Lambert lam = new Lambert(this.color, 1f);

        Vec3 h =  (_lightV.add(_dir).multScalar(0.5f));                   // Berechnung H Vektor

        RgbColor lRGB = lam.getColor(_iP, _normal, _lightV);
        float nlScalar =_normal.scalar(_lightV);                    //Normalenvektor mal Lichtvektor
        Vec3 reflectionV = _normal.multScalar(nlScalar*2);
        reflectionV = reflectionV.sub(_lightV);
        float alpha = (float) _normal.angle(h);
        float beta = (float) _lightV.angle(_normal);

        float m = 0.6f;                                             // Standardabweichung in der Verteilung

        // Gaußverteilung
        float d1 = (float) Math.pow(Math.E,(-(alpha/Math.pow (m,2f) ) ) ) ;

        //Beckmannverteilung
        float d2 = (float) (Math.pow(Math.E,(-(Math.pow (Math.tan(alpha)/m,2) ) ) ))/4* (float) (Math.pow(m,2)*Math.pow(Math.cos(alpha),4));

        // Selbstschattierung
        float nh = _normal.scalar(h);       //Normale mal Halbvektor
        float nv = _normal.scalar(_dir);    // Normale mal Direction
        float vh = _dir.scalar(h);          // Direction mal Halbvektor
        float nl = _normal.scalar(_lightV); // Normale mal Lichtvektor

        //Unblocked
        float g1 = 1;
        //Masking
        float g2 = 2*(nh*nv)/vh;
        //Shadowing
        float g3 = 2*(nh*nl)/vh;
       // Log.print(this, "D1:"+d1);
        //Log.print(this, "G2:"+g2);


        // Fresnelterm in Rechnung 1 da Reflektion ! Sonst Formel

        float n1 =1.5f;
        float n2 = 5f;

        float fParalel = (float) ((n2*Math.cos(alpha)-n1*Math.cos(beta))/(n2*Math.cos(alpha)+n1*Math.cos(beta)));
        float fSenkrecht = (float)((n1*Math.cos(alpha)-n2*Math.cos(beta))/(n2*Math.cos(alpha)+n1*Math.cos(beta)));

        float f = 0.5f *(float) (Math.pow(fSenkrecht,2)+Math.pow(fParalel,2));



        /*if (beta > Math.PI/2 || alpha > Math.PI/2)
        {
            return lRGB;
        }*/

        RgbColor pRGB = _iP.multScalar(  (super.materialCoff * ((d1*g1*1)/(_normal.scalar(_dir)))));


        return   pRGB.add(lRGB);
    }


}
