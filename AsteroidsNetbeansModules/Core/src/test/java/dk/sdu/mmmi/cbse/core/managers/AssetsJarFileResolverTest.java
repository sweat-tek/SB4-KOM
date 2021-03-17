package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxNativesLoader;
import java.io.File;
import java.net.MalformedURLException;
import static junit.framework.TestCase.assertNotNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author jcs
 */
public class AssetsJarFileResolverTest {

    public AssetsJarFileResolverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Gdx.app = mock(Application.class);
        Gdx.gl = mock(GL20.class);
        GdxNativesLoader.load();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of resolve method, of class AssetsJarFileResolver.
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testJarAssetManager() throws MalformedURLException {

        // SETUP
        System.out.println("testJarAssetManager");
       
        //TODO: Change for your own settings
        //String jarUrl1 = "/Users/jcs/Workspace/GitHub/SB4-KOM-F20/AsteroidsNetbeansModules/Core/target/Core-1.0-SNAPSHOT.jar!/assets/images/Ship.png";               
        String jarUrl = java.nio.file.Paths.get(new File("").getAbsolutePath(),
                "target", "Core-1.0-SNAPSHOT.jar!", "assets", "images", "Ship.png").toString();

        AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        AssetManager am = new AssetManager(jfhr);

        // TEST
        am.load(jarUrl, Texture.class);
        am.finishLoading();
        
        Texture result = am.get(jarUrl, Texture.class);

        // ASSERTS
        assertNotNull(result.getTextureData());
    }

}
