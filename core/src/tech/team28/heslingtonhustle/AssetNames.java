package tech.team28.heslingtonhustle;

/**
 * Data holder for the paths of assets that will be loaded by the Game's AssetManager.
 *
 * <p>This provides a central place for storing the asset name constants to ensure what we provide
 * to "load" matches what we provide to "get". The names of sprites within the atlas don't need to
 * be stored in fields here because they're only referenced once, in the Screen.
 */
public class AssetNames {
    /** The TextureAtlas manifest file, packed by ./gradlew texturePacker. */
    public static final String TEXTURE_ATLAS = "textures/pack.atlas";

    /**
     * The TiledMap description file edited with Tiled (mapeditor.org). We need this so we don't
     * have to render a single huge image as the background.
     */
    public static final String TILE_MAP = "map/map.tmx";

    /** The BitmapFont description file created by Hiero. */
    public static final String BITMAP_FONT = "lsans-32.fnt";

    /** Sound effect for feeding the ducks at a RecreationArea. */
    public static final String SOUND_QUACK = "sound/duck-quacking.mp3";

    /** Sound effect for eating at an EatArea. */
    public static final String SOUND_EATING = "sound/eating.mp3";

    /** Sound effect for studying at a StudyArea. */
    public static final String SOUND_STUDY = "sound/library-environment.mp3";

    /** Sound effect for snoring at a SleepArea. */
    public static final String SOUND_SLEEP = "sound/sleep.mp3";
}
