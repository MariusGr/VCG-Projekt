package raytracer;

import light.Light;
import material.Material;
import objects.Shape;
import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;
/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Intersection {
    public Vec3 interSectionPoint;
    public Vec3 normal;
    public Ray inRay;
    public Ray outRay;
    public Shape shape;
    public float distance;
    public Boolean incoming;
    public Boolean hit;
    public int shadowCounter;

    public Intersection() {
        interSectionPoint = null;
        normal = null;
        inRay = null;
        outRay= null;
        shape = null;
        distance = -1;
        incoming = false;
        hit = false;
        shadowCounter = 0;
    }


    public Ray calculateReflecionRay() {
        return null;
    }

    public Ray calculateRefractionRay() {
        return null;
    }

    public Boolean isOutOfDistance() {
        return null;
    }



    public RgbColor getRgbColor() {
        if(!this.hit) return Raytracer.backgroundColor;      // Wenn kein Objekt getroffen wird, wird dieser Wert für den Pixel verwendet --> Hintergrundfarbe der Szene

        Light l = Raytracer.lightList.get(0);               // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                        // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(interSectionPoint).normalize();            // Vektor von Schnittpunkt zu Lichtquelle
        Vec3 direction = this.inRay.getDirection();

        Material m = this.shape.getMaterial();
        RgbColor rgb = m.getColor(l.getColor(), shape.getNormal(interSectionPoint), lVector, direction);
        rgb = rgb.add(Raytracer.ambientLight);               // Ambientes Licht addieren, wenn es sich um den Pixel eines getroffenen Objekts handelt

        while(shadowCounter!=0)
        {
            rgb.add(-0.5f,-0.5f,-0.5f);         //Im Schatten ambientes Licht
            shadowCounter--;
        }

        return rgb;
    }
}

