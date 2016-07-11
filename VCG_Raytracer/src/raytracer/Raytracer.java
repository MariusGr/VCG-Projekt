package raytracer;

import light.Light;
import light.PointLight;
//import material.Blinn;
import material.*;
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
    public static ArrayList<Light> lightList = new ArrayList<Light>();      // Liste aller Lichter der Szene
    public static RgbColor backgroundColor = new RgbColor(0f, 0f, 0f);      // Hintergrundfarbe
    public static RgbColor ambientLight = new RgbColor(0.1f, 0.1f, 0.1f);   // ambientes Licht
    public static Shape[] shapeArray = new Shape[7];//new Shape[34];        // maximal mögliche Shapes
    public static int reflectionLevel = 5;                                  // maximal mögliche Reflektionen
    public static RgbColor shadow = new RgbColor(0.5f,0.5f,0.5f);           // Farbwert wird subtrahiert bei schattierten Bereichen

    public Raytracer(Window renderWindow) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene() {
        Log.print(this, "Start rendering!");

        // Camera  -----------------------------------------------------------------------
        Camera myCam = new Camera(  new Vec3(0, 0, 5),      // Position
                                    new Vec3(0, 0, -1),     // Look-At
                                    new Vec3(0, 1, 0),      // Up
                                    90f,                    // Öffnungswinkel
                                    new Vec3(0, 0, 0)       // Center of Interest
        );
        Vec3 start = myCam.getPosition();


        //szene1();


        // Shapes -----------------------------------------------------------------------

        // SPHERES
        Sphere sphere1 = new Sphere(new Vec3(1f, 1f, 1f),                 // Skalierung
                                    new Vec3(1, -2f, -6),                        // Position
                                    new Blinn(new RgbColor(1, 0, 0), 1f, 10),   // Material
                                    new Refraction(1f, Refraction.mCoffGlass)   // Refraktion/Reflektion
        );
        Sphere sphere2 = new Sphere(new Vec3(1.0f, 1.0f, 1.0f),                 // Skalierung
                                    new Vec3(-1.5f, -2, -11),                   // Position
                                    new Blinn(new RgbColor(0, 0, 1), 1f, 10),   // Material
                                    new Reflection(1f)                        // Refraktion/Reflektion
        );

        // Planes

        // Boden
        Plane plane1 = new Plane(   new Vec3(0, -3f, 0),                        // Position
                                    new Blinn(new RgbColor(1, 1, 1), 1f, 10),   // Material
                                    new Vec3(0, 1, 0),                          // Normale
                                    null                                        // Refraktion/Reflektion
        );

        // gegenüber
        Plane plane2 = new Plane(   new Vec3(0, 0, -14f),                        // Position
                                    new Blinn(new RgbColor(1, 1, 1), 1f, 10),   // Material
                                    new Vec3(0, 0, 1),                          // Normale
                new Reflection(1f)                                           // Refraktion/Reflektion
        );

        // Decke
        Plane plane3 = new Plane(   new Vec3(0, 3f, 0),                        // Position
                                    new Blinn(new RgbColor(1, 1, 1), 1f, 10),   // Material
                                    new Vec3(0, -1, 0),                          // Normale
                                    null                                        // Refraktion/Reflektion
        );

        // rechts
        Plane plane4 = new Plane(   new Vec3(3f, 0, 0),                        // Position
                                    new Blinn(new RgbColor(0, 1, 0), 1f, 10),   // Material
                                    new Vec3(-1, 0, 0),                          // Normale
                                    null                                        // Refraktion/Reflektion
        );

        // links
        Plane plane5 = new Plane(   new Vec3(-3f, 0, 0),                        // Position
                                    new Blinn(new RgbColor(1, 0, 0), 1f, 10),   // Material
                                    new Vec3(1, 0, 0),                           // Normale
                                    null                                        // Refraktion/Reflektion
        );

        // Shape Array -----------------------------------------------------------------------
        // ACHTUNG: Darf niemals leer sein, wegen Treffererkennungs-Initialisierung! (s. unten)
        shapeArray[0] = sphere1;
        shapeArray[1] = sphere2;
        shapeArray[2] = plane2;
        shapeArray[3] = plane3;
        shapeArray[4] = plane5;
        shapeArray[5] = plane1;
        shapeArray[6] = plane4;

        // Lights -----------------------------------------------------------------------

        createLight(0, new RgbColor(0.8f, 0.8f, 0.8f), new Vec3(0f, 2.8f, -8f));


        // Alle Pixel druchlaufen...
        for (int j = 0; j < mBufferedImage.getHeight(); j++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = myCam.calculateDestination(i, j);       // Zielpunkt des Strahls ist das Äquivalent auf der Viewplane
                Ray r = new Ray(start, dest.sub(start), 200);       // Strahl, der durch den aktuellen Pixel geht

                Intersection inters = sendRay(r, null); //Strahlenberechnung für alle Objekte


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


    public static Intersection sendRay(Ray ray, Shape s)
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

        return inters;
    }

    private void szene1() {
        // Shapes -----------------------------------------------------------------------

        // Planes

        // rechts
        Plane plane1 = new Plane(   new Vec3(10f, 0, 0),                        // Position
                new Blinn(new RgbColor(0, 1, 0), 1f, 10),   // Material
                new Vec3(-1, 0, 0),                          // Normale
                new Reflection(1f)                                        // Refraktion/Reflektion
        );

        // links
        Plane plane2 = new Plane(   new Vec3(-20f, 0, 0),                        // Position
                new Blinn(new RgbColor(1, 0, 0), 1f, 10),   // Material
                new Vec3(1, 0, 0.5f),                           // Normale
                new Reflection(1f)                                           // Refraktion/Reflektion
        );

        // Boden
        Plane plane3 = new Plane(   new Vec3(0, -3, 0),                        // Position
                new Blinn(new RgbColor(1, 0, 0), 1f, 10),   // Material
                new Vec3(0, 1, 0),                           // Normale
                new Reflection(0.4f)                                        // Refraktion/Reflektion
        );

        // gegenüber
        Plane plane4 = new Plane(   new Vec3(0, 0, -50f),                        // Position
                new Lambert(new RgbColor(0, 0, 0), 1f),   // Material
                new Vec3(-1f, 0, 1),                          // Normale
                null                                           // Refraktion/Reflektion
        );

        shapeArray[0] = plane1;
        shapeArray[1] = plane2;
        shapeArray[2] = plane3;
        shapeArray[3] = plane4;

        Sphere sphere1 = new Sphere(new Vec3(1f, 1f, 1f),                 // Skalierung
                new Vec3(1, -2f, -40),                        // Position
                new Blinn(new RgbColor(1, 0, 0), 1f, 10),   // Material
                new Refraction(1f, Refraction.mCoffGlass)   // Refraktion/Reflektion
        );

        int max = 30;

        for (int b = 0; b <max ; b++)
        {
            Vec3 sphereStart = new Vec3(-1, -2, -38);
            Vec3 sphereEnd = new Vec3(1, -2, 0);
            Vec3 hor = new Vec3(new Random().nextFloat()*20f-10, 0, new Random().nextFloat()*30f);
            float bF = (float)b/100f;
            sphereEnd = sphereEnd.sub(sphereStart).multScalar(bF);
            sphereStart = sphereStart.add(sphereEnd).add(hor);

            RayHandling tempRh = new Reflection(new Random().nextFloat()*1f);

            if (new Random().nextFloat()*2f > 1f) {
                tempRh = new Refraction(new Random().nextFloat()*1f, Refraction.mCoffGlass);
            }

            float sclale = new Random().nextFloat()*0.8f+0.6f;
            Sphere sphere = new Sphere(new Vec3(sclale, sclale, sclale),                 // Skalierung
                    sphereStart,                        // Position
                    new Blinn(new RgbColor(new Random().nextFloat()*1f, new Random().nextFloat()*1f, new Random().nextFloat()*1f), new Random().nextFloat()*1f, new Random().nextFloat()*20f),   // Material
                    tempRh   // Refraktion/Reflektion
            );
            shapeArray[b+4] = sphere;
        }


        // Shape Array -----------------------------------------------------------------------
        // ACHTUNG: Darf niemals leer sein, wegen Treffererkennungs-Initialisierung! (s. unten)
        shapeArray[0] = sphere1;

        // Lights -----------------------------------------------------------------------

        createLight(0, new RgbColor(0.8f, 0.8f, 0.8f), new Vec3(0f, 2.8f, -8f));
        //createLight(0, new RgbColor(0.8f, 0.8f, 0.8f), new Vec3(0f, 0f, -13.5f));
    }

    private void kugelTest() {
        for (int b = 0; b <100; b++)
        {
            Vec3 sphereStart = new Vec3(-3, 3, -12);
            Vec3 sphereEnd = new Vec3(3, -3, 4);
            Vec3 hor = new Vec3(new Random().nextFloat()*3f, 0, 0);
            float bF = (float)b/100f;
            sphereEnd = sphereEnd.sub(sphereStart).multScalar(bF);
            sphereStart = sphereStart.add(sphereEnd).add(hor);

            Sphere sphere = new Sphere(new Vec3(0.1f, 0.1f, 0.1f), sphereStart, new Blinn(new RgbColor(0.9f, 0.6f, 0), 1, 10), new Reflection(1f));
            shapeArray[b+4] = sphere;
        }
    }
}