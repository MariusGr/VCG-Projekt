package raytracer;

import light.Light;
import material.Material;
import material.RayHandling;
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
    public int level;

    public Intersection() {
        interSectionPoint = null;
        normal = null;
        inRay = null;
        outRay= null;
        shape = null;
        distance = -1;
        incoming = false;
        hit = false;
        level = 0;
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

        this.normal = shape.getNormal(interSectionPoint);
        Light l = Raytracer.lightList.get(0);               // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                        // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(interSectionPoint).normalize();            // Vektor von Schnittpunkt zu Lichtquelle
        Vec3 direction = this.inRay.getDirection();
        RayHandling tempRh = shape.getRayHandling();
        RgbColor rgb = new RgbColor(0,0,0);

        //MATERIAL OHNE REFLETKION REFRAKTION
        if (tempRh == null || tempRh.dominance < 1f) {  //Farbe berechenen ist unnötig, wenn die Reflektion/Refraktion diese eh 100% überschreibt
            Material m = this.shape.getMaterial();
            rgb = m.getColor(l.getColor(), normal, lVector, direction);

            //SCHATTEN
            // wird im nur gezeigt + berechnet, wenn Reflektion/Refraktion < 100% ist
            for (Light light : Raytracer.lightList) //alle Lichter der Szene durchgehen
            {
                Ray shadowRay = new Ray(interSectionPoint, light.getPosition().sub(interSectionPoint), 200); //Strahl von IntersectionPoint zu Licht senden
                float lightDistance = light.getPosition().sub(interSectionPoint).length(); //Distanz von Licht zu IntersectionPoint
                for (int i = 0; i < Raytracer.shapeArray.length; i++) // alle Szenenobjekte durchgehen
                {
                    Shape tempS =Raytracer.shapeArray[i];
                    if (Raytracer.shapeArray[i] != shape) //außer aktuelles Objekt
                    {
                        Intersection shadowInters = tempS.intersect(shadowRay); //Intersection zwischen Objekt und Licht testen
                        if (shadowInters.hit) {
                            if ((shadowInters.distance > 0) && (shadowInters.distance < lightDistance)) //wenn getroffenes Objekt zwischen Licht und Punkt liegt, male Schatten
                            {
                                rgb = rgb.sub(Raytracer.shadow);    // Jedes mal, wenn es einen Schatten an dieser Stelle gibt, Wert aus Raytracer abziehen
                                if (rgb.equals(RgbColor.BLACK)) return rgb;     // Wenn jetzt schon schwarz, sind weitere Berchnungen überflüssig
                            }
                        }
                    }
                }
            }
        }

        //REFLEKTION & REFRAKTION
        Intersection finalIntersection; // Am Ende findet sich hier das Intersection-Objekt, mit dem letzten rellevanten Treffer des ggf. mehrfach reflektierten/refraktierten Strahls

        //TODO bekommt man diese Berechnungen auch woanders her?
        if (tempRh != null && level < Raytracer.reflectionLevel) //falls Reflektion gewünscht wird neuer Strahl geschickt
        {
            outRay = tempRh.getOutRay(direction, normal, interSectionPoint); //Der Strahl geht von dem Objekt weiter in den Raum. Richtung & Ausgangspunkt berechnet das jeweilige Rayhandling
            finalIntersection = Raytracer.sendRay(outRay, shape);           //outRay wird auf Kollision mit anderen Objekten der Szene getestet
            finalIntersection.level = this.level+1;                         // Speicher, wie oft er jetzt bereits reflektiert wurde zwecks Einschränkung maximal möglicher Reflektionen
            rgb = rgb.add(finalIntersection.getRgbColor().multScalar(tempRh.dominance));        // je nachdem, wie stark das Objekt reflektiert/refraktiert, setzt sich die Farbe vom Material mit durch
        }

        // AMBIENTES LICHT
        rgb = rgb.add(Raytracer.ambientLight);

        return rgb;
    }
}

