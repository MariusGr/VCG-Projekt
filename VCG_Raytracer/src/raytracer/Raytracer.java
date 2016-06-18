package raytracer;

import light.Light;
import light.PointLight;
//import material.Blinn;
import material.Blinn;
import material.Lambert;
import material.Phong;
//import material.Reflection;
import material.Reflection;
import objects.Plane;
import objects.Shape;
import objects.Sphere;
import scene.Camera;
import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    public static ArrayList<Light> lightList = new ArrayList<Light>();
    public static RgbColor backgroundColor = new RgbColor(0f, 0f, 0f);
    public static RgbColor ambientLight = new RgbColor(0.1f, 0.1f, 0.1f);

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering!");

        // Camera  -----------------------------------------------------------------------
        Camera myCam = new Camera(new Vec3(0 ,0, 5), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 90f, new Vec3(0,0,0));
        Vec3 start = myCam.getPosition();

        // Shapes Positions -----------------------------------------------------------------------
        Vec3 sphereStart1 = new Vec3(1, -1, -6);
        Vec3 sphereStart2 = new Vec3(-1, -1, -3);

        // Shapes -----------------------------------------------------------------------
        Sphere sphere1 = new Sphere(1, sphereStart1, new Phong(new RgbColor(0.9f,0.6f,0),1,5));
        Sphere sphere2 = new Sphere(1, sphereStart2, new Blinn(new RgbColor(0,0,1),0.8f,5));

        Plane plane1 = new Plane(new Vec3(0,-2,0), new Phong(new RgbColor(1,1,1), 1f, 10), new Vec3(0, 1, 0));
        Plane plane2 = new Plane(new Vec3(0,0,-8), new Phong(new RgbColor(1,1,1), 1f, 10), new Vec3(0, 0, 1));
        Plane plane3 = new Plane(new Vec3(0,2.3f,0), new Phong(new RgbColor(1,1,1), 1f, 10), new Vec3(0, -1, 0));
        Plane plane4 = new Plane(new Vec3(2.3f,0,0), new Phong(new RgbColor(0,1,0), 1f, 10), new Vec3(-1, 0, 0));
        Plane plane5 = new Plane(new Vec3(-2.3f,0,0), new Phong(new RgbColor(1,0,0), 1f, 10), new Vec3(1, 0, 0));

        // Shape Array -----------------------------------------------------------------------
        // ACHTUNG: Darf niemals leer sein, wegen Treffererkennungs-Initialisierung! (s. unten)
        Shape[] shapeArray = new Shape[7];
        shapeArray[0] = sphere2;
        shapeArray[1] = sphere1;
        shapeArray[2] = plane1;
        shapeArray[3] = plane2;
        shapeArray[4] = plane3;
        shapeArray[5] = plane4;
        shapeArray[6] = plane5;


        // Lights -----------------------------------------------------------------------

        createLight(0, new RgbColor(0.8f,0.8f,0.8f), new Vec3(0, 1.5f, 0));

        // Alle Pixel druchlaufen...
        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = myCam.calculateDestination(i, j);       // Zielpunkt des Strahls ist das Äquivalent auf der Viewplane
                Ray r = new Ray(start, dest.sub(start), 200);       // Strahl, der durch den aktuellen Pixel geht

                // Folgende Werte müssen initialisert werden, indem sie für das erste Objekt im Array geprüft werden
                Intersection inters = shapeArray[0].intersect(r);   // wird am Ende der folgenden Schleife den rellevanten/nahesten Treffer beinhalten
                float smallestDistance = -1;                        // speichert die Distanz des bisher nahesten Treffers
                if (inters.hit) smallestDistance = inters.distance; // Wenn das erste Objekt schon einen Treffer hat, Distanz zum Vergleich mit evtl. weiteren Treffern speichern

                for (int k = 1; k < shapeArray.length; k++) {           // alle restliches Shapes durchlaufen...
                    Intersection tempInters = shapeArray[k].intersect(r);   // Treffer mit momentan betrachteter Shape prüfen
                    float tempDis = tempInters.distance;                // -1 --> kein Treffer, >1 Treffer mit Distanz "distance"

                    // Gab es einen Treffer? Ist dieser näher als der vorherige bzw. wenn es keinen gab bisher, setze diesen als rellevanten
                    if(tempInters.hit && (tempDis < smallestDistance || smallestDistance < 0)) {
                        inters = tempInters;
                        smallestDistance = tempDis;
                    }
                }
                mRenderWindow.setPixel(mBufferedImage, inters.getRgbColor(), new Vec2(i, j));     // Pixel entsprechend einfärben (inters.rgb ist backgroundColor, wenn kein Objekt getroffen)
            }
        }
        IO.saveImageToPng(mBufferedImage, "RenderBilder\\raytracing"+System.currentTimeMillis()+".png");
    }

    private Light createLight(int _type, RgbColor _col, Vec3 _pos) {
        Light l = new PointLight(_col, _pos);
        lightList.add(l);
        return l;
    }
}


/**
 *
// Init vor den Schleifen
 scene.Camera myCam = new scene.Camera(new Vec3(0 ,0, 0), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
 Vec3 start = myCam.getPosition();

 Vec3 dest = myCam.calculateDestination(mBufferedImage.getWidth(), mBufferedImage.getHeight());
 raytracer.Ray r = new raytracer.Ray(start, dest);
 Vec3 rEndP = r.getEndpoint();
 float rDist = r.getDistance();
 float vDiffMax = rDist-myCam.getFocalLength();
 Vec3 rRef = new Vec3(start.x+1, start.y, start.z).sub(start);

 // muss in die Schleifen rein


 dest = myCam.calculateDestination(i, j);
 r = new raytracer.Ray(start, dest);
 rEndP = r.getEndpoint();
 rDist = r.getDistance();
 float vDiff = rDist-myCam.getFocalLength();
 vDiff = vDiff/vDiffMax;

 Vec3 rV = r.rayVector();
 Vec3 rP = new Vec3 (rV.x, rV.y, start.z);

 double angle = rP.angle(rRef)*(360/(2*Math.PI));

 if (rP.y < rRef.y) {
 angle = 360-angle;
 }

 //Log.print(this, "--   "+rP.x+", "+rP.y+", "+rP.z+", AA:"+angle);

 float[] rgb = utils.HSV.hsvToRgb((float) angle, vDiff*100, 100f);
 float red = rgb[0];
 float blue = rgb[1];
 float green = rgb[2];

 mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
 **/