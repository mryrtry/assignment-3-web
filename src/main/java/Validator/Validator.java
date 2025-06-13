package Validator;

public class Validator {

    public static boolean validateParams(float x, float y, float r) {
        if (x < -3 || x > 5) return false;
        if (y < -5 || y > 5) return false;
        if (Math.round(r * 2) != r * 2 || r < 1 || r > 3) return false;
        return true;
    }

    public static boolean checkPointInArea(float x, float y, float r) {
        return checkPointInRectangle(x, y, r) || checkPointInSector(x, y, r) || checkPointInTriangle(x, y, r);
    }

    private static boolean checkPointInRectangle(float x, float y, float r) {
        return x <= 0 && x >= -r && y >= 0 && y <= r;
    }

    private static boolean checkPointInSector(float x, float y, float r) {
        return x >= 0 && y <= 0 && x * x + y * y <= r * r / 4;
    }

    private static boolean checkPointInTriangle(float x, float y, float r) {
        return x >= 0 && y >= 0 && y <= -x + r;
    }

}
