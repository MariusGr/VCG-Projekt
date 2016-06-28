package raytracer;

import light.Light;
import light.PointLight;
//import material.Blinn;
import material.Blinn;
import material.Phong;
//import material.Reflectionold;
<<<<<<< HEAD
=======
import material.Reflection;
import material.Refraction;
>>>>>>> origin/branch_1
import objects.Plane;
import objects.Shape;
import objects.Sphere;
import scene.Camera;
import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    public static ArrayList<Light> lightList = new ArrayList<Light>();
    public static RgbColor backgroundColor = new RgbColor(0f, 0f, 0f);
    public static RgbColor ambientLight = new RgbColor(0.1f, 0.1f, 0.1f);
    public static Shape[] shapeArray = new Shape[7];
<<<<<<< HEAD
=======
    public static int reflectionLevel = 5;
    public static RgbColor shadow = new RgbColor(0.5f,0.5f,0.5f);
>>>>>>> origin/branch_1

    public Raytracer(Window renderWindow) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene() {
        Log.print(this, "Start rendering!");

        // Camera  -----------------------------------------------------------------------
        Camera myCam = new Camera(new Vec3(0, 0, 5), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 90f, new Vec3(0, 0, 0));
        Vec3 start = myCam.getPosition();

        // Shapes Positions -----------------------------------------------------------------------
        Vec3 sphereStart1 = new Vec3(1, -2, -10);
        Vec3 sphereStart2 = new Vec3(-1, -2, -8);

        // Shapes -----------------------------------------------------------------------
        /*for (int b = 0; b <100; b++)
        {
            Vec3 sphereStart = new Vec3(-3, 3, -12);
            Vec3 sphereEnd = new Vec3(3, -3, 4);
            Vec3 hor = new Vec3(new Random().nextFloat()*3f, 0, 0);
            float bF = (float)b/100f;
            sphereEnd = sphereEnd.sub(sphereStart).multScalar(bF);
            sphereStart = sphereStart.add(sphereEnd).add(hor);

            Sphere sphere = new Sphere(0.1f, sphereStart, new Blinn(new RgbColor(0.9f, 0.6f, 0), 1, 10));
            sphere.setReflection(true);
            shapeArray[b+5] = sphere;

        }*/
<<<<<<< HEAD
        Sphere sphere1 = new Sphere (1, new Vec3(1, -2, -5), new Blinn(new RgbColor(1,0,0), 1f, 10));
        Sphere sphere2 = new Sphere (1, new Vec3(-1, -2, -5), new Blinn(new RgbColor(0,0,1), 1f, 10));
        sphere1.setReflection(true);
        sphere2.setReflection(true);

        Plane plane1 = new Plane(new Vec3(0, -3f, 0), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, 1, 0));
        Plane plane2 = new Plane(new Vec3(0, 0, -12f), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, 0, 1));
        Plane plane3 = new Plane(new Vec3(0, 3f, 0), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, -1, 0));
        Plane plane4 = new Plane(new Vec3(3f, 0, 0), new Blinn(new RgbColor(0, 1, 0), 1f, 10), new Vec3(-1, 0, 0));
        Plane plane5 = new Plane(new Vec3(-3f, 0, 0), new Blinn(new RgbColor(1, 0, 0), 1f, 10), new Vec3(1, 0, 0));
=======


        Sphere sphere1 = new Sphere(1, new Vec3(0, -1, -4), new Blinn(new RgbColor(1, 0, 0), 1f, 10), new Refraction(1f, Refraction.mCoffWater));
        Sphere sphere2 = new Sphere(1, new Vec3(-2, -2, -7), new Blinn(new RgbColor(0, 0, 1), 1f, 10), new Reflection(0.1f));

        Plane plane1 = new Plane(new Vec3(0, -3f, 0), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, 1, 0),null);
        Plane plane2 = new Plane(new Vec3(0, 0, -12f), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, 0, 1),null);
        Plane plane3 = new Plane(new Vec3(0, 3f, 0), new Blinn(new RgbColor(1, 1, 1), 1f, 10), new Vec3(0, -1, 0), null);
        Plane plane4 = new Plane(new Vec3(3f, 0, 0), new Blinn(new RgbColor(0, 1, 0), 1f, 10), new Vec3(-1, 0, 0),  null);
        Plane plane5 = new Plane(new Vec3(-3f, 0, 0), new Blinn(new RgbColor(1, 0, 0), 1f, 10), new Vec3(1, 0, 0),  null);
>>>>>>> origin/branch_1
        //plane2.setReflection(true);

        // Shape Array -----------------------------------------------------------------------
        // ACHTUNG: Darf niemals leer sein, wegen Treffererkennungs-Initialisierung! (s. unten)
        shapeArray[0] = plane4;
        shapeArray[1] = plane1;
        shapeArray[2] = plane2;
        shapeArray[3] = plane3;
        shapeArray[4] = plane5;
        shapeArray[5] = sphere1;
        shapeArray[6] = sphere2;

        // Lights -----------------------------------------------------------------------

        createLight(0, new RgbColor(0.8f, 0.8f, 0.8f), new Vec3(0f, 2.8f, -6f));

        // Alle Pixel druchlaufen...
        for (int j = 0; j < mBufferedImage.getHeight(); j++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = myCam.calculateDestination(i, j);       // Zielpunkt des Strahls ist das Äquivalent auf der Viewplane
                Ray r = new Ray(start, dest.sub(start), 200);       // Strahl, der durch den aktuellen Pixel geht

                Intersection inters = sendRay(r, null); //Strahlenberechnung für alle Objekte

                int rekursion = 20; //Anzahl der Rekursionen
                Ray refRay = r;
                //REFLEKTION
                while (inters.shape.getReflection()) //solange das vom Strahl getroffene Objekt reflektierend ist, berechne neue Reflektion
                {
                    //berechne Reflektionsstrahl
                    Vec3 l = refRay.getDirection().multScalar(-1);
                    Vec3 n = inters.shape.getNormal(inters.interSectionPoint);
                    float skalarNI = n.scalar(l)*2;
                    Vec3 zweiSkalarNIN = n.multScalar(skalarNI);
                    Vec3 refDirection = zweiSkalarNIN.sub(l);
                    refRay = new Ray(inters.interSectionPoint, refDirection, 50);
                    inters = sendRay(refRay, inters.shape); //Schnittest mit Reflektionsstrahl
                    rekursion--;
                    if (rekursion == 0) break; //wenn Rekursion abgearbeitet beende Schleife
                }

                mRenderWindow.setPixel(mBufferedImage, inters.getRgbColor(), new Vec2(i, j));     // Pixel entsprechend einfärben (inters.rgb ist backgroundColor, wenn kein Objekt getroffen)
            }
        }
        IO.saveImageToPng(mBufferedImage, "RenderBilder\\raytracing" + System.currentTimeMillis() + ".png");
    }

    private Light createLight(int _type, RgbColor _col, Vec3 _pos) {
        Light l = new PointLight(_col, _pos);
        lightList.add(l);
        return l;
    }


    public Intersection sendRay(Ray ray, Shape s)
    {
        //DISANZABFRAGE
        // Folgende Werte müssen initialisert werden, indem sie für das erste Objekt im Array geprüft werden
        Intersection inters = shapeArray[0].intersect(ray);   // wird am Ende der folgenden Schleife den rellevanten/nahesten Treffer beinhalten
        float smallestDistance = -1;                        // speichert die Distanz des bisher nahesten Treffers
        // Gab es einen Treffer? Ist dieser näher als der vorherige bzw. wenn es keinen gab bisher, setze diesen als rellevanten

        for (int k = 0; k < shapeArray.length; k++)
        {           // alle restliches Shapes durchlaufen...
            if(shapeArray[k] != s) {
                Intersection tempInters = shapeArray[k].intersect(ray);   // Treffer mit momentan betrachteter Shape prüfen
                float tempDis = tempInters.distance;                // -1 --> kein Treffer, >1 Treffer mit Distanz "distance"

                // Gab es einen Treffer? Ist dieser näher als der vorherige bzw. wenn es keinen gab bisher, setze diesen als rellevanten
                if (tempInters.hit && (tempDis < smallestDistance || smallestDistance < 0)) {
                    inters = tempInters;
                    smallestDistance = tempDis;
                }
            }
        }

        //SCHATTEN
        for (Light light : lightList) //alle Lichter der Szene durchgehen
        {
            Ray shadowRay = new Ray(inters.interSectionPoint, light.getPosition().sub(inters.interSectionPoint), 20); //Strahl von IntersectionPoint zu Licht senden
            float lightDistance = light.getPosition().sub(inters.interSectionPoint).length(); //Distanz von Licht zu IntersectionPoint
            for (int m = 0; m < shapeArray.length; m++) // alle Szenenobjekte durchgehen
            {
                if (shapeArray[m] != inters.shape) //außer aktuelles Objekt
                {
                    Intersection shadowInters = shapeArray[m].intersect(shadowRay); //Intersection zwischen Objekt und Licht testen
                    if (shadowInters.hit) {
                        if ((shadowInters.distance > 0) && (shadowInters.distance < lightDistance)) //wenn getroffenes Objekt zwischen Licht und Punkt liegt, male Schatten
                        {
                            inters.shadowCounter++; //zählen wie viele Objekte im Weg liegen
                        }
                    }
                }
            }
        }
        return inters;
    }
}