package backend.academy.fractal.records;

public record Image(Pixel[][] pixels, int width, int height) {
    public static Image create(int width, int height) {
        Pixel[][] newData = initializePixelArray(width, height);
        return new Image(newData, width, height);
    }

    private static Pixel[][] initializePixelArray(int width, int height) {
        Pixel[][] pixels = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = new Pixel(0, 0, 0, 0);
            }
        }
        return pixels;
    }

    public boolean contains(int x, int y) {
        return (0 <= x && x < width && 0 <= y && y < height);
    }
}
